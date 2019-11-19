package com.synway.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.synway.dao.ViewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ViewService {
    private static final Logger log = LoggerFactory.getLogger(ViewService.class);
    private static final Gson gson = new Gson();
    @Value("${file.prefix.mapping}")
    private String filePrefixMapping;
    @Autowired
    private ViewMapper viewMapper;
    @Autowired
    private UploadService uploadService;


    @Transactional(rollbackFor = Exception.class)
    public boolean saveThemeContent(Map<String, Object> params, MultipartFile templeteFile) {
//        try {
            String pageType = String.valueOf(params.get("pageType"));
            if("add".equals(pageType)){
                doSave(params,templeteFile);
            }else if("edit".equals(pageType)){
                doUpdate(params,templeteFile);
            }
//        } catch (Exception e) {
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            log.error("保存主题失败", e);
//            return false;
//        }
        return true;
    }

    /**
     * 保存方法实现
     * @param params
     * @param templeteFile
     */
    private void doSave(Map<String, Object> params, MultipartFile templeteFile){
        params.put("theme_id", null);
        params.put("background_id", null);
        params.put("data_id", null);
        params.put("program_id", null);
        params.put("code_id", null);
        params.put("type", 1);
        //读取Excel模板中的值
        List<Map<String, Object>> templeteList = null;
        if (templeteFile != null) {
            templeteList = uploadService.listTempleteData(templeteFile);
        }
        int introductionCount = viewMapper.saveIntroduction(params);
        if (!"".equals(params.get("themePic_Name")) && introductionCount > 0) {
            params = saveFile("themePic_", params, (int) params.get("theme_id"), "主题图片");
            viewMapper.saveFile(params);
        }
        int backgroundCount = viewMapper.saveBackground(params);

        int dataCount = viewMapper.saveData(params);
        if (!"".equals(params.get("themeData_Name")) && dataCount > 0) {
            params = saveFile("themeData_", params, (int) params.get("data_id"), "数据集");
            viewMapper.saveFile(params);
        }
        int programCount = viewMapper.saveProgram(params);
        if (!"".equals(params.get("themeProgram_Name")) && programCount > 0) {
            params = saveFile("themeProgram_", params, (int) params.get("program_id"), "技术方案");
            viewMapper.saveFile(params);
        }
        int codeCount = viewMapper.saveCode(params);
        if (!"".equals(params.get("themeCode_Name")) && codeCount > 0) {
            params = saveFile("themeCode_", params, (int) params.get("code_id"), "实现代码");
            viewMapper.saveFile(params);
        }
        //保存模板附件方法
        if (templeteList != null && templeteList.size() > 0) {
            saveTempleteFile(templeteList, params);
        }
    }



    private Map<String, Object> saveFile(String type,
                                         Map<String, Object> params,
                                         int business_id,
                                         String business_name) {
        params.put("business_id", business_id);
        params.put("business_name", business_name);
        params.put("file_name", params.get(type + "Name"));
        params.put("file_path", params.get(type + "Path"));
        params.put("file_addr", params.get(type + "Addr"));
        params.put("type",1);
        params.put("position", 0);
        return params;
    }

    public void saveTempleteFile(List<Map<String, Object>> templeteList, Map<String, Object> params) {
        params.remove("business_id");
        params.remove("business_name");
        params.remove("file_name");
        params.remove("file_path");
        params.remove("file_addr");
        params.remove("position");
        for (int i = 0; i < templeteList.size(); i++) {
            String business_name = String.valueOf(templeteList.get(i).get("business_name"));
            String file_name = String.valueOf(templeteList.get(i).get("file_name"));
            String file_path = String.valueOf(templeteList.get(i).get("file_path"));
            params.put("file_name", file_name);
            params.put("file_path", file_path);
            //如果是windows系统，模板中一般是 D:\software\nginx_win.rar,所以只需要soft..后的内容
            //否则就默认是linux系统,模板中是 /home/package/nginx/pcre/pcre-8.33.tar.gz 所以只需要home以后的内容,并且不需要转换符号
            if(System.getProperty("os.name").startsWith("Win")){
                params.put("file_addr", filePrefixMapping + file_path.substring(file_path.indexOf("\\") + 1).replace("\\","/"));
            }else{
                params.put("file_addr", filePrefixMapping + file_path.substring(1));
            }
            params.put("position", "1");
            switch (business_name) {
                case "数据集":
                    params.put("business_id", params.get("data_id"));
                    params.put("business_name", "数据集");
                    viewMapper.saveFile(params);
                    break;
                case "技术方案":
                    params.put("business_id", params.get("program_id"));
                    params.put("business_name", "技术方案");
                    viewMapper.saveFile(params);
                    break;
                case "实现代码":
                    params.put("business_id", params.get("code_id"));
                    params.put("business_name", "实现代码");
                    viewMapper.saveFile(params);
                    break;
            }
        }
    }

    public List<Map<String, Object>> listThemeData() {
        return viewMapper.listThemeData();
    }

    public Map<String, Object> getThemeContent(String theme_id) {
        return viewMapper.getThemeContent(theme_id);
    }

    public Map<String, Object> getBackgroundContent(String theme_id) {
        return viewMapper.getBackgroundContent(theme_id);
    }

    public Map<String, Object> getDataContent(String theme_id) {
        //根据主题id查询数据集数据和主题数据,根据数据集id查询对应的附件
        Map<String, Object> resultMap = viewMapper.getDataByThemeId(theme_id);
        int business_id =  Integer.parseInt(resultMap.get("id").toString());
        List<Map<String, Object>> attachmentMap = viewMapper.getAttachmentByBusinessIdAndType(business_id,"数据集");
        resultMap.put("attachmentMap",attachmentMap);
        return resultMap;
    }

    public Map<String, Object> getProgramContent(String theme_id) {
        //同上
        Map<String, Object> resultMap = viewMapper.getProgramByThemeId(theme_id);
        int business_id =  Integer.parseInt(resultMap.get("id").toString());
        List<Map<String, Object>> attachmentMap = viewMapper.getAttachmentByBusinessIdAndType(business_id,"技术方案");
        resultMap.put("attachmentMap",attachmentMap);
        return resultMap;
    }

    public Map<String, Object> getCodeContent(String theme_id) {
        //同上
        Map<String, Object> resultMap = viewMapper.getCodeByThemeId(theme_id);
        int business_id =  Integer.parseInt(resultMap.get("id").toString());
        List<Map<String, Object>> attachmentMap = viewMapper.getAttachmentByBusinessIdAndType(business_id,"实现代码");
        resultMap.put("attachmentMap",attachmentMap);
        return resultMap;
    }

    public String listView(int pageSize,int page,String sort){
        Map<String,Object> result = new HashMap<>();
        PageHelper.startPage(page,pageSize,sort);
        List<Map<String,Object>>  list = viewMapper.listView();
        PageInfo pageInfo = new PageInfo(list);
        result.put("rows",pageInfo.getList());
        result.put("total",pageInfo.getTotal());
        return gson.toJson(result);
    }

    public Map<String,Object> getThemeDetails(String themeId){
        Map<String, Object> themeContent = viewMapper.getThemeContent(themeId);
        Map<String, Object> backgroundContent = viewMapper.getBackgroundContent(themeId);
        Map<String, Object> dataByThemeId = viewMapper.getDataByThemeId(themeId);
        Map<String, Object> programByThemeId = viewMapper.getProgramByThemeId(themeId);
        Map<String, Object> codeByThemeId = viewMapper.getCodeByThemeId(themeId);
        String type = "1";
        List<Map<String, Object>> fileByType = viewMapper.getFileByType(themeId, type);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("theme",themeContent);
        resultMap.put("background",backgroundContent);
        resultMap.put("data",dataByThemeId);
        resultMap.put("program",programByThemeId);
        resultMap.put("code",codeByThemeId);
        resultMap.put("file",fileByType);
        return resultMap;
    }

    public boolean delFile(int id){
        int result = viewMapper.delFileById(id);
        if(result > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 实现更新方法
     * 由于各模块如主题和背景间还未存在一对多关系
     * 所以更新使用主题id更新条件
     * @param params
     */
    private void doUpdate(Map<String,Object> params, MultipartFile templeteFile){
        params.put("update_time",new Date());
        viewMapper.updateTheme(params);
        //封装模块id
        encapsulationModalId(params);

        viewMapper.updateBackground(params);
        //数据集,技术方案,代码实现目前只有附件没有内容,故不在此更新
/*        viewMapper.updateData(params);
        viewMapper.updateCode(params);
        viewMapper.updateProgram(params);*/
        if (!"".equals(params.get("themePic_Name"))) {
            params = saveFile("themePic_", params, Integer.parseInt(params.get("theme_id").toString()), "主题图片");
            viewMapper.saveFile(params);
        }
        if (!"".equals(params.get("themeData_Name"))) {
            params = saveFile("themeData_", params, Integer.parseInt(params.get("data_id").toString()), "数据集");
            viewMapper.saveFile(params);
        }
        if (!"".equals(params.get("themeProgram_Name"))) {
            params = saveFile("themeProgram_", params, Integer.parseInt(params.get("program_id").toString()), "技术方案");
            viewMapper.saveFile(params);
        }
        if (!"".equals(params.get("themeCode_Name"))) {
            params = saveFile("themeCode_", params, Integer.parseInt(params.get("code_id").toString()), "实现代码");
            viewMapper.saveFile(params);
        }
        //读取Excel模板中的值
        List<Map<String, Object>> templeteList = null;
        if (templeteFile != null) {
            templeteList = uploadService.listTempleteData(templeteFile);
        }
        //保存模板附件方法
        if (templeteList != null && templeteList.size() > 0) {
            saveTempleteFile(templeteList, params);
        }
    }

    private void encapsulationModalId(Map<String,Object> params){
        String business_ids = String.valueOf(params.get("business_ids"));
        JSONObject jsonObject = JSONObject.parseObject(business_ids);
        String background_id = String.valueOf(jsonObject.get("background_id"));
        String data_id = String.valueOf(jsonObject.get("data_id"));
        String program_id = String.valueOf(jsonObject.get("program_id"));
        String code_id = String.valueOf(jsonObject.get("code_id"));
        params.put("background_id",background_id);
        params.put("data_id",data_id);
        params.put("program_id",program_id);
        params.put("code_id",code_id);
    }

    public boolean delTheme(int id){
        int result = viewMapper.delThemeById(id);
        if(result > 0){
            return true;
        }else{
            return false;
        }
    }

}
