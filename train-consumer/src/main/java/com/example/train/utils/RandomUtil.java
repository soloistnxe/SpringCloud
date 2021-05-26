package com.example.train.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author soloist
 * @version 1.0
 * @describe 随机数生成工具类
 * @date 2021/5/14 15:50
 */
public class RandomUtil {

    public static List<Integer> getRandomIndex(int length,int number){
        int count = 0;
        Random random = new Random();
        //System.out.println(random.nextInt(10));//产生[0,10)的随机数
        List<Integer> arrayList = new ArrayList<>();
        while (count < number) {
            int num = random.nextInt(length);// 产生[0-length)的随机数
            if (!arrayList.contains(num)) {
                arrayList.add(num);
                count++;
            }
        }
        return arrayList;
    }

}
