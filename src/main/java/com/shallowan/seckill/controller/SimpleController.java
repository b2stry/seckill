package com.shallowan.seckill.controller;

import com.shallowan.seckill.domain.User;
import com.shallowan.seckill.rabbitmq.MQSender;
import com.shallowan.seckill.redis.RedisService;
import com.shallowan.seckill.redis.UserKey;
import com.shallowan.seckill.result.CodeMsg;
import com.shallowan.seckill.result.Result;
import com.shallowan.seckill.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ShallowAn
 */
@Controller
@RequestMapping("/demo")
public class SimpleController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisSerice;

    @Autowired
    private MQSender sender;


//    @GetMapping("/mq/header")
//    @ResponseBody
//    public Result<String> mqHeader() {
//        sender.sendHeaders("hello,message");
//        return Result.success("Hello,world");
//    }
//
//    //swagger
//    @GetMapping("/mq/fanout")
//    @ResponseBody
//    public Result<String> mqFanout() {
//        sender.sendFanout("hello,message");
//        return Result.success("Hello,world");
//    }
//
//    @GetMapping("/mq/topic")
//    @ResponseBody
//    public Result<String> mqTopic() {
//        sender.sendTopic("hello,message");
//        return Result.success("Hello,world");
//    }
//
//
//    @GetMapping("/mq")
//    @ResponseBody
//    public Result<String> mq() {
//        sender.send("hello,message");
//        return Result.success("Hello,world");
//    }

    @ApiOperation("初始化测试接口")
    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello() {
        return Result.success("hello,imooc");
    }

    @ApiOperation("error测试接口")
    @RequestMapping("/error")
    @ResponseBody
    public Result<String> error() {
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    @ApiOperation("thymeleaf测试接口")
    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "shallowan");
        return "hello";
    }

    @ApiOperation("db测试接口")
    @RequestMapping("/db")
    @ResponseBody
    public Result<User> dbGet() {
        User user = userService.getById(1);
        return Result.success(user);
    }

    @ApiOperation("db事务测试接口")
    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx() {
        boolean b = userService.tx();
        return Result.success(b);
    }

    @ApiOperation("redis get测试接口")
    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<List<User>> redisGet() {
        User user = redisSerice.get(UserKey.getById, "1", User.class);
        User user2 = redisSerice.get(UserKey.getById, "2", User.class);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user2);
        return Result.success(userList);
    }

    @ApiOperation("redis set测试接口")
    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet() {
        User user = new User();
        user.setId(2);
        user.setName("222222");
        boolean ret = redisSerice.set(UserKey.getById, "2", user);
        return Result.success(ret);
    }
}
