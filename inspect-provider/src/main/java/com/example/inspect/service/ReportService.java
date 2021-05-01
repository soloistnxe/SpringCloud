package com.example.inspect.service;

import com.example.inspect.Arithmetic.apriori.AprioriMyself;
import com.example.inspect.Arithmetic.apriori.AssociationRules;
import com.example.inspect.Arithmetic.knn.KNN;
import com.example.inspect.Arithmetic.knn.KNNData;
import com.example.inspect.common.Result;
import com.example.inspect.dao.InspectWorkDao;
import com.example.inspect.entity.InspectWork;
import com.example.inspect.entity.Report;
import com.example.inspect.entity.vo.InspectWorkVo;
import com.example.inspect.entity.vo.ScoreDetail;
//import com.example.inspect.utils.ReadCsvFileUtils;
import org.apache.commons.lang.StringUtils;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/2/17 11:17
 */
@Service
public class ReportService {
    @Resource
    private InspectWorkDao inspectWorkDao;
    @Resource
    private InspectWorkService inspectWorkService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Result getPCA() {
        Result result = new Result();
        Map<String, Object> res = new HashMap<>();
        try {
            List<String> list = inspectWorkDao.findAllScore();
            String s1 = list.get(0);
            List<String> names = new LinkedList<>();
            String[] split1 = s1.split("#");
            for (int i = 0; i < split1.length; i++) {
                String[] split = split1[i].split(":");
                names.add("X" + (i + 1)+":"+ split[0]);
            }
            int size = names.size();
            List<String> xAxis = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                xAxis.add("Comp"+(i+1));
            }
            String data = convertToPcaData(list);
            Boolean pca = Pca(data);
            /*if (pca) {
                result.setMessage("主成分分析正常");
                List<String[]> standardDeviations = ReadCsvFileUtils.readCsvFile("D:/PCA/standardDeviations.csv");
                List<String[]> loadings = ReadCsvFileUtils.readCsvFile("D:/PCA/loadings.csv");
                Report report = convertToPCAReport(standardDeviations, loadings);
                res.put("report", report);
                res.put("names", names);
                res.put("xAxis",xAxis);
                result.setData(res);
            } else {
                result.setMessage("主成分分析异常");
                logger.error("主成分分析异常");
                result.setCode(500);
            }*/
        } catch (Exception e) {
            result.setMessage("数据分析异常");
            result.setCode(500);
            result.setSuccess(false);
            logger.error("数据分析异常" + e);
        }
        return result;
    }

    private String convertToPcaData(List<String> list) {
        List<List<String>> scores = new ArrayList<>();
        for (String s : list) {
            List<String> score = new ArrayList<>();
            String[] split = s.split("#");
            for (String s2 : split) {
                String[] split2 = s2.split(":");
                score.add(split2[1]);
            }
            scores.add(score);
        }
        int col = scores.size();
        int row = scores.get(0).size();
        String[][] sc = new String[row][col];
        for (int i = 0; i < scores.size(); i++) {
            for (int j = 0; j < scores.get(i).size(); j++) {
                sc[j][i] = scores.get(i).get(j);
            }
        }
        String[] pca = new String[sc.length];
        for (int i = 0; i < sc.length; i++) {
            pca[i] = "X" + (i + 1) + "=c(";
            for (int j = 0; j < sc[0].length; j++) {
                if (j != sc[0].length - 1)
                    pca[i] += sc[i][j] + ",";
                else
                    pca[i] += sc[i][j];
            }
            if (i != sc.length - 1)
                pca[i] += "),";
            else
                pca[i] += ")";
        }
        String data = "";
        for (String s : pca) {
            data += s;
        }
        return data;
    }

    private Boolean Pca(String data) {
        RConnection connection = null;
        System.out.println("开始主成分分析");
        try {
            //创建对象
            connection = new RConnection();
            connection.eval("PCA<-data.frame(" + data + ")");
            System.out.println(data);
            connection.eval("res<-princomp(PCA, cor=TRUE) ");
            connection.eval("standardDeviations<-res$sdev");
            connection.eval("write.csv (standardDeviations, file ='D:/PCA/standardDeviations.csv',row.names =TRUE)");
            connection.eval("data<-summary(res, loadings=TRUE)$loadings");
            connection.eval("write.csv (data, file ='D:/PCA/loadings.csv',row.names =TRUE)");
            connection.eval("jpeg(file = 'D:/PCA/data.jpg')");
            connection.eval("screeplot(res,type='lines')");
            connection.eval("dev.off()");
        } catch (RserveException e) {
            e.printStackTrace();
            return false;
        }
        connection.close();
        return true;
    }

    private Report convertToPCAReport(List<String[]> standardDeviations, List<String[]> loadings) {
        Report report = new Report();
        List<Double> list = new ArrayList<>();
        List<List<String>> formatLoading = new ArrayList<>();
        //
        DecimalFormat df = new DecimalFormat("0.00000");
        for (String[] loading : loadings) {
            List<String> sc = new ArrayList<>();
            sc.add(loading[0]);
            for (int i = 1; i < loading.length; i++) {
                sc.add(df.format(Double.parseDouble(loading[i])));
            }
            formatLoading.add(sc);
        }
        for (String[] standardDeviation : standardDeviations) {
            double parseDouble = Double.parseDouble(standardDeviation[1]);
            list.add(parseDouble);
        }
        List<Double> variance = new ArrayList<>();
        Double num = 0.0;
        for (Double value : list) {
            num += value * value;
            variance.add(value * value);
        }
        List<Double> proportionOfvariance = new ArrayList<>();
        for (Double value : variance) {
            Double res = value / num;
            proportionOfvariance.add(res);
        }
        List<Double> cumulativeProportion = new ArrayList<>();
        num = 0.0;
        for (Double value : proportionOfvariance) {
            num += value;
            cumulativeProportion.add(num);
        }
        report.setStandardDeviation(formatDoubleList(list));
        report.setVariance(formatDoubleList(variance));
        report.setProportionOfvariance(formatDoubleList(proportionOfvariance));
        report.setCumulativeProportion(formatDoubleList(cumulativeProportion));
        report.setLoadings(formatLoading);
        return report;
    }

    private List<Double> formatDoubleList(List<Double> list) {
        DecimalFormat df = new DecimalFormat("0.00000");
        List<Double> res = new ArrayList<>();
        for (Double value : list) {
            double v = Double.parseDouble(df.format(value));
            res.add(v);
        }
        return res;
    }


    public Result getAprioriReport(){
        Map<String, List<String>> unqualified = inspectWorkService.unqualified();
        Result result = new Result();
        Map<String, Object> res = new HashMap<>();
        AprioriMyself aprioriMyself = new AprioriMyself();
        List<List<String>> record = new ArrayList<>();
        Set<String> strings = unqualified.keySet();
        for (String string : strings) {
            List<String> list = unqualified.get(string);
            if(!list.isEmpty())
                record.add(list);
        }
        List<AssociationRules> associationRules = aprioriMyself.getRecommend(record);
        Map<String, Double> recommend = new HashMap<>();
        for (AssociationRules associationRule : associationRules) {
            recommend.put(associationRule.rulesExpress(),associationRule.getConfidence());
        }
        // 以下是组合为前端页面显示的操作
        res.put("recommend",recommend);
        res.put("record",unqualified);
        res.put("count",aprioriMyself.convertCount(aprioriMyself.getMap()));
        res.put("frequentItems",aprioriMyself.getFrequentItemset());
        result.setData(res);
        return result;
    }
}
