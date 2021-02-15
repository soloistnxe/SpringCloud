package com.example.inspect.controller;

import com.example.inspect.common.Result;
import com.example.inspect.entity.InspectProject;
import com.example.inspect.entity.InspectProjectType;
import com.example.inspect.entity.vo.InspectProjectVo;
import com.example.inspect.service.InspectProjectService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/1/11 11:55
 */
@RestController()
@RequestMapping("/project")
public class InspectProjectController {

    @Resource
    private InspectProjectService inspectProjectService;

    /*@RequestMapping("/findAll")
    List<InspectionProject> list() {
        List<InspectionProject> all = inspectProjectDao.findAll();
        return all;
    }*/
    @RequestMapping(value = "/findPage")
    public Result findProject(@RequestParam(value="pageNum") Integer pageNum,
                               @RequestParam(value="pageSize") Integer pageSize,
                              @RequestParam(value="query") String query){
        return inspectProjectService.findPage(pageNum,pageSize,query);
    }

    @RequestMapping(value = "/deleteProject/{id}")
    public Result deleteProject(@PathVariable(value = "id") Integer id){
        System.out.println(id);
        return inspectProjectService.deleteProject(id);
    }
    @RequestMapping(value = "/insertProject",method = RequestMethod.POST)
    public Result insertProject(InspectProjectVo inspectProjectVo){
        return inspectProjectService.insertProject(inspectProjectVo);
    }
    @RequestMapping("/updateProject")
    public Result updateProject(InspectProjectVo inspectProjectVo){
        return inspectProjectService.updateProject(inspectProjectVo);
    }
    @RequestMapping("/findProjectType")
    public Result findProjectType(){
        return inspectProjectService.findProjectType();
    }
    @RequestMapping("/insertProjectType")
    public Result insertProjectType(InspectProjectType inspectProjectType){
        return inspectProjectService.insertProjectType(inspectProjectType);
    }
    @RequestMapping("/deleteProjectType")
    public Result deleteProjectType(Integer projectTypeId){
        return inspectProjectService.deleteProjectType(projectTypeId);
    }
    @RequestMapping("/updateProjectType")
    public Result updateProjectType (InspectProjectType inspectProjectType){
        return inspectProjectService.updateProjectType(inspectProjectType);
    }
}
