package com.synway.dao;

import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
public interface ViewMapper {

    int saveIntroduction(Map<String,Object> params);

    int saveBackground(Map<String,Object> params);

    int saveData(Map<String,Object> params);

    int saveProgram(Map<String,Object> params);

    int saveCode(Map<String,Object> params);

    int saveFile(Map<String,Object> params);

}
