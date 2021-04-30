package com.example.train.service;

import com.example.train.common.Result;
import com.example.train.dao.QuestionDao;
import com.example.train.entity.Consumer;
import com.example.train.entity.dto.QuestionDto;
import com.example.train.entity.vo.QuestionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/4/1 20:59
 */
@Service
public class QuestionService {
    @Resource
    private QuestionDao questionDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Result findPage(Integer pageNum, Integer pageSize, String query){
        Result result = new Result();
        Map<String, Object> map = new HashMap<>();
        List<QuestionDto> questionDtoList = new ArrayList<>();
        try {
            if (pageNum > 0 && pageSize > 0) {
                questionDtoList = questionDao.findPage((pageNum - 1) * pageSize, pageSize, query);
            }
            Integer total = questionDao.findPageCount(query);
            if (!questionDtoList.isEmpty()) {
                List<QuestionVo> questionVos = convertToQuestionVo(questionDtoList);
                map.put("question", questionVos);
                map.put("total", total);
                result.setData(map);
                result.setMessage("题目列表查询成功");
                logger.info("题目列表查询成功");
            } else {
                result.setSuccess(false);
                result.setCode(404);
                result.setMessage("题目列表查询失败");
                logger.error("题目列表查询失败");
            }

        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("题目列表查询失败" + e);
            logger.error("题目列表查询失败" + e);
        }
        return result;
    }

    private List<QuestionVo> convertToQuestionVo(List<QuestionDto> questionDtos){
        List<QuestionVo> questionVos = new ArrayList<>();
        for (QuestionDto questionDto : questionDtos) {
            QuestionVo questionVo = new QuestionVo();
            questionVo.setQuestionId(questionDto.getQuestionId());
            questionVo.setQuestionName(questionDto.getQuestionName());
            questionVo.setAnswer(questionDto.getAnswer());
            questionVo.setQuestionTypeId(questionDto.getQuestionTypeId());
            questionVo.setQuestionTypeName(questionDto.getQuestionTypeName());
            String[] split = questionDto.getQuestionOption().split("#");
            List<String> questionOption = new ArrayList<>();
            for (String s : split) {
                questionOption.add(s);
            }
            questionVo.setQuestionOption(questionOption);
            questionVos.add(questionVo);
        }
        return questionVos;
    }
}
