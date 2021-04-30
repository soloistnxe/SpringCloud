package com.example.inspect.Arithmetic.knn;

import lombok.Data;

import java.util.List;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/3/24 11:07
 */
@Data
public class KNNData implements Comparable<KNNData> {
    List<Double> knnData;
    double distance;
    String type;

    public KNNData(List<Double> knnData, String type) {
        this.knnData=knnData;
        this.type = type;
    }

    @Override
    public int compareTo(KNNData arg0) {
        return Double.valueOf(this.distance).compareTo(Double.valueOf(arg0.distance));
    }
}
