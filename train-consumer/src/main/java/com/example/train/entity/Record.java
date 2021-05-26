package com.example.train.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/5/16 10:46
 */
@Data
public class Record {

    private Integer recordId;

    private Integer consumerId;

    private String questionDetail;

    private Integer mark;
    /**
     * 问卷类型： 推荐问卷和随机问卷
     */
    private String examType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;
}
