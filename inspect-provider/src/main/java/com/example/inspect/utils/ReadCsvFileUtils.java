package com.example.inspect.utils;

import com.csvreader.CsvReader;
import org.hibernate.validator.constraints.EAN;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/2/16 16:12
 */
public class ReadCsvFileUtils {
    public static List<String[]> readCsvFile(String filePath){
        List<String[]> csvList = null;
        try {
            csvList = new ArrayList<>();
            CsvReader reader = new CsvReader(filePath,',', Charset.forName("GBK"));
            reader.readHeaders(); //跳过表头,不跳可以注释掉

            while(reader.readRecord()){
                csvList.add(reader.getValues()); //按行读取，并把每一行的数据添加到list集合
            }
            reader.close();
            System.out.println("读取的行数："+csvList.size());

            for (String[] strings : csvList) {
                System.out.println(Arrays.toString(strings));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return csvList;
    }
}
