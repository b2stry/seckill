package com.shallowan.seckill.service;

import com.shallowan.seckill.dao.SeckillUserDao;
import com.shallowan.seckill.domain.SeckillUser;
import com.shallowan.seckill.exception.GlobalException;
import com.shallowan.seckill.redis.RedisService;
import com.shallowan.seckill.redis.SeckillUserKey;
import com.shallowan.seckill.result.CodeMsg;
import com.shallowan.seckill.util.MD5Util;
import com.shallowan.seckill.util.UUIDUtil;
import com.shallowan.seckill.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ShallowAn
 * <p>
 * service只能调service
 * service只能调自己的dao
 */
@Service
public class SeckillUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    private SeckillUserDao seckillUserDao;

    @Autowired
    private RedisService redisService;

    public SeckillUser getById(long id) {
        //取缓存
        SeckillUser user = redisService.get(SeckillUserKey.getById, "" + id, SeckillUser.class);
        if (user != null) {
            return user;
        }

        //取数据库
        user = seckillUserDao.getByid(id);
        if (user != null) {
            redisService.set(SeckillUserKey.getById, "" + id, user);
        }
        return user;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updatePassword(String token, long id, String passwordNew) {
        //取user
        SeckillUser user = getById(id);
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //更新数据库
        SeckillUser toUpdateUpdate = new SeckillUser();
        toUpdateUpdate.setId(id);
        toUpdateUpdate.setPassword(MD5Util.FormPassToDBPass(passwordNew, user.getSalt()));
        seckillUserDao.update(toUpdateUpdate);
        //处理缓存
        redisService.delete(SeckillUserKey.getById, "" + id);
        user.setPassword(toUpdateUpdate.getPassword());
        redisService.set(SeckillUserKey.token, token, user);
        return true;
    }

    public String login(HttpServletResponse response, LoginVO loginVO) {
        if (loginVO == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVO.getMobile();
        String formPass = loginVO.getPassword();

        //判断手机号是否存在
        SeckillUser user = getById(Long.parseLong(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.FormPassToDBPass(formPass, saltDB);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        String token = UUIDUtil.uuid();
        //生成cookie
        addCookie(response, token, user);
        return token;
    }

    public SeckillUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        SeckillUser user = redisService.get(SeckillUserKey.token, token, SeckillUser.class);
        if (user != null) {
            //延迟有效时间
            addCookie(response, token, user);
        }

        return user;
    }

    private void addCookie(HttpServletResponse response, String token, SeckillUser user) {


        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        redisService.set(SeckillUserKey.token, token, user);
        cookie.setMaxAge(SeckillUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
