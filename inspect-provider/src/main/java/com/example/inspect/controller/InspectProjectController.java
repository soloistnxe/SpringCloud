package com.example.inspect.controller;

import com.example.inspect.dao.InspectProjectDao;
import com.example.inspect.entity.InspectionProject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/1/11 11:55
 */
@RestController
public class InspectProjectController {

    @Resource
    private InspectProjectDao inspectProjectDao;

    /*@RequestMapping("/findAll")
    List<InspectionProject> list() {
        List<InspectionProject> all = inspectProjectDao.findAll();
        return all;
    }*/
}
