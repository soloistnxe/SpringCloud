package com.example.inspect.entity;

import lombok.Data;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/2/9 11:43
 */
@Data
public class InspectWork {

    private Integer workId;

    private String companyName;

    private Integer companyId;

    private String productType;

    private String inspectScore;

    private Integer auditType;
}
