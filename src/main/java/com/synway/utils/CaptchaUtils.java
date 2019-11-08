package com.synway.utils;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;

public class CaptchaUtils {
    private static LineCaptcha lineCaptcha = null;

    public static String createLineCaptcha(){
        lineCaptcha = CaptchaUtil.createLineCaptcha(120,35,4,50);
        int i = lineCaptcha.hashCode();
        System.out.println(i);
        return lineCaptcha.getImageBase64();
    }

    public static boolean verifyCapcha(String code){
        int i = lineCaptcha.hashCode();
        System.out.println(i);
        return lineCaptcha.verify(code);
    }

}
