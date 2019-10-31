package com.synway.service;

import com.google.gson.Gson;
import com.synway.dao.UploadMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UploadService {
    @Autowired
    private UploadMapper uploadMapper;
    private static Logger log = LoggerFactory.getLogger(UploadService.class);

    @Value("${file.prefix}")
    private String fielPrefix = "";
    @Value("${file.prefix.mapping}")
    private String fielPrefixMapping = "";

    public Map<String, String> uploadFile(MultipartFile file) throws IOException {
        log.info("========================上传附件开始=========================");
        String fileName = "";
        BufferedOutputStream out = null;
        Map<String, String> resultMap = new HashMap<>();
        File create_file = null;
        if (!file.isEmpty()) {
            File rootFile = new File(fielPrefix);
            if (!rootFile.exists()) {
                rootFile.mkdir();
            }
            fileName = file.getOriginalFilename();
            create_file = new File(fielPrefix + "/" + fileName);
            out = new BufferedOutputStream(new FileOutputStream(create_file));
            out.write(file.getBytes());
            out.flush();
            out.close();
            resultMap.put("fileName", create_file.getName());
            resultMap.put("filePath", create_file.getPath());
            resultMap.put("fileAddr", fielPrefixMapping + fileName);
        }
        return resultMap;
    }

    public Map<String, Object> uploadImage(MultipartFile file) throws IOException {
        log.info("========================上传(内容)图片开始=========================");
        String fileName = "";
        BufferedOutputStream out = null;
        Map<String, Object> paramsMap = new HashMap<>();
        Map<String, Object> resultMap = new HashMap<>();
        File rootFile = new File(fielPrefix + "/content");
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        fileName = file.getOriginalFilename();
        File create_file = new File(fielPrefix + "/content/" + fileName);
        paramsMap.put("file_name", create_file.getName());
        paramsMap.put("file_path", create_file.getPath());
        paramsMap.put("file_addr", fielPrefixMapping + "content/" + fileName);
        paramsMap.put("type", 0);
        int result = uploadMapper.saveContentPicture(paramsMap);
        if (result > 0) {
            out = new BufferedOutputStream(new FileOutputStream(create_file));
            out.write(file.getBytes());
            out.flush();
            out.close();
            resultMap.put("success", 1);
            resultMap.put("message", "上传成功");
            resultMap.put("url", fielPrefixMapping + "content/" + create_file.getName());
        } else {
            resultMap.put("success", 0);
            resultMap.put("message", "上传失败");
        }
        return resultMap;
    }


}
