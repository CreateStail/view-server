package com.synway.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Controller
@RequestMapping("/Test")
public class TestController {

    @GetMapping("/generateValidateCode")
    @ResponseBody
    public String generateValidateCode(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        //验证码token存入cookie
        String tokenId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("imgCodeToken",tokenId);
        cookie.setPath("/");
        response.addCookie(cookie);

        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(120,35,4,150);
        String imageBase64 = lineCaptcha.getImageBase64();
        byte[] imageByte = imageBase64.getBytes();
        //凭证信息可保存到redis

        return "data:image/png;base64," + imageBase64;
        //输出浏览器
/*        OutputStream out = response.getOutputStream();

        lineCaptcha.write(out);
        out.flush();
        out.close();*/
    }
}
