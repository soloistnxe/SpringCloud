package com.example.inspect.dao;

import com.example.inspect.entity.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {

    List<Users> findAll();
}
