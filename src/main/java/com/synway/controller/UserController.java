package com.synway.controller;

import com.synway.pojo.User;
import com.synway.service.UserService;
import com.synway.utils.JsonData;
import com.synway.utils.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
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


@RestController
@RequestMapping("/Api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public JsonData login(@RequestParam String username,
                          @RequestParam String password){
        User user = userService.getUserByLoginInfo(username,password);
        if(user != null){
            return JsonData.buildSuccess("登录成功", JwtUtils.getJsonWebToken(user));
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
            return JsonData.buildSuccess("已登录");
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

}
