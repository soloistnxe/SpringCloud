package com.example.train.dao;

import com.example.train.entity.Question;
import com.example.train.entity.QuestionType;
import com.example.train.entity.dto.QuestionDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionDao {

    List<QuestionDto> findPage(Integer start, Integer limit, @Param("query") String query);

    Integer findPageCount(String query);

    List<QuestionType> findQuestionType();

    Boolean insertQuestion(Question question);

    Boolean updateQuestion(Question question);

    Boolean deleteQuestion(Integer questionId);
}
