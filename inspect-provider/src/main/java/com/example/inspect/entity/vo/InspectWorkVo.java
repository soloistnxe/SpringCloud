package com.example.inspect.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/2/10 18:18
 */
@Data
public class InspectWorkVo {

    private Integer workId;

    private String companyName;

    private String productType;

    private List<String> detail;

    private List<ScoreDetail> inspectScoreDetail;

    private Integer score;

    private Integer auditType;
}
