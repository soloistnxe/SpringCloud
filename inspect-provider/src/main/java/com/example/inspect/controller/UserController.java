package com.example.inspect.controller;

import com.example.inspect.common.Result;
import com.example.inspect.entity.InspectionProject;
import com.example.inspect.entity.Users;
import com.example.inspect.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController()
public class UserController {
    @Resource
    private UserService userService;



    @RequestMapping("/findPage")
    public Result list(@RequestParam(value="pageNum") Integer pageNum,
                       @RequestParam(value="pageSize") Integer pageSize,
                       @RequestParam(value="query") String query) {
        return userService.findPage(pageNum,pageSize,query);
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(String username, String password) {
        return userService.userLogin(username,password);
    }
    @RequestMapping(value = "/findUserById/{userId}")
    public Result findUserById(@PathVariable(value = "userId") Integer userId){
        return userService.findUserById(userId);
    }
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Result insert(Users users){
        return userService.insert(users);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(Users users){
        return userService.update(users);
    }

    @RequestMapping(value = "/delete/{userId}")
    public Result delete(@PathVariable(value = "userId") Integer userId){
        return userService.deleteUser(userId);
    }
}
