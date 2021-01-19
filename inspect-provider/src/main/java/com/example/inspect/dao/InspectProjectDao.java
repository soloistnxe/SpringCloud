package com.example.inspect.dao;

import com.example.inspect.entity.InspectionProject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InspectProjectDao {

    List<InspectionProject> findAll();
}
