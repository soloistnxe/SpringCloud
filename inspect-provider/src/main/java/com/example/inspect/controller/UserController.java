package com.example.inspect.controller;

import com.example.inspect.dao.UserDao;
import com.example.inspect.entity.Users;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController()
public class UserController {
    @Resource
    UserDao userDao;

    @RequestMapping(value = "/find")
    public List<Users> findAll(){

        List<Users> all = userDao.findAll();

        for (Users users : all) {
            System.out.println(users);
        }
        return all;
    }
}
