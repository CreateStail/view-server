package com.synway.dao;


import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UploadMapper {

    int saveContentPicture(Map<String,Object> params);
}
