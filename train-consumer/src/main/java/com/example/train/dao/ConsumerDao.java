package com.example.train.dao;

import com.example.train.entity.Consumer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ConsumerDao {

    List<Consumer> findAll();

    Consumer findByName(String consumerName);

    List<Consumer> findPage(Integer start, Integer limit, @Param("query") String query);

    Integer findPageCount(String query);

    Boolean insertConsumer(Consumer consumer);

    Boolean update(Consumer consumer);

    Boolean delete(Integer consumerId);
}
