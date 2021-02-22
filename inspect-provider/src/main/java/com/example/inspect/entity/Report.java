package com.example.inspect.entity;

import lombok.Data;

import java.util.List;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/2/17 10:44
 */
@Data
public class Report {
    /**
     * 主成分载荷
     */
    private List<List<String>> loadings;
    /**
     * 主成分标准差
     */
    private List<Double> standardDeviation;
    /**
     * 主成分方差
     */
    private List<Double> variance;
    /**
     * 方差贡献率
     */
    private List<Double> proportionOfvariance;
    /**
     * 累计方差贡献率
     */
    private List<Double> cumulativeProportion;
}
