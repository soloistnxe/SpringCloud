package com.example.train.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/5/16 11:01
 */
@Data
public class RecordVo {

    private Integer consumerId;

    private List<QuestionDetail> questionDetail;

    private Integer mark;

    private String examType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

}

