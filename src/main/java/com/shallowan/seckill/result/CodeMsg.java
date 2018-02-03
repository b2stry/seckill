package com.shallowan.seckill.result;

import lombok.Data;

/**
 * @author ShallowAn
 */
@Data
public class CodeMsg {
    private int code;
    private String msg;

    //通用异常
    public static CodeMsg SUCEESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常！");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s!");
    public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102, "请求非法！");
    public static CodeMsg ACCESS_LIMIT_REACHED = new CodeMsg(500103, "访问太频繁！");

    //登录模块 5002XX
    public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效！");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "登录密码不能为空！");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号码不能为空！");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500213, "手机号码格式错误！");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214, "用户不存在！");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密码错误！");

    //商品模块 5003XX

    //订单模块 5004XX
    public static CodeMsg ORDER_NOT_EXIST = new CodeMsg(500400, "订单不存在！");

    //秒杀模块 5005XX
    public static CodeMsg SECKILL_OVER = new CodeMsg(500500, "商品已经秒杀完毕！");
    public static CodeMsg REPEATE_SECKILL = new CodeMsg(500501, "不能重复秒杀！");
    public static CodeMsg SECKILL_FAIL = new CodeMsg(500502, "秒杀失败！");


    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CodeMsg fillArgs(Object... objects) {
        int code = this.code;
        String message = String.format(this.msg, objects);
        return new CodeMsg(code, message);
    }

    @Override
    public String toString() {
        return "CodeMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
