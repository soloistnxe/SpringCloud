package com.example.train.dao;

import com.example.train.entity.dto.QuestionDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionDao {

    List<QuestionDto> findPage(Integer start, Integer limit, @Param("query") String query);

    Integer findPageCount(String query);
}
