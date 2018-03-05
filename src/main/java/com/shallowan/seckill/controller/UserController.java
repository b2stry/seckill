package com.shallowan.seckill.controller;

import com.shallowan.seckill.domain.SeckillUser;
import com.shallowan.seckill.redis.RedisService;
import com.shallowan.seckill.result.Result;
import com.shallowan.seckill.service.SeckillUserService;
import com.shallowan.seckill.vo.LoginVO;
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
@RequestMapping("/user")
@Slf4j
public class UserController {

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @RequestMapping("/info")
    @ResponseBody
    public Result<SeckillUser> doLogin(SeckillUser seckillUser) {
        return Result.success(seckillUser);
    }
}
