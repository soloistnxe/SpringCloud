package com.example.inspect.entity.vo;

import lombok.Data;

import java.util.ArrayList;
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

    private Integer companyId;

    private String productType;

    private List<String> detail;

    private List<ScoreDetail> inspectScoreDetail;

    private Integer score;

    private Integer auditType;

    /**
     * 判断检查结果中是否包含有不合格的项
     * @return
     */
    public Boolean containUnqualified(){
        boolean flag = false;
        for (ScoreDetail scoreDetail : inspectScoreDetail) {
            if(scoreDetail.getScore()<6){
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 获取不合格的项目
     * @return
     */
    public List<String> findUnqualifiedProject(){
        List<String> unqualified = new ArrayList<>();
        for (ScoreDetail scoreDetail : inspectScoreDetail) {
            if(scoreDetail.getScore()<6){
                unqualified.add(scoreDetail.getProject());
            }
        }
        return unqualified;
    }
}
