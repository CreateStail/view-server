package com.synway.controller;

import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.synway.service.ViewService;
import com.synway.utils.JsonData;
import com.synway.utils.JwtUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Api/View")
public class ViewController {
    @Autowired
    private ViewService viewService;

    //TODO
    @PostMapping("/saveThemeContent")
    @RequiresRoles(value = "admin")
    public JsonData saveThemeContent(@RequestParam Map<String, Object> params,
                                     @RequestParam(value = "templeteFile",required = false) MultipartFile templeteFile,
                                     HttpServletRequest request) {
        //这里先直接从请求头获取token,后面改为redis
        String token = request.getHeader("token");
        int user_id = JwtUtils.getUserId(token);
        String user_name = JwtUtils.getUserName(token);

        params.put("user_id", user_id);
        params.put("user_name", user_name);
        boolean result = viewService.saveThemeContent(params,templeteFile);
        if (result) {
            return JsonData.buildSuccess("保存主题成功");
        } else {
            return JsonData.buildError("保存主题失败");
        }
    }

    @PostMapping("/listThemeData")
    public JsonData listThemeData() {
        List<Map<String, Object>> list = viewService.listThemeData();
        if (list.size() > 0) {
            return JsonData.buildSuccess("查询成功", list);
        } else {
            return JsonData.buildError("查询失败");
        }
    }

    @PostMapping("/getThemeContent")
    public JsonData getThemeContent(@RequestParam String theme_id){
        Map<String, Object> themeContent = viewService.getThemeContent(theme_id);
        if(themeContent != null){
            return JsonData.buildSuccess("查询主题id:"+theme_id+"成功",themeContent);
        }else{
            return JsonData.buildError("查询主题id:"+theme_id+"失败");
        }
    }

    @PostMapping("/getBackgroundContent")
    public JsonData getBackgroundContent(@RequestParam String theme_id){
        Map<String, Object> backgroundContent = viewService.getBackgroundContent(theme_id);
        if(backgroundContent != null){
            return JsonData.buildSuccess("查询背景内容成功-----主题id:"+theme_id,backgroundContent);
        }else{
            return JsonData.buildError("查询背景内容-----主题id:"+theme_id+"失败");
        }
    }

    @PostMapping("/getDataContent")
    public JsonData getDataContent(@RequestParam String theme_id){
        Map<String, Object> dataContent = viewService.getDataContent(theme_id);
        if(dataContent != null){
            return JsonData.buildSuccess("查询数据集内容成功-----主题id:"+theme_id,dataContent);
        }else{
            return JsonData.buildError("查询数据集内容-----主题id:"+theme_id+"失败");
        }
    }

    @PostMapping("/getProgramContent")
    public JsonData getProgramContent(@RequestParam String theme_id){
        Map<String, Object> programContent = viewService.getProgramContent(theme_id);
        if(programContent != null){
            return JsonData.buildSuccess("查询程序设计内容成功-----主题id:"+theme_id,programContent);
        }else{
            return JsonData.buildError("查询程序设计-----主题id:"+theme_id+"失败");
        }
    }

    @PostMapping("/getCodeContent")
    public JsonData getCodeContent(@RequestParam String theme_id){
        Map<String, Object> codeContent = viewService.getCodeContent(theme_id);
        if(codeContent != null){
            return JsonData.buildSuccess("查询代码内容成功-----主题id:"+theme_id,codeContent);
        }else{
            return JsonData.buildError("查询代码内容-----主题id:"+theme_id+"失败");
        }
    }

    @GetMapping("/listView")
    @RequiresRoles("admin")
    public String listView(@RequestParam int pageSize,
                           @RequestParam int page,String sort,String order){
        return  viewService.listView(pageSize,page,sort+" "+order);
    }

    @PostMapping("/getThemeDetails")
    @RequiresRoles("admin")
    public JsonData getThemeDetails(@RequestParam String themeId){
        Map<String, Object> themeDetails = viewService.getThemeDetails(themeId);
        if(themeDetails != null){
            return JsonData.buildSuccess("查询主题详情成功",themeDetails);
        }else{
            return JsonData.buildError("查询主题详情失败");
        }
    }

    @PostMapping("/delFile")
    public JsonData delFile(@RequestParam int id){
        boolean result = viewService.delFile(id);
        if(result){
            return JsonData.buildSuccess("删除附件成功");
        }else{
            return JsonData.buildError("删除附件失败");
        }
    }

    @GetMapping("/delTheme")
    public JsonData delTheme(@RequestParam int id){
        boolean result = viewService.delTheme(id);
        if(result){
            return JsonData.buildSuccess("删除主题成功");
        }else{
            return JsonData.buildError("删除主题失败");
        }
    }

    @GetMapping("/getDefaultThemeId")
    public JsonData getDefaultThemeId(){
        String defaultThemeId = viewService.getDefaultThemeId();
        return JsonData.buildSuccess(null,defaultThemeId);
    }
}
