package com.synway.controller;

import com.synway.service.ViewService;
import com.synway.utils.JsonData;
import com.synway.utils.JwtUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/Api")
public class ViewController {
    @Autowired
    private ViewService viewService;

    @PostMapping("/saveThemeContent")
    @RequiresRoles(value = "admin")
    public JsonData saveThemeContent(@RequestParam Map<String,Object> params,
                                     HttpServletRequest request){
        //这里先直接从请求头获取token,后面改为redis
        String token = request.getHeader("token");
        int user_id = JwtUtils.getUserId(token);
        String user_name = JwtUtils.getUserName(token);
        params.put("theme_id",null);
        params.put("background_id",null);
        params.put("data_id",null);
        params.put("program_id",null);
        params.put("code_id",null);
        params.put("user_id",user_id);
        params.put("user_name",user_name);
        viewService.saveThemeContent(params);
        return JsonData.buildSuccess();
    }

}
