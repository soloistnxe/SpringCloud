package com.example.inspect.service;

import com.example.inspect.common.Result;
import com.example.inspect.dao.UserDao;
import com.example.inspect.entity.Users;
import com.example.inspect.utils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/1/14 11:06
 */
@Service
public class UserService {
    @Resource
    private UserDao userDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public Result findPage(Integer pageNum, Integer pageSize, String query) {
        Result result = new Result();
        Map<String, Object> map = new HashMap<>();
        List<Users> users = null;
        try {
            if (pageNum > 0 && pageSize > 0) {
                users = userDao.findPage((pageNum - 1) * pageSize, pageSize, query);
            }
            Integer total = userDao.findPageCount(query);
            map.put("users", users);
            map.put("total", total);
            result.setData(map);
            result.setMessage("用户列表查询成功");
            logger.info("用户列表查询成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("服务器异常" + e);
            logger.error("服务器异常" + e);
        }
        return result;

    }

    public Result findUserById(Integer userId){
        Result result = new Result();
        Map<String, Object> map = new HashMap<>();

        try {
            Users user = userDao.findById(userId);
            if(user!=null){
                map.put("user", user);
                result.setData(map);
                result.setMessage("用户查询成功");
                logger.info("用户查询成功");
            }else{
                result.setSuccess(false);
                result.setCode(404);
                result.setMessage("查询用户失败");
                logger.error("查询用户失败");
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("服务器异常" + e);
            logger.error("服务器异常" + e);
        }
        return result;
    }

    public Result userLogin(String userName, String password) {
        Result result = new Result();
        Map<String, Object> map = new HashMap<>();
        try {
            Users user = userDao.findByName(userName);
            if (password.equals(user.getPassword())) {
                String token = TokenUtil.getToken(user.getUserId(), user.getPassword());
                map.put("user", user);
                map.put("token", token);
                result.setMessage("登录成功");
                logger.info("登录成功");
                result.setData(map);
            } else {
                result.setSuccess(false);
                result.setCode(404);
                result.setMessage("登录失败，找不到用户");
                logger.error("登录失败，找不到用户");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("服务器异常");
            logger.error("服务器异常" + e);
        }
        return result;
    }

    public Result insert(Users users) {
        Result result = new Result();
        try {
            boolean insert = userDao.insert(users);
            if (insert) {
                result.setMessage("用户创建成功");
                logger.info("用户创建成功");
            } else {
                result.setSuccess(false);
                result.setCode(500);
                result.setMessage("用户创建失败");
                logger.info("用户创建失败");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("创建失败" + e);
            logger.info("创建失败");
        }
        return result;
    }

    public Result update(Users users) {
        Result result = new Result();
        try {
            boolean update = userDao.update(users);
            if (update) {
                result.setMessage("修改成功");
                logger.info("用户修改成功");
            } else {
                result.setSuccess(false);
                result.setCode(500);
                result.setMessage("修改失败");
                logger.info("用户修改失败");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("修改失败" + e);
            logger.info("修改密码失败");
        }
        return result;
    }

    public Result deleteUser(Integer userId) {
        Result result = new Result();
        try {
            boolean delete = userDao.delete(userId);
            if (delete) {
                result.setMessage("删除成功");
                logger.info("删除成功");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("删除失败" + e);
            logger.info("删除失败");
        }
        return result;
    }

}
