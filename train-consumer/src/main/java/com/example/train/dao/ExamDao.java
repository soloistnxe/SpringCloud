package com.example.train.dao;

import com.example.train.entity.Question;
import com.example.train.entity.Record;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExamDao {

    List<Question> findAll();

    Boolean insertRecord(Record record);

    List<Question> findList(List<String> list);
}
