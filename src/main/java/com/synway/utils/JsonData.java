package com.synway.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 返回值
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JsonData implements Serializable{
    private static final long  seriaVersionUID = 1L;
    int code;//状态码:0成功,1处理中,-1失败
    String msg;//描述
    Object data;//数据

    public static JsonData buildSuccess(){
        return new JsonData(0,null,null);
    }

    public static JsonData buildSuccess(int code,String msg,Object data){
        return new JsonData(code,msg,data);
    }

    public static JsonData buildSuccess(String msg){
        return new JsonData(0,msg,null);
    }

    public static JsonData buildSuccess(String msg,Object data){
        return new JsonData(0,msg,data);
    }

    public static JsonData buildSuccess(Object data){
        return new JsonData(0,null,data);
    }

    public static JsonData buildError(){
        return new JsonData(-1,null,null);
    }

    public static JsonData buildError(int code,String msg,Object data){
        return new JsonData(code,msg,data);
    }

    public static JsonData buildError(String msg){
        return new JsonData(-1,msg,null);
    }

    public static JsonData buildError(String msg,Object data){
        return new JsonData(-1,msg,data);
    }

    public static JsonData buildError(Object data){
        return new JsonData(-1,null,data);
    }
}
