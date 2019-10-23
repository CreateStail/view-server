package com.synway.domain;

import lombok.Data;

@Data
public class Role {
    private Integer id;
    private Integer userId;
    private String role;
    private String permission;

}
