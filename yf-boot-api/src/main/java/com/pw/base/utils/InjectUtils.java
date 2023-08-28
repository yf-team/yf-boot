package com.pw.base.utils;

import com.alibaba.fastjson.JSON;
import com.pw.base.api.api.ApiError;
import com.pw.base.api.api.ApiRest;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 注入工具类
 * @author bool
 * @date 2019-07-17 09:32
 */
@Log4j2
public class InjectUtils {


    /**
     * 给对象字段赋值
     *
     * @param object 赋值的对象
     * @param value  值
     * @param fields 字段
     * @throws Exception 异常
     */
    public static void setValue(Object object, Object value, String... fields) throws Exception {

        //设置同类的属性
        for (String fieldName : fields) {

            //获取当前
            Field field = getField(object.getClass(), fieldName);
            if(field == null){
                continue;
            }

            field.setAccessible(true);
            field.set(object, value);
        }

    }

    /**
     * 查找字段包含父类属性
     * @param clazz
     * @param name
     * @return
     */
    public static Field getField(Class<?> clazz, String name){

        //遍历全部的域
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            if (name.equals(field.getName())){
                return field;
            }
        }

        Class<?> superclass = clazz.getSuperclass();
        if (null == superclass){
            return null;
        }
        return getField(superclass, name);
    }


    /**
     * 打印结果返回
     * @param response
     * @throws IOException
     */
    public static void restError(HttpServletResponse response) {

        try {

            //固定错误
            ApiRest apiRest = new ApiRest(ApiError.ERROR_10010002);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(JSON.toJSONString(apiRest));
            response.getWriter().close();

        }catch (IOException e){

        }
    }


    /**
     * 获得包含父类属性的全部字段
     * @param clazz
     * @param allFields
     */
    public static void extractFields(Class clazz, List<Field> allFields){

        // 获取对象属性
        Field [] fields = clazz.getDeclaredFields();
        for(Field field: fields){
            allFields.add(field);
        }

        if(clazz.getSuperclass()!=null){
            extractFields(clazz.getSuperclass(), allFields);
        }
    }

    /**
     * 提取全部字段，包含父类的字段在内的
     * @param clazz
     * @return
     */
    public static List<Field> extractAllFields(Class clazz){
        // 提取全部字段
        List<Field> fields = new ArrayList<>();
        extractFields(clazz, fields);
        return fields;
    }




}
