package com.example.train.service;

import com.example.train.common.Result;
import com.example.train.dao.RecordDao;
import com.example.train.entity.Consumer;
import com.example.train.entity.Rank;
import com.example.train.entity.Record;
import com.example.train.entity.vo.QuestionDetail;
import com.example.train.entity.vo.RecordVo;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/5/24 10:45
 */
@Service
public class RecordService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private RecordDao recordDao;

    public Result findpage(Integer consumerId,Integer pageNum, Integer pageSize,String startDate, String endDate){
        Result result = new Result();
        Map<String, Object> map = new HashMap<>();
        List<RecordVo> recordVoList = new ArrayList<>();
        try {
            if (pageNum > 0 && pageSize > 0){
                List<Record> list = recordDao.findPage(consumerId,(pageNum - 1) * pageSize, pageSize, startDate, endDate);
                for (Record record : list) {
                    RecordVo recordVo = convertToVo(record);
                    recordVoList.add(recordVo);
                }
            }
            Integer total = recordDao.findPageCount(consumerId,startDate,endDate);
            if(!recordVoList.isEmpty()){
                map.put("recordList",recordVoList);
                map.put("total", total);
                result.setData(map);
                result.setMessage("答题记录列表查询成功");
                logger.info("答题记录作列表查询成功");
            } else {
                result.setSuccess(false);
                result.setCode(200);
                result.setMessage("未搜索到符合条件的记录，查询失败");
                logger.error("未搜索到符合条件的记录，查询失败");
            }

       }catch (Exception e){
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("答题记录列表查询失败" + e);
            logger.error("答题记录列表查询失败" + e);
       }
       return result;
    }

    public Result findRank(){
        Result result = new Result();
        Map<String, Object> map = new HashMap<>();
        DecimalFormat df = new DecimalFormat("0.00");
        List<Rank> list = new ArrayList<>();
        try {
             list = recordDao.findRank();
             if(!list.isEmpty()){
                 for (Rank rank : list) {
                     double res = rank.getSum()/(rank.getCount()*100.0);
                     String format = df.format(res * 100);
                     rank.setAccuracy(format+"%");
                 }
                 map.put("rank",list);
                 result.setData(map);
                 result.setMessage("排名列表查询成功");
                 logger.info("排名列表查询成功");
             }else {
                 result.setMessage("排名列表查询为空");
                 logger.info("排名列表查询为空");
             }
        }catch (Exception e){
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("排名列表查询失败"+e);
            logger.info("排名列表查询失败"+e);
        }
        return result;
    }

    private RecordVo convertToVo(Record record){
        RecordVo recordVo = new RecordVo();
        recordVo.setConsumerId(record.getConsumerId());
        recordVo.setExamType(record.getExamType());
        recordVo.setMark(record.getMark());
        recordVo.setTime(record.getTime());
        String questionDetail = record.getQuestionDetail();
        List<QuestionDetail> list=(List<QuestionDetail>) JSONArray.toList(JSONArray.fromObject(questionDetail), QuestionDetail.class);
        recordVo.setQuestionDetail(list);
        return recordVo;
    }
}
