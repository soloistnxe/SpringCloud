package com.example.inspect.service;

import com.example.inspect.common.Result;
import com.example.inspect.dao.InspectProjectDao;
import com.example.inspect.entity.InspectProject;
import com.example.inspect.entity.InspectProjectType;
import com.example.inspect.entity.dto.InspectProjectDto;
import com.example.inspect.entity.vo.InspectProjectVo;
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
 * @date 2021/1/19 15:49
 */
@Service
public class InspectProjectService {
    @Resource
    private InspectProjectDao inspectProjectDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Result findPage(Integer pageNum, Integer pageSize, String query) {
        Result result = new Result();
        Map<String, Object> map = new HashMap<>();
        List<InspectProjectDto> list = null;
        List<InspectProjectVo> inspectProjectVos = null;
        try {
            if (pageNum > 0 && pageSize > 0) {
                list = inspectProjectDao.findPage((pageNum - 1) * pageSize, pageSize, query);
                inspectProjectVos = convertToVo(list);
            }
            Integer total = inspectProjectDao.findPageCount(query);
            if (!list.isEmpty()) {
                map.put("inspectProject", inspectProjectVos);
                map.put("total", total);
                result.setData(map);
                result.setMessage("巡检项目列表查询成功");
                logger.info("巡检项目列表查询成功");
            } else {
                result.setSuccess(false);
                result.setCode(404);
                result.setMessage("巡检项目列表查询失败");
                logger.error("巡检项目列表查询失败");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("服务器异常" + e);
            logger.error("服务器异常" + e);
        }
        return result;
    }

    public Result deleteProject(Integer id) {
        Result result = new Result();
        try {
            boolean delete = inspectProjectDao.delete(id);
            if (delete) {
                result.setMessage("删除巡检项目成功");
                logger.info("删除巡检项目成功");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("删除巡检项目失败" + e);
            logger.info("删除巡检项目失败");
        }
        return result;
    }

    public Result insertProject(InspectProjectVo inspectProjectVo) {
        Result result = new Result();
        try {
            InspectProject inspectProject = convertToPo(inspectProjectVo);
            boolean insert = inspectProjectDao.insertProject(inspectProject);
            if (insert) {
                result.setMessage("添加巡检项目成功");
                logger.info("添加巡检项目成功");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("添加巡检项目失败" + e);
            logger.info("添加巡检项目失败");
        }
        return result;
    }

    public Result updateProject(InspectProjectVo inspectProjectVo) {
        Result result = new Result();
        try {
            InspectProject inspectProject = convertToPo(inspectProjectVo);
            boolean update = inspectProjectDao.updateProject(inspectProject);
            if (update) {
                result.setMessage("更新巡检项目成功");
                logger.info("更新巡检项目成功");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("更细巡检项目失败" + e);
            logger.info("更新巡检项目失败");
        }
        return result;
    }

    public Result findProjectType() {
        Result result = new Result();
        Map<String, Object> map = new HashMap<>();
        List<InspectProjectType> inspectProjectType = null;
        try {
            inspectProjectType = inspectProjectDao.findProjectType();
            map.put("inspectProjectType", inspectProjectType);
            result.setData(map);
            result.setMessage("巡检项目类型列表查询成功");
            logger.info("巡检项目类型列表查询成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("巡检项目类型列表查询失败" + e);
            logger.info("巡检项目类型列表查询失败");
        }
        return result;
    }

    public Result insertProjectType(InspectProjectType inspectProjectTyp) {
        Result result = new Result();
        try {
            boolean insertProjectType = inspectProjectDao.insertProjectType(inspectProjectTyp);
            if (insertProjectType) {
                result.setMessage("巡检项目类型列表插入成功");
                logger.info("巡检项目类型列表插入成功");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("巡检项目类型列表插入失败" + e);
            logger.info("巡检项目类型列表插入失败");
        }
        return result;
    }

    public Result deleteProjectType(Integer projectTypeId) {
        Result result = new Result();
        try {
            boolean deleteProjectType = inspectProjectDao.deleteProjectType(projectTypeId);
            if (deleteProjectType) {
                result.setMessage("巡检项目类型删除成功");
                logger.info("巡检项目类型删除成功");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("巡检项目类型删除失败" + e);
            logger.info("巡检项目类型删除失败");
        }
        return result;
    }

    public Result updateProjectType(InspectProjectType inspectProjectType) {
        Result result = new Result();
        try {
            boolean updateProjectType = inspectProjectDao.updateProjectType(inspectProjectType);
            if (updateProjectType) {
                result.setMessage("巡检项目类型更新成功");
                logger.info("巡检项目类型更新成功");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("巡检项目类型更新失败" + e);
            logger.info("巡检项目类型更新失败");
        }
        return result;
    }

    private List<InspectProjectVo> convertToVo(List<InspectProjectDto> list) {
        List<InspectProjectVo> res = new ArrayList<>();
        for (InspectProjectDto inspectProjectDto : list) {
            InspectProjectVo inspectProjectVo = new InspectProjectVo();
            inspectProjectVo.setProjectId(inspectProjectDto.getId());
            inspectProjectVo.setProjectTypeId(inspectProjectDto.getProjectTypeId());
            inspectProjectVo.setProjectTypeName(inspectProjectDto.getProjectTypeName());
            inspectProjectVo.setProjectContent(inspectProjectDto.getProjectContent());
            String projectOption = inspectProjectDto.getProjectOption();
            if (StringUtils.isNotEmpty(projectOption)) {
                String[] split = projectOption.split("#");
                inspectProjectVo.setProjectOptionList(Arrays.asList(split));
            }
            res.add(inspectProjectVo);
        }
        return res;
    }

    private InspectProject convertToPo(InspectProjectVo inspectProjectVo) {
        InspectProject inspectProject = new InspectProject();
        inspectProject.setId(inspectProjectVo.getProjectId());
        inspectProject.setProjectContent(inspectProjectVo.getProjectContent());
        inspectProject.setProjectTypeId(inspectProjectVo.getProjectTypeId());
        List<String> projectOptionList = inspectProjectVo.getProjectOptionList();
        if (!projectOptionList.isEmpty()) {
            String projectOpetion = "";
            for (String str : projectOptionList) {
                projectOpetion += str + "#";
            }
            inspectProject.setProjectOption(projectOpetion);
        }
        return inspectProject;
    }
}
