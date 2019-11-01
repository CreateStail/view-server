package com.synway.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface ViewMapper {

    int saveIntroduction(Map<String,Object> params);

    int saveBackground(Map<String,Object> params);

    int saveData(Map<String,Object> params);

    int saveProgram(Map<String,Object> params);

    int saveCode(Map<String,Object> params);

    void saveFile(Map<String,Object> params);

    List<Map<String,Object>> listThemeData();

    Map<String,Object> getThemeContent(String theme_id);

    Map<String,Object> getBackgroundContent(String theme_id);

    Map<String,Object> findAttachment(Map<String,String> params);
}
