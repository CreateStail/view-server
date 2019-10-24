package com.synway.utils;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.UUID;

public class CommonUtils {

    private static final Gson gson = new Gson();


    public static String generateUUID(){
        String uuid = UUID.randomUUID().toString().
                replace("-","").substring(0,32);
        return uuid;
    }

    /**
     * md5常用工具类
     */
    public static String MD5(String data){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] array = md5.digest(data.getBytes("UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            for(byte item : array){
                stringBuilder.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1,3));
            }
            return stringBuilder.toString().toUpperCase();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    public static void sendJsonMessage(HttpServletResponse response,Object obj) throws Exception{
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(gson.toJson(obj));
        writer.close();
        response.flushBuffer();
    }

}
