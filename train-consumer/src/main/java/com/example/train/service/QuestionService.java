package com.example.train.service;

import com.example.train.common.Result;
import com.example.train.dao.QuestionDao;
import com.example.train.entity.Question;
import com.example.train.entity.QuestionType;
import com.example.train.entity.dto.QuestionDto;
import com.example.train.entity.vo.QuestionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

    public Result findPage(Integer pageNum, Integer pageSize, String query) {
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

    public Result findQuestionType(){
        Result result = new Result();
        Map<String, Object> map = new HashMap<>();
        try {
            List<QuestionType> questionType = questionDao.findQuestionType();
            if(!questionType.isEmpty()){
                map.put("questionType",questionType);
                result.setData(map);
                result.setMessage("查询题目类型列表成功");
                logger.info("查询题目类型列表成功");
            }else {
                result.setCode(404);
                result.setMessage("查询题目类型列表为空");
                logger.warn("查询题目类型列表为空");
            }
        }catch (Exception e){
            result.setCode(500);
            result.setMessage("查询题目类型列表失败" +e);
            logger.error("查询题目类型列表失败"+e);
        }
        return result;
    }

    public Result insertQuestion(QuestionVo questionVo) {
        Result result = new Result();
        Question question = convertToPo(questionVo);
        try {
            Boolean insertQuestion = questionDao.insertQuestion(question);
            if (insertQuestion) {
                result.setMessage("题目插入成功");
                logger.info("题目插入成功");
            }else {
                result.setSuccess(false);
                result.setCode(404);
                result.setMessage("题目插入失败");
                logger.error("题目插入失败");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("题目插入失败,服务器异常" + e);
            logger.error("题目插入失败,服务器异常" + e);
        }
        return result;
    }

    public Result updateQuestion(QuestionVo questionVo){
        Result result = new Result();
        Question question = convertToPo(questionVo);
        try {
            Boolean updateQuestion = questionDao.updateQuestion(question);
            if (updateQuestion) {
                result.setMessage("题目修改成功");
                logger.info("题目修改成功");
            }else {
                result.setSuccess(false);
                result.setCode(404);
                result.setMessage("题目修改失败");
                logger.error("题目修改失败");
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("题目修改失败,服务器异常" + e);
            logger.error("题目修改失败,服务器异常" + e);
        }
        return result;
    }

    public Result deleteQuestion(Integer questionId){
        Result result = new Result();
        try {
            Boolean deleteQuestion = questionDao.deleteQuestion(questionId);
            if (deleteQuestion) {
                result.setMessage("题目删除成功");
                logger.info("题目删除成功");
            }else {
                result.setSuccess(false);
                result.setCode(404);
                result.setMessage("题目删除失败");
                logger.error("题目删除失败");
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("题目删除失败,服务器异常" + e);
            logger.error("题目删除失败,服务器异常" + e);
        }
        return result;
    }


    private List<QuestionVo> convertToQuestionVo(List<QuestionDto> questionDtos) {
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

    private Question convertToPo(QuestionVo questionVo) {
        Question question = new Question();
        question.setQuestionId(questionVo.getQuestionId());
        question.setQuestionName(questionVo.getQuestionName());
        List<String> questionOption = questionVo.getQuestionOption();
        StringBuilder option = new StringBuilder();
        for (String s : questionOption) {
            option.append(s).append("#");
        }
        question.setQuestionOption(option.toString());
        question.setQuestionType(questionVo.getQuestionTypeId());
        question.setAnswer(questionVo.getAnswer());
        return question;
    }

}
