package com.pw.base.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间段计算工具类
 * @author bool
 */
public class DateRangeUtils {


    /**
     * 时间格式
     */
    public static SimpleDateFormat startFmt = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    public static SimpleDateFormat endFmt = new SimpleDateFormat("yyyy-MM-dd 23:59:59");


    /**
     * 根据标识构建时间段，1为今日，2为本周，3为本月
     * @param rankingType
     * @return
     */
    public static String [] generateRange(int rankingType){

        switch (rankingType){
            case 1:
                return generateToday();
            case 2:
                return generateWeek();
            case 3:
                return generateMonth();
            default:
                return generateYear();

        }
    }


    /**
     * 今天的开始结束时间
     * @return
     */
    public static String [] generateToday(){

        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(System.currentTimeMillis());

        String startTime = startFmt.format(cl.getTime());
        String endTime = endFmt.format(cl.getTime());

        return new String[]{startTime, endTime};
    }


    /**
     * 本周的开始结束时间
     * @return
     */
    public static String [] generateWeek(){

        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(System.currentTimeMillis());

        cl.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String startTime = startFmt.format(cl.getTime());

        cl.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        String endTime = endFmt.format(cl.getTime());

        return new String[]{startTime, endTime};
    }

    /**
     * 本月的的开始结束时间
     * @return
     */
    public static String [] generateMonth(){

        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(System.currentTimeMillis());

        cl.set(Calendar.DAY_OF_MONTH, cl.getActualMinimum(Calendar.DAY_OF_MONTH));
        String startTime = startFmt.format(cl.getTime());

        cl.set(Calendar.DAY_OF_MONTH, cl.getActualMaximum(Calendar.DAY_OF_MONTH));
        String endTime = endFmt.format(cl.getTime());

        return new String[]{startTime, endTime};
    }

    /**
     * 获取近一年的时间
     * @return
     */
    public static String [] generateYear(){

        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(System.currentTimeMillis());

        String endTime = endFmt.format(cl.getTime());
        cl.add(Calendar.DATE, -365);
        String startTime = startFmt.format(cl.getTime());

        return new String[]{startTime, endTime};
    }

    /**
     * 校验时间是否在时间段范围内，时间段格式字符串为：12:00-14:00
     * @param arr
     * @return
     */
    public static boolean inRange(String [] arr){

        // 逐行循环
        for(String line: arr){

            // 不符合规范的忽略
            if(StringUtils.isBlank(line) || line.indexOf("-")==-1){
                continue;
            }
            // 不符合规范
            String [] range = line.split("-");

            if(range==null || range.length!=2){
                continue;
            }

            String l = range[0];
            String r = range[1];

            String [] lt = l.split(":");
            String [] rt = r.split(":");

            if(lt==null || lt.length!=2 || rt==null || rt.length!=2){
                continue;
            }

            Calendar c1 = Calendar.getInstance();
            c1.setTimeInMillis(System.currentTimeMillis());
            c1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(lt[0]));
            c1.set(Calendar.MINUTE, Integer.parseInt(lt[1]));
            c1.set(Calendar.SECOND, 0);
            c1.set(Calendar.MILLISECOND, 0);

            Calendar c2 = Calendar.getInstance();
            c2.setTimeInMillis(System.currentTimeMillis());
            c2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(rt[0]));
            c2.set(Calendar.MINUTE, Integer.parseInt(rt[1]));
            c2.set(Calendar.SECOND, 0);
            c2.set(Calendar.MILLISECOND, 0);

            // 一个符合就返回
            if(new Date().after(c1.getTime()) && new Date().before(c2.getTime())){
                // 表示正常
                return true;
            }
        }

        return false;
    }
}
