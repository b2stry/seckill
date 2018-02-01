package com.shallowan.seckill.controller;

import com.shallowan.seckill.redis.RedisService;
import com.shallowan.seckill.result.Result;
import com.shallowan.seckill.service.SeckillUserService;
import com.shallowan.seckill.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author ShallowAn
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Autowired
    private RedisService redisSerice;

    @Autowired
    private SeckillUserService seckillUserService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response, @Valid LoginVO loginVO) {
        log.info("【用户登录】" + loginVO.toString());

        //登录
        String token = seckillUserService.login(response, loginVO);
        return Result.success(token);
    }
}
