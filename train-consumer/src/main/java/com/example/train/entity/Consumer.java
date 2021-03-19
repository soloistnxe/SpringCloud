package com.example.train.entity;

import lombok.Data;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/2/22 10:24
 */
@Data
public class Consumer {

    /**
     * 用户Id
     */
    private Integer consumerId;

    /**
     * 用户名
     */
    private String consumerName;
    /**
     * 电话
     */
    private String consumerPhone;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户公司
     */
    private String consumerCompany;
    /**
     * 用户类型 0：管理员 1：普通用户
     */
    private Integer consumerType;
}
