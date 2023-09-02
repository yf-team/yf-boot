package com.yf.base.utils;


import com.yf.base.api.exception.ServiceException;

/**
 * 部门编码生成器，用于生成每个级别的部门，每个同级部门最多支持2573个，超出会异常
 * @author Van
 */
public class DeptCodeGen {

    /**
     * 产生部门编号
     * @param num
     * @return
     */
    public static String gen(int num){

        if(num > 2573){
            throw new ServiceException("每级最大支持2573个部门！");
        }

        // 序列，0-99为A,100-199为B
        int index = num / 99;
        int left = num % 99;
        String tag = AbcTags.get(index);
        StringBuffer sb = new StringBuffer(tag);
        if(left < 10){
            sb.append("0");
        }

        sb.append(left);
        return sb.toString();
    }

    public static void main(String[] args) {
        for(int i=0;i<=2573;i++){
            System.out.println(gen(i));
        }
    }
}
