package com.synway.domain;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;
    private String name;
    private String headImg;
    private String phone;
    private String email;
    private Integer sex;
    private Date createTime;
    private int state;
}
