package com.shallowan.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author ShallowAn
 */
public class MD5Util {
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "1a2b3c4d";

    public static String inputPassToFormPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String FormPassToDBPass(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBPass(String input, String saltDb) {
        String formpass = inputPassToFormPass(input);
        String dbPass = FormPassToDBPass(formpass, saltDb);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFormPass("123456"));
        System.out.println(inputPassToDBPass("123456", "1a2b3c4d"));
    }
}
