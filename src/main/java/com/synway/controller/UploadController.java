package com.synway.controller;

import com.google.gson.Gson;
import com.synway.service.UploadService;
import com.synway.utils.JsonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Api")
public class UploadController {
    private static Logger log = LoggerFactory.getLogger(UploadController.class);
    private Gson gson = new Gson();
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public JsonData uplodFile(@RequestParam("file") MultipartFile file) {
        try {
            Map<String,String> resultMap = uploadService.uploadFile(file);
            if(resultMap != null){
                return JsonData.buildSuccess("上传成功",resultMap);
            }
        } catch (IOException e) {
            log.error("上传失败==========="+file.getOriginalFilename());
            JsonData.buildError("上传失败");
        }
        return JsonData.buildError("上传失败");
    }

    @PostMapping("/uploadImage")
    public String uploadImage(@RequestParam(value = "editormd-image-file") MultipartFile file) {
        Map<String,Object> resulMap = new HashMap<>();
        try {
             resulMap = uploadService.uploadImage(file);
        } catch (IOException e) {
            log.error("上传失败============="+file.getOriginalFilename());
            JsonData.buildError("上传失败");
        }
        return gson.toJson(resulMap);
    }

    @PostMapping("/uploadHeadImg")
    public JsonData uplodHeadImg(@RequestParam("headImg") MultipartFile file) {
        try {
            Map<String,String> resultMap = uploadService.uploadHeadImg(file);
            if(resultMap != null){
                return JsonData.buildSuccess("上传成功",resultMap);
            }
        } catch (IOException e) {
            log.error("上传失败==========="+file.getOriginalFilename());
            JsonData.buildError("上传失败");
        }
        return JsonData.buildError("上传失败");
    }
}
