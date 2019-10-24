package com.synway.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;
    private String name;
    private String password;
    private String headImg;
    private String phone;
    private String email;
    private Integer sex;
    private Date createTime;
    private Integer state;
    private Integer lrr;
    private String lrrName;
    private Date updateTime;
}
