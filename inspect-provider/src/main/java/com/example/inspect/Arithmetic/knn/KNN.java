package com.example.inspect.Arithmetic.knn;

import java.util.*;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/3/24 11:08
 */
public class KNN {
    //训练集
    private List<KNNData> KNNDS = null;

    public KNN(List<KNNData> KNNDS) {
        this.KNNDS = KNNDS;
    }

    //欧式距离
    private static double disCal(KNNData i, KNNData td) {
        double res=0.0;
        if(i.knnData.size()!=td.knnData.size()){
            return 0;
        }else{
            for (int c = 0; c < i.knnData.size(); c++) {
                res+=Math.pow(i.knnData.get(c)-td.knnData.get(c),2);
            }
        }
        return Math.sqrt(res);
    }

    private static String getMaxValueKey(int k, List<KNNData> ts){
        //只保留前k个元素

        while(ts.size() != k) {
            ts.remove(k);
        }

        String sKey;
        //保存key以及出现次数
        HashMap<String,Integer> keySet = new HashMap<String,Integer>();
        keySet.put(ts.get(0).type,1);
        for (int x = 1; x < ts.size(); x++) {
            sKey = ts.get(x).type;
            if (keySet.containsKey(sKey)) {
                keySet.put(sKey, keySet.get(sKey)+1);
            } else {
                keySet.put(sKey, 1);
            }
        }
        Set<Map.Entry<String,Integer>> set = keySet.entrySet();
        Iterator<Map.Entry<String,Integer>> iter = set.iterator();

        int mValue = 0;
        String mType = "";
        while (iter.hasNext()){
            Map.Entry<String,Integer> map = iter.next();
            if (mValue < map.getValue()) {
                mType = map.getKey();
                mValue = map.getValue();
            }
        }

        return mType;
    }

    public static String knnCal(int k, KNNData i, List<KNNData> ts) {
        //保存距离
        for (KNNData td : ts) {
            td.distance = disCal(i, td);
        }
        Collections.sort(ts);
        return getMaxValueKey(k, ts);
    }
}
