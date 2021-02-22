package com.example.inspect.dao;

import com.example.inspect.entity.InspectProject;
import com.example.inspect.entity.InspectProjectType;
import com.example.inspect.entity.dto.InspectProjectDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InspectProjectDao {

    List<InspectProjectDto> findPage(Integer start, Integer limit,@Param("query") String query);

    Integer findPageCount(String query);

    boolean delete(Integer id);

    boolean insertProject(InspectProject inspectProject);

    boolean updateProject(InspectProject inspectProject);

    List<InspectProjectType> findProjectType();

    boolean insertProjectType(InspectProjectType inspectProjectType);

    boolean deleteProjectType(Integer projectTypeId);

    boolean updateProjectType (InspectProjectType inspectProjectType);
}
