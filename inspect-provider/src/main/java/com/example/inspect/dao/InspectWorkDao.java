package com.example.inspect.dao;

import com.example.inspect.entity.InspectWork;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InspectWorkDao {

    List<InspectWork> findPage(Integer start, Integer limit, String query);

    Integer findPageCount(String query);

    Boolean insertWork(InspectWork inspectWork);

    Boolean updateWork(InspectWork inspectWork);

    Boolean deleteWork(Integer workId);

}
