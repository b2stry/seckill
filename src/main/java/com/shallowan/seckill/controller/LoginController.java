package com.shallowan.seckill.controller;

import com.shallowan.seckill.redis.RedisService;
import com.shallowan.seckill.result.Result;
import com.shallowan.seckill.service.SeckillUserService;
import com.shallowan.seckill.vo.LoginVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("获取登录界面接口")
    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @ApiOperation("登录接口")
    @ApiImplicitParam(name = "loginVO", value = "登录实体", required = true, dataType = "LoginVO")
    @RequestMapping("/do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response, @Valid LoginVO loginVO) {
        log.info("【用户登录】" + loginVO.toString());

        //登录
        String token = seckillUserService.login(response, loginVO);
        return Result.success(token);
    }
}
