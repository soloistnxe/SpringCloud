package com.example.train.entity;

import lombok.Data;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/2/22 10:30
 */

/**
 * 公司信息
 */
@Data
public class Company {
    /**
     * 公司Id
     */
    private Integer companyId;
    /**
     * 公司名
     */
    private String companyName;
    /**
     * 公司电话
     */
    private String companyPhone;

    /**
     * 公司地址
     */
    private String companyAddress;
    /**
     * 公司邮箱
     */
    private String companyEmail;
    /**
     * 产品类别
     */
    private String productType;
}
