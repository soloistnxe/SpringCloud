package com.example.inspect.entity;

import lombok.Data;
import org.springframework.data.relational.core.sql.In;

/**
 * @author soloist
 * @version 1.0
 * @describe 用户实体类
 * @date 201/6 11:34
 */
@Data
public class Users {
    /**
     * 用户编号id
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户电话
     */
    private String userPhone;
    /**
     * 用户邮箱
     */
    private String userEmail;
    /**
     * 登陆密码
     */
    private String password;
    /**
     * 用户类型 0:管理员 1:普通用户
     */
    private Integer userType;
}
