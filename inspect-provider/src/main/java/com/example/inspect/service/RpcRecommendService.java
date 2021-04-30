package com.example.inspect.service;

import com.example.inspect.Arithmetic.knn.KNN;
import com.example.inspect.Arithmetic.knn.KNNData;
import com.example.inspect.common.Result;
import com.example.inspect.dao.InspectWorkDao;
import com.example.inspect.entity.InspectWork;
import com.example.inspect.entity.vo.InspectWorkVo;
import com.example.inspect.entity.vo.ScoreDetail;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/4/27 12:12
 */
@Service
public class RpcRecommendService {
    @Resource
    private InspectWorkService inspectWorkService;

    @Resource
    private ReportService reportService;
    @Resource
    private InspectWorkDao inspectWorkDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Result recommend(Integer companyId){
        Result result = new Result();
        Map<String, Object> res = new HashMap<>();
        try {
            InspectWork sample = inspectWorkDao.findByCompanyId(companyId);
            if(sample!=null){
                InspectWorkVo inspectWorkVo = convertToWorkVo(sample);
                if(inspectWorkVo.getInspectScoreDetail().isEmpty()){
                    result.setCode(404);
                    result.setMessage("未查询到该用户所在公司的检查信息，无法进行推荐，请使用随机答题");
                    result.setSuccess(false);
                    return result;
                }else {
                    // 判断是否包含不合格的项目，选择推荐方案
                    Boolean flag = inspectWorkVo.containUnqualified();
                    // 不包含不合格项目，走KNN算法推荐
                    if(!flag){
                        List<String> knnRecommend = getKnnRecommend(inspectWorkVo);
                        res.put("recommend",knnRecommend);
                        result.setCode(200);
                        result.setMessage("Knn算法推荐成功");
                        result.setData(res);
                    }else{
                        // 获取不合格的项目列表
                        List<String> unqualifiedProject = inspectWorkVo.findUnqualifiedProject();
                        //获取Apriori推荐算法结果
                        Result aprioriReport = reportService.getAprioriReport();
                        Map<String,String> associationRules = (Map<String, String>) aprioriReport.getData().get("rpcRecommend");
                        List<String> aprioriRecommend = getAprioriRecommend(unqualifiedProject, associationRules);
                        res.put("recommend",aprioriRecommend);
                        result.setCode(200);
                        result.setMessage("Apriori算法推荐成功");
                        result.setData(res);
                    }
                }
            }else {
                result.setCode(404);
                result.setMessage("未查询到该用户所在公司的检查信息，无法进行推荐，请使用随机答题");
                result.setSuccess(false);
                return result;
            }

        }catch (Exception e){
            result.setCode(500);
            result.setMessage("服务器异常"+e);
            result.setSuccess(false);
            logger.error("服务器异常"+e);
        }

    return result;
    }

    private List<String> getKnnRecommend(InspectWorkVo inspectWorkVo){
        KNNData sample = convertToKnnSample(inspectWorkVo);
        List<KNNData> knnData = inspectWorkService.getKnnData();
        String recommend = KNN.knnCal(3, sample, knnData);
        String[] split = recommend.split(";");
        return Arrays.asList(split);
    }

    private InspectWorkVo convertToWorkVo(InspectWork inspectWork) {
        InspectWorkVo inspectWorkVo = new InspectWorkVo();
        inspectWorkVo.setWorkId(inspectWork.getWorkId());
        inspectWorkVo.setCompanyName(inspectWork.getCompanyName());
        inspectWorkVo.setCompanyId(inspectWork.getCompanyId());
        inspectWorkVo.setProductType(inspectWork.getProductType());
        inspectWorkVo.setAuditType(inspectWork.getAuditType());
        String inspectScore = inspectWork.getInspectScore();
        if (StringUtils.isNotEmpty(inspectScore)) {
            Integer num = 0;
            String[] split = inspectScore.split("#");
            List<ScoreDetail> list = new ArrayList<>();
            for (String s : split) {
                String[] score = s.split(":");
                ScoreDetail scoreDetail = new ScoreDetail();
                scoreDetail.setProject(score[0]);
                scoreDetail.setScore(Integer.parseInt(score[1]));
                num += Integer.parseInt(score[1]);
                list.add(scoreDetail);
            }
            inspectWorkVo.setInspectScoreDetail(list);
            inspectWorkVo.setScore(num);
        }
        return inspectWorkVo;
    }
    // 转换为KNN算法的样本格式
    private KNNData convertToKnnSample(InspectWorkVo inspectWorkVo){
        List<ScoreDetail> inspectScoreDetail = inspectWorkVo.getInspectScoreDetail();
        List<Double> knndata = new ArrayList<>();
        for (ScoreDetail scoreDetail : inspectScoreDetail) {
            knndata.add(Double.parseDouble(scoreDetail.getScore().toString()));
        }
        return new KNNData(knndata,"N/A");
    }
    // 获取最终推荐列表
    private List<String> getAprioriRecommend(List<String> unqualifiedProject,Map<String,String> associationRules){
        // 满足推荐规则的结果集
        List<String> satisfy = new LinkedList<>();
        Set<String> rules = associationRules.keySet();
        System.out.println("=================++++++++++++++");
        System.out.println(unqualifiedProject.toString());
        System.out.println(associationRules.toString());
        for (String rule : rules) {
            List<String> list = Arrays.asList(StringUtils.strip(rule,"[]").split(","));
            if(isContainRules(unqualifiedProject,list)){
                satisfy.addAll(Arrays.asList(associationRules.get(rule)));
                System.out.println("推荐集++++");
                System.out.println(satisfy);
            }
        }
        unqualifiedProject.addAll(satisfy);
        // 去重
        HashSet res = new HashSet(unqualifiedProject);
        List<String> aprioriRecommend = new LinkedList<>();
        aprioriRecommend.addAll(res);
        return aprioriRecommend;
    }
    // 项集比较算法。判断项集中是否完全包含符合强关联规则的项
    private boolean isContainRules(List<String> unqualifiedProject,List<String> rules){
        // 取交集，不改变原有集合
        List<String> intersection = unqualifiedProject.stream().filter(item -> rules.contains(item)).collect(toList());
        // 判断交集是否和规则相同
        boolean equals = intersection.stream().sorted().collect(Collectors.joining())
                .equals(rules.stream().sorted().collect(Collectors.joining()));
        return equals;
    }


    public static void main(String[] args) {
        List<String> str1 = new ArrayList<>();
        List<String> str2 = new ArrayList<>();

        str1.add("进货查验结果");
        str1.add("不合格品管理和食品召回");



        str2.add("不合格品管理和食品召回");
        str2.add("进货查验结果");


        List<String> intersection = str1.stream().filter(item -> str2.contains(item)).collect(toList());
        boolean equals = intersection.stream().sorted().collect(Collectors.joining())
                .equals(str2.stream().sorted().collect(Collectors.joining()));
        System.out.println(intersection.toString());
        System.out.println(equals);
        System.out.println(str1.toString());
        System.out.println(str2.toString());


    }
}