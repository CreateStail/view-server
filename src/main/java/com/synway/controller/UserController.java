package com.synway.controller;

import cn.hutool.crypto.SecureUtil;
import com.synway.pojo.Role;
import com.synway.pojo.User;
import com.synway.service.UserService;
import com.synway.utils.CaptchaUtils;
import com.synway.utils.JsonData;
import com.synway.utils.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/Api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public JsonData login(@RequestParam String username,
                          @RequestParam String password,
                          HttpServletResponse response){
        User user = userService.getUserByLoginInfo(username,password);
        if(user != null){
            //TODO 登录成功用户名和头像从cookie中获取(分布式部署的话不知道会不会有问题,
            // 可在nginx用ip_hash解决，或者请求用户信息)
            Cookie name_cookie = new Cookie("userName",user.getName());
            name_cookie.setPath("/");
            response.addCookie(name_cookie);
            Cookie img_cookie = new Cookie("headImg",user.getHeadImg());
            img_cookie.setPath("/");
            response.addCookie(img_cookie);
            //查询用户权限
            Role role = userService.getRoleById(user.getId());
            return JsonData.buildSuccess("登录成功", JwtUtils.getJsonWebToken(user,role));
        }else{
            throw new IncorrectCredentialsException();
        }
    }

    /**
     * 用来判断用户是否存在，是否已登录
     */
    @GetMapping("/article")
    public JsonData article(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            Map<String, Object> userRole = JwtUtils.getUserRole(subject.getPrincipal().toString());
            return JsonData.buildSuccess("已登录",userRole);
        }else {
            return JsonData.buildError("游客");
        }
    }

    /**
     * @RequiresAuthentication 注解需要带有token，否则进入不了API
     * @return
     */
    @GetMapping("/require_auth")
    @RequiresAuthentication
    public JsonData requireAuth(){
        return JsonData.buildSuccess("已鉴明身份");
    }



    /**
     * @RequiresRoles 与值不符合会报错
     * @return
     */
    @GetMapping("/reqire_role")
    @RequiresRoles("admin")
    public JsonData requireRole(){
        return JsonData.buildSuccess("管理员角色");
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND,value = {"view","edit"})
    public JsonData requirePermission() {
        return JsonData.buildSuccess("You are visiting permission require edit,view", null);
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public JsonData unauthorized() {
        return JsonData.buildError(401, "Unauthorized", null);
    }

    @GetMapping("/generateValidateCode")
    public JsonData generateValidateCode(HttpServletResponse response) throws IOException {
        //验证码token存入cookie
        String tokenId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("imgCodeToken",tokenId);
        cookie.setPath("/");
        response.addCookie(cookie);
        //定义图形验证码的长和宽
        String lineCaptcha = CaptchaUtils.createLineCaptcha();
        //凭证信息可保存到redis
        return JsonData.buildSuccess("加载验证图片成功","data:image/png;base64," + lineCaptcha);
    }

    /**
     * @param params
     * @return
     */
    @PostMapping("/saveUser")
    public JsonData saveUser(@RequestParam Map<String,Object> params,
                             HttpServletRequest request){
        String verifyInput = String.valueOf(params.get("verifyInput"));
        String password = String.valueOf(params.get("password"));
        params.put("password", SecureUtil.md5(password));
        //TODO 这里不太清楚是如何维护的,如果遇到高并发情况是否会有问题?
        boolean verifyResult = CaptchaUtils.verifyCapcha(verifyInput);
        if(verifyResult){
            boolean result = userService.saveUser(params);
            if(result){
                return JsonData.buildSuccess("用户注册成功");
            }else{
                return JsonData.buildError("用户注册失败");
            }
        }else{
            return  JsonData.buildError("验证码校验错误");
        }
    }





}
