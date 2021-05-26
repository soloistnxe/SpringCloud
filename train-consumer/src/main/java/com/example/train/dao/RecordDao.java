package com.example.train.dao;

import com.example.train.entity.Rank;
import com.example.train.entity.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/5/24 10:46
 */
@Mapper
public interface RecordDao {

    List<Record> findPage(@Param("consumerId") Integer consumerId,Integer start, Integer limit, @Param("startDate") String startDate,@Param("endDate") String endDate);

    Integer findPageCount(@Param("consumerId") Integer consumerId,@Param("startDate") String startDate,@Param("endDate") String endDate);

    List<Rank> findRank();
}
