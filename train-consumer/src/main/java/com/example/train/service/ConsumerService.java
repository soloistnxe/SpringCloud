package com.example.train.service;

import com.example.train.common.Result;
import com.example.train.dao.ConsumerDao;
import com.example.train.entity.Consumer;
import com.example.train.utils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/3/5 9:36
 */
@Service
public class ConsumerService {
    @Resource
    private ConsumerDao consumerDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Result login(String consumerName, String password) {
        Result result = new Result();
        Map<String, Object> map = new HashMap<>();
        try {
            Consumer consumer = consumerDao.findByName(consumerName);
            if (password.equals(consumer.getPassword())) {
                String token = TokenUtil.getToken(consumer.getConsumerId(), consumer.getPassword());
                map.put("consumer", consumer);
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
            result.setMessage("服务器异常"+e);
            logger.error("服务器异常" + e);
        }
        return result;
    }

    public Result findPage(Integer pageNum, Integer pageSize, String query) {
        Result result = new Result();
        Map<String, Object> map = new HashMap<>();
        List<Consumer> consumerList = new ArrayList<>();
        try {
            if (pageNum > 0 && pageSize > 0) {
                consumerList = consumerDao.findPage((pageNum - 1) * pageSize, pageSize, query);
            }
            Integer total = consumerDao.findPageCount(query);
            if (!consumerList.isEmpty()) {
                map.put("consumer", consumerList);
                map.put("total", total);
                result.setData(map);
                result.setMessage("用户列表查询成功");
                logger.info("用户作列表查询成功");
            } else {
                result.setSuccess(false);
                result.setCode(404);
                result.setMessage("用户列表查询失败");
                logger.error("用户列表查询失败");
            }

        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("用户列表查询失败" + e);
            logger.error("用户列表查询失败" + e);
        }
        return result;
    }

    public Result insertConsumer(Consumer consumer){
        Result result = new Result();
        try {
            Boolean insertConsumer = consumerDao.insertConsumer(consumer);
            if(insertConsumer){
                result.setMessage("用户创建成功");
                logger.info("用户创建成功");
            }else {
                result.setSuccess(false);
                result.setCode(500);
                result.setMessage("用户创建失败");
                logger.info("用户创建失败");
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("创建失败" + e);
            logger.info("创建失败");
        }
        return result;
    }

    public Result update(Consumer consumer){
        Result result = new Result();
        try {
            Boolean update = consumerDao.update(consumer);
            if(update){
                result.setMessage("修改用户成功");
                logger.info("修改用户成功");
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("修改用户失败" + e);
            logger.info("修改用户失败");
        }
        return result;
    }

    public Result delete(Integer consumerId){
        Result result = new Result();
        try {
            Boolean delete = consumerDao.delete(consumerId);
            if(delete){
                result.setMessage("删除用户成功");
                logger.info("删除用户成功");
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("删除用户失败" + e);
            logger.info("删除用户失败");
        }
        return result;
    }
}
