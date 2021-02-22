package com.example.inspect.dao;

import com.example.inspect.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {

    List<Users> findAll();

    List<Users> findPage(Integer start, Integer limit,@Param("query") String query);

    Integer findPageCount(String query);

    Users findByName(String userName);

    Users findById(Integer userId);

    boolean update(Users users);

    boolean insert(Users users);

    boolean delete(Integer userId);
}
