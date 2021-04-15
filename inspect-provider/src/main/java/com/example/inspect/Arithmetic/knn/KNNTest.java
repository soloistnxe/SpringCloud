package com.example.inspect.Arithmetic.knn;

import java.util.ArrayList;
import java.util.List;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/3/24 11:09
 */
public class KNNTest {
    public static void main(String[] args) {

        List<KNNData> kd = new ArrayList<KNNData>();
        //训练集
        Double[] l1 = new Double[] {1.2,1.1,0.1};
        Double[] l2 = new Double[] {100.0,1.1,0.1};
        Double[] l3 = new Double[] {1.2,1.1,0.1};
        Double[] l4 = new Double[] {7.0,1.5,0.1};
        Double[] l5 = new Double[] {6.0,1.2,0.1};
        Double[] l6 = new Double[] {2.0,2.6,0.1};
        Double[] l7 = new Double[] {2.0,2.6,0.1};
        Double[] l8 = new Double[] {2.0,2.6,0.1};
        Double[] lll = new Double[]{1.1,1.1,0.1};


        kd.add(new KNNData(convert(l1),"A"));
        kd.add(new KNNData(convert(l2),"D"));
        kd.add(new KNNData(convert(l3),"A"));
        kd.add(new KNNData(convert(l4),"B"));
        kd.add(new KNNData(convert(l5),"B"));
        kd.add(new KNNData(convert(l6),"C"));
        kd.add(new KNNData(convert(l7),"C"));
        kd.add(new KNNData(convert(l8),"C"));

        System.out.println(KNN.knnCal(3, new KNNData(convert(lll),"N/A"), kd));
    }
    private static List<Double> convert(Double[] var){
        List<Double> list = new ArrayList<>();
        for (Double c : var) {
            list.add(c);
        }
        return list;
    }
}
