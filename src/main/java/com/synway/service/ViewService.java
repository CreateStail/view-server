package com.synway.service;

import com.synway.dao.ViewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ViewService {
    private static final Logger log = LoggerFactory.getLogger(ViewService.class);

    @Autowired
    private ViewMapper viewMapper;

    /**
     * 这里事务未实现，回头再来看看
     * @param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean saveThemeContent(Map<String, Object> params) {
        try {
            params.put("theme_id",null);
            params.put("background_id",null);
            params.put("data_id",null);
            params.put("program_id",null);
            params.put("code_id",null);
            params.put("type",1);
            int introductionCount = viewMapper.saveIntroduction(params);
            if(!"".equals(params.get("themePic_Name")) && introductionCount > 0){
                params.put("business_id",params.get("theme_id"));
                params.put("business_name","主题图片");
                params.put("file_name",params.get("themePic_Name"));
                params.put("file_path",params.get("themePic_Path"));
                params.put("file_addr",params.get("themePic_Addr"));
                viewMapper.saveFile(params);
            }
            int backgroundCount = viewMapper.saveBackground(params);

            int dataCount = viewMapper.saveData(params);
            if(!"".equals(params.get("themeData_Name")) && dataCount > 0){
                params.put("business_id",params.get("data_id"));
                params.put("business_name","数据集附件");
                params.put("file_name",params.get("themeData_Name"));
                params.put("file_path",params.get("themeData_Path"));
                params.put("file_addr",params.get("themeData_Addr"));
                viewMapper.saveFile(params);
            }
            int programCount = viewMapper.saveProgram(params);
            if(!"".equals(params.get("themeProgram_Name")) && programCount > 0){
                params.put("business_id",params.get("program_id"));
                params.put("business_name","设计附件");
                params.put("file_name",params.get("themeProgram_Name"));
                params.put("file_path",params.get("themeProgram_Path"));
                params.put("file_addr",params.get("themeProgram_Addr"));
                viewMapper.saveFile(params);
            }
            int codeCount = viewMapper.saveCode(params);
            if(!"".equals(params.get("themeCode_Name")) && codeCount > 0){
                params.put("business_id",params.get("code_id"));
                params.put("business_name","代码附件");
                params.put("file_name",params.get("themeCode_Name"));
                params.put("file_path",params.get("themeCode_Path"));
                params.put("file_addr",params.get("themeCode_Addr"));
                viewMapper.saveFile(params);
            }
        } catch (Exception e) {
            log.error("保存主题失败",e.getMessage());
            return false;
        }
        return true;
    }

    public List<Map<String,Object>> listThemeData(){
       return viewMapper.listThemeData();
    }

    public Map<String,Object> getThemeContent(String theme_id){
        return viewMapper.getThemeContent(theme_id);
    }

    public Map<String,Object> getBackgroundContent(String theme_id){
        return viewMapper.getBackgroundContent(theme_id);
    }

    public Map<String,Object> getDataContent(String theme_id){
        //可以根据主题id关联出data表id再去file表找出附件，
        //也可以根据id直接去file和业务名找出附件
        //由于目前不存再同一业务一对多附件，切data表中没什么字段，所以采取第二种方式
        Map<String,String> params = new HashMap<>();
        params.put("theme_id",theme_id);
        params.put("business_name","数据集附件");
        return viewMapper.findAttachment(params);
    }

    public Map<String,Object> getProgramContent(String theme_id){
        //同上
        Map<String,String> params = new HashMap<>();
        params.put("theme_id",theme_id);
        params.put("business_name","设计附件");
        return viewMapper.findAttachment(params);
    }
    public Map<String,Object> getCodeContent(String theme_id){
        //同上
        Map<String,String> params = new HashMap<>();
        params.put("theme_id",theme_id);
        params.put("business_name","代码附件");
        return viewMapper.findAttachment(params);
    }
}
