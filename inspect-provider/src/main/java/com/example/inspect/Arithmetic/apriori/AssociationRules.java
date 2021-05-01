package com.example.inspect.Arithmetic.apriori;

import lombok.Data;

import java.util.List;

/**
 * 关联规则
 */
@Data
public class AssociationRules {
    /**
     * 前项
     */
    private List<String> formerItem;
    /**
     * 后项
     */
    private List<String> behindItem;
    /**
     * 置信度
     */
    private Double confidence;

    /**
     * 规则表示
     * @return
     */
    public String rulesExpress(){
        return this.formerItem.toString()+"->"+this.behindItem.toString();
    }

    public AssociationRules(List<String> formerItem, List<String> behindItem, Double confidence) {
        this.formerItem = formerItem;
        this.behindItem = behindItem;
        this.confidence = confidence;
    }
}
