package com.synway.service;

import com.synway.dao.ViewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ViewService {
    private static final Logger log = LoggerFactory.getLogger(ViewService.class);

    @Value("${file.prefix.mapping}")
    private String filePrefixMapping;
    @Autowired
    private ViewMapper viewMapper;
    @Autowired
    private UploadService uploadService;

    /**
     * TODO 这里事务未实现，回头再来看看
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean saveThemeContent(Map<String, Object> params, MultipartFile templeteFile) {
        try {
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
        } catch (Exception e) {
            log.error("保存主题失败", e);
            return false;
        }
        return true;
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
}
