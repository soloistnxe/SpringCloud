package com.example.train.service;

import com.example.train.common.Result;
import com.example.train.dao.ExamDao;
import com.example.train.entity.Question;
import com.example.train.entity.Record;
import com.example.train.entity.vo.QuestionDetail;
import com.example.train.entity.vo.QuestionVo;
import com.example.train.entity.vo.RecordVo;
import com.example.train.utils.RandomUtil;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/5/14 9:56
 */
@Service
public class ExamService {
    @Resource
    private ExamDao examDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Result randomExam() {
        Result result = new Result();
        Map<String, Object> map = new HashMap<>();
        try {
            List<Question> questionList = examDao.findAll();
            if (!questionList.isEmpty()) {
                List<QuestionVo> questionVos = convertToQuestionVo(questionList);
                List<Integer> index = RandomUtil.getRandomIndex(questionVos.size(), 10);
                List<QuestionVo> res = new ArrayList<>();
                for (Integer i : index) {
                    res.add(questionVos.get(i));
                }
                map.put("questionList", res);
                result.setMessage("随机问卷生成成功");
                logger.info("随机问卷生成成功");
                result.setData(map);
            } else {
                result.setSuccess(false);
                result.setCode(404);
                result.setMessage("随机问卷生成失败，找不到题目");
                logger.error("随机问卷生成失败，找不到题目");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("随机问卷生成失败，服务器异常" + e);
            logger.error("随机问卷生成失败，服务器异常" + e);
        }
        return result;
    }

    /**
     * @param recommendList 推荐题目类型的列表
     * @return
     */
    public Result recommendExam(List<String> recommendList){
        Result result = new Result();
        Map<String, Object> map = new HashMap<>();
        try {
            List<Question> questionList = examDao.findList(recommendList);
            if(!questionList.isEmpty()){
                List<QuestionVo> questionVos = convertToQuestionVo(questionList);
                if(questionVos.size()<10){
                    map.put("questionList", questionVos);
                }else {
                    List<Integer> index = RandomUtil.getRandomIndex(questionVos.size(), 10);
                    List<QuestionVo> res = new ArrayList<>();
                    for (Integer i : index) {
                        res.add(questionVos.get(i));
                    }
                    map.put("questionList", res);
                }
                result.setMessage("推荐问卷生成成功");
                logger.info("推荐问卷生成成功");
                result.setData(map);
            }else {
                result.setSuccess(false);
                result.setCode(404);
                result.setMessage("推荐问卷生成失败，找不到题目");
                logger.error("推荐问卷生成失败，找不到题目");
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("推荐问卷生成失败，服务器异常" + e);
            logger.error("推荐问卷生成失败，服务器异常" + e);
        }
        return result;
    }

    public Result commitExam(RecordVo recordVo) {
        Result result = new Result();
        Record record = convertToPo(recordVo);
        try {
            Boolean insertRecord = examDao.insertRecord(record);
            if (insertRecord) {
                result.setMessage("随机问卷答题交卷成功");
                logger.info("随机问卷答题交卷成功");
            } else {
                result.setCode(404);
                result.setMessage("随机问卷答题交卷失败");
                result.setSuccess(false);
                logger.info("随机问卷答题交卷失败");
            }
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            result.setMessage("随机问卷答题交卷失败" + e);
            logger.info("随机问卷答题交卷失败" + e);
        }
        return result;
    }

    private List<QuestionVo> convertToQuestionVo(List<Question> questions) {
        List<QuestionVo> questionVos = new ArrayList<>();
        for (Question question : questions) {
            QuestionVo questionVo = new QuestionVo();
            questionVo.setQuestionId(question.getQuestionId());
            questionVo.setQuestionName(question.getQuestionName());
            questionVo.setAnswer(question.getAnswer());
            questionVo.setQuestionTypeId(question.getQuestionType());
            String[] split = question.getQuestionOption().split("#");
            List<String> questionOption = new ArrayList<>();
            for (String s : split) {
                questionOption.add(s);
            }
            questionVo.setQuestionOption(questionOption);
            questionVos.add(questionVo);
        }
        return questionVos;
    }

    private Record convertToPo(RecordVo recordVo) {
        Record record = new Record();
        List<QuestionDetail> list = new ArrayList<>();
        double correctCount = 0;
        for (QuestionDetail questionDetail : recordVo.getQuestionDetail()) {
            if (questionDetail.getConsumerOption().equals(questionDetail.getCorrectAnswer())) {
                questionDetail.setIsCorrect("正确");
                correctCount++;
            } else {
                questionDetail.setIsCorrect("错误");
            }
            list.add(questionDetail);

        }
        recordVo.setQuestionDetail(list);
        record.setConsumerId(recordVo.getConsumerId());
        JSONArray listArray=JSONArray.fromObject(recordVo.getQuestionDetail());
        record.setQuestionDetail(listArray.toString());
        record.setMark((int) ((correctCount / recordVo.getQuestionDetail().size()) * 100));
        record.setExamType(recordVo.getExamType());
        record.setTime(new Date());
        return record;

    }
}
