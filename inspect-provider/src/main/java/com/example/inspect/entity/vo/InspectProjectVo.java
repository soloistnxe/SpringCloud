package com.example.inspect.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/1/19 15:54
 */
@Data
public class InspectProjectVo {

    private Integer projectId;

    private Integer projectTypeId;

    private String projectTypeName;

    private String projectContent;

    private List<String> projectOptionList;
}
