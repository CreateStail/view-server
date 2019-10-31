package com.synway.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Introduction {
    private Integer id;
    private String theme;
    private String introduction_content;
    private String introduction_html;
    private int user_id;
    private String user_name;
    private Date create_time;
    private Integer xgr;
    private String xgr_name;
    private Date update_time;
    private int is_delete;
}
