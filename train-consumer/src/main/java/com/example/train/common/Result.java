package com.example.train.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/1/8 17:03
 */
@Data
public class Result {

    private Boolean success = true;

    private Integer code = 200;

    private String message;

    private Map<String, Object> data = new HashMap<String, Object>();



}
