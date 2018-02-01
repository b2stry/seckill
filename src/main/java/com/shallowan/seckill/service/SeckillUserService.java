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
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ShallowAn
 */
@Service
public class SeckillUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    private SeckillUserDao seckillUserDao;

    @Autowired
    private RedisService redisService;

    public SeckillUser getById(long id) {
        return seckillUserDao.getByid(id);
    }

    public boolean login(HttpServletResponse response, LoginVO loginVO) {
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
        return true;
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


        redisService.set(SeckillUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(SeckillUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
