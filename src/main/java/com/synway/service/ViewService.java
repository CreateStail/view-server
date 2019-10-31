package com.synway.service;

import com.synway.dao.ViewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class ViewService {
    @Autowired
    private ViewMapper viewMapper;

    @Transactional
    public boolean saveThemeContent(Map<String, Object> params) {
        try {
            int introductionCount = viewMapper.saveIntroduction(params);
            if(!"".equals(params.get("themePic_Name")) && introductionCount > 0){
                
            }
            int backgroundCount = viewMapper.saveBackground(params);
            int dataCount = viewMapper.saveData(params);
            int programCount = viewMapper.saveProgram(params);
            int codeCount = viewMapper.saveCode(params);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }


}
