package com.example.inspect.service;

import com.example.inspect.common.Result;
import com.example.inspect.dao.InspectWorkDao;
import com.example.inspect.entity.InspectWork;
import com.example.inspect.entity.vo.InspectWorkVo;
import com.example.inspect.entity.vo.ScoreDetail;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/2/10 18:16
 */
@Service
public class InspectWorkService {
    @Resource
    private InspectWorkDao inspectWorkDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Result findPage(Integer pageNum, Integer pageSize, String query) {
        Result result = new Result();
        Map<String, Object> map = new HashMap<>();
        List<InspectWork> inspectWorkList = null;
        List<InspectWorkVo> inspectWorkVoList = null;
        try {
            if (pageNum > 0 && pageSize > 0) {
                inspectWorkList = inspectWorkDao.findPage((pageNum - 1) * pageSize, pageSize, query);
                inspectWorkVoList = convertToWorkVo(inspectWorkList);
            }
            Integer total = inspectWorkDao.findPageCount(query);
            if (!inspectWorkList.isEmpty()) {
                map.put("inspectWork", inspectWorkVoList);
                map.put("total", total);
                result.setData(map);
                result.setMessage("检查工作列表查询成功");
                logger.info("检查工作列表查询成功");
            } else {
                result.setSuccess(false);
                result.setCode(404);
                result.setMessage("检查工作列表查询失败");
                logger.error("检查工作列表查询失败");
            }

        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("检查工作列表查询失败" + e);
            logger.error("检查工作列表查询失败" + e);
        }
        return result;
    }

    public Result insertWork(InspectWork inspectWork) {
        Result result = new Result();
        try {
            Boolean insert = inspectWorkDao.insertWork(inspectWork);
            if (insert) {
                result.setMessage("添加检查工作成功");
                logger.info("添加检查工作成功");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("添加检查工作目失败" + e);
            logger.error("添加检查工作失败" + e);
        }
        return result;
    }

    public Result updateWork(InspectWorkVo inspectWorkVo) {
        Result result = new Result();
        try {
            InspectWork inspectWork = convertToPo(inspectWorkVo);
            Boolean updateWork = inspectWorkDao.updateWork(inspectWork);
            if (updateWork) {
                result.setMessage("审核检查工作成功");
                logger.info("审核检查工作成功");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("审核检查工作目失败" + e);
            logger.error("审核检查工作失败" + e);
        }
        return result;
    }

    public Result deleteWork(Integer workId) {
        Result result = new Result();
        try {
            Boolean deleteWork = inspectWorkDao.deleteWork(workId);
            if (deleteWork) {
                result.setMessage("删除检查工作成功");
                logger.info("删除检查工作成功");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("删除检查工作失败" + e);
            logger.error("删除检查工作失败" + e);
        }
        return result;
    }

    private List<InspectWorkVo> convertToWorkVo(List<InspectWork> inspectWorkList) {
        List<InspectWorkVo> res = new ArrayList<>();
        for (InspectWork inspectWork : inspectWorkList) {
            InspectWorkVo inspectWorkVo = new InspectWorkVo();
            inspectWorkVo.setWorkId(inspectWork.getWorkId());
            inspectWorkVo.setCompanyName(inspectWork.getCompanyName());
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
            res.add(inspectWorkVo);
        }
        return res;
    }

    private InspectWork convertToPo(InspectWorkVo inspectWorkVo) {
        InspectWork inspectWork = new InspectWork();
        inspectWork.setWorkId(inspectWorkVo.getWorkId());
        inspectWork.setCompanyName(inspectWorkVo.getCompanyName());
        inspectWork.setProductType(inspectWorkVo.getProductType());
        inspectWork.setAuditType(inspectWorkVo.getAuditType());
        List<ScoreDetail> inspectScoreDetail = inspectWorkVo.getInspectScoreDetail();
        String score = "";
        for (ScoreDetail scoreDetail : inspectScoreDetail) {
            score += scoreDetail.getProject() + ":" + scoreDetail.getScore() + "#";
        }
        inspectWork.setInspectScore(score);
        return inspectWork;
    }

    public Map<String, List<String>> unqualified() {
        Map<String, List<String>> map = new LinkedHashMap<>();
        try {
            List<InspectWork> all = inspectWorkDao.findAll();
            List<InspectWorkVo> inspectWorkVoList = convertToWorkVo(all);
            map = convert(inspectWorkVoList);
        } catch (Exception e) {
            logger.error("数据查询异常" + e);
        }
        return map;
    }

    private Map<String, List<String>> convert(List<InspectWorkVo> inspectWorkVos) {
        Map<String, List<String>> map = new LinkedHashMap<>();
        for (InspectWorkVo inspectWorkVo : inspectWorkVos) {
            List<String> project = new ArrayList<>();
            for (ScoreDetail scoreDetail : inspectWorkVo.getInspectScoreDetail()) {
                if (scoreDetail.getScore() < 6) {
                    project.add(scoreDetail.getProject());
                }
            }
            map.put(inspectWorkVo.getCompanyName(), project);
        }
        return map;
    }
}
