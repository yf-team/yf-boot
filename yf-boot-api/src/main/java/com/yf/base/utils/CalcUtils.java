package com.yf.base.utils;

import com.yf.base.utils.jackson.JsonHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 计算工具类
 * @author van
 */
public class CalcUtils {

    /**
     * 将一个浮点数字相除，得到一个精准的数组，最后一项进行补充
     * 如：10 / 3 划分成：3.3 | 3.3 | 3.4
     * @param input 确保是证书或者一位小数的
     * @param size
     * @return
     */
    public static List<BigDecimal> avgSplit(BigDecimal input, int size){

        // 放大10倍
        int x10 = DecimalUtils.multiply(input, 10).intValue();
        // 每份的数量
        int per = DecimalUtils.divide(new BigDecimal(x10), size).intValue();
        // 剩余数量
        int left = x10;

        List<BigDecimal> list = new ArrayList<>();

        for(int i=0; i < size; i++){
            int num = per;
            if(i == size -1){
                num = left;
            }

            // 保留1位
            BigDecimal item = DecimalUtils.divide(new BigDecimal(num), 10).setScale(1, BigDecimal.ROUND_HALF_UP);
            list.add(item);

            // 扣减余量
            left -= per;
        }

        return list;
    }

    public static void main(String[] args) {
        List<BigDecimal> list = avgSplit(new BigDecimal(10), 3);
        System.out.println(JsonHelper.toJson(list));
    }
}
