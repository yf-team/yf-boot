package com.yf.ability.excel;

import cn.hutool.core.io.IoUtil;
import com.yf.ability.excel.annotation.ExcelField;
import com.yf.ability.excel.service.ExcelDictService;
import com.yf.base.api.exception.ServiceException;
import com.yf.base.utils.DateUtils;
import com.yf.base.utils.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * excel导出工具类
 * @author bool
 */
@Component
public class ExportExcel {

    @Lazy
    @Autowired
    private ExcelDictService excelDictService;

    /**
     * 导出数据
     * @param response
     * @param clazz 数据类型
     * @param title 表头名称
     * @param list 列表数据
     */
    public void export(HttpServletResponse response, Class clazz, String title, List list){


        if(CollectionUtils.isEmpty(list)){
            throw new ServiceException("没有可导出的数据，请确认！");
        }

        // 获取当前类字段
        Field[] fields = clazz.getDeclaredFields();

        // 字段列表
        List<Field> fieldList = new ArrayList<>();

        for (Field field : fields) {
            if (!field.isAccessible()) {
                // 关闭反射访问安全检查，为了提高速度
                field.setAccessible(true);
            }

            // 只导出有注解的字段
            boolean export = field.isAnnotationPresent(ExcelField.class);
            if(!export){
                continue;
            }

            // 加入列表
            fieldList.add(field);
        }


        // 进行列排序
        Collections.sort(fieldList, (o1, o2) -> {
            ExcelField a1 = o1.getAnnotation(ExcelField.class);
            ExcelField a2 = o2.getAnnotation(ExcelField.class);
            return a1.sort() - a2.sort();
        });

        try {
            this.write(response, fieldList, title, list);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 导出数据
     * @param response
     * @param list
     * @throws Exception
     */
    private void write(HttpServletResponse response, List<Field> fieldList, String title, List list) throws IOException {

        MyExcelWriter writer= MyExcelWriter.getBigWriter();

        // 处理内容
        List<Map<String,Object>> rows = this.processRows(fieldList, list);

        writer.merge(rows.get(0).size() - 1, title);
        writer.write(rows, true);
        writer.autoSizeColumnAll();

        ServletOutputStream out = response.getOutputStream();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
    }


    /**
     * 添加数据（通过annotation.ExportField添加数据）
     * @return list 数据列表
     */
    public List<Map<String,Object>> processRows(List<Field> fieldList, List list){

        List<Map<String,Object>> all = new ArrayList<>();
        for(Object data: list){
            Map<String, Object> row = new LinkedHashMap<>();
            for(Field field: fieldList){
                ExcelField ann = field.getAnnotation(ExcelField.class);
                Object val = Reflections.invokeGetter(data, field.getName());

                // 数据字典转换及相关格式化
                String result = this.transData(ann, val);

                // 统一转成文本处理
                row.put(ann.title(), result);
            }
            all.add(row);
        }
        return all;
    }

    /**
     * 数据字典转换
     * @param ann
     * @param val
     * @return
     */
    private String transDict(ExcelField ann, Object val){

        String table = ann.dictTable();
        String code = ann.dictCode();
        String text = ann.dicText();

        String key = String.valueOf(val);
        if(StringUtils.isEmpty(key)){
            return key;
        }

        try {
            // 数据字典表
            if (StringUtils.isEmpty(table)) {
                return excelDictService.findDictText(code, key);
            }

            // 直接查其他表
            return excelDictService.findTableText(table, text, code, key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(val);
    }


    /**
     * 数据处理、转换、格式化等
     * @param ann
     * @param val
     * @return
     */
    private String transData(ExcelField ann, Object val){

        // 为空直接返回空数据
        if(val == null){
            return "";
        }

        // 时间格式化
        if(val instanceof Date){

            String pattern = ann.pattern();
            // 默认日期格式
            if(StringUtils.isEmpty(pattern)){
                pattern = "yyyy-MM-dd HH:mm:ss";
            }
            return DateUtils.formatDate((Date)val, pattern);
        }

        // 静态过滤
        String filter = ann.filter();

        // 处理静态映射
        if(!StringUtils.isEmpty(filter)){
            return this.transFilter(val, filter);
        }

        // 处理数据字典
        String dic = ann.dictCode();
        if(!StringUtils.isEmpty(dic)){
            return this.transDict(ann, val);
        }

        // 返回空字符
        return String.valueOf(val);
    }

    /**
     * 静态数据转换，如：1=男,0=女
     * @param val
     * @param filter
     * @return
     */
    private String transFilter(Object val, String filter){

        String [] arr = filter.split(",");

        if(arr.length > 0) {

            for (String item : arr) {
                String[] arr1 = item.split("=");

                // 空用null表示
                if (((val == null || "".equals(String.valueOf(val))) && "null".equals(arr1[0])) || String.valueOf(val).equals(arr1[0])) {
                    return arr1[1];
                }
            }
        }

        // 返回未翻译的值
        return String.valueOf(val);
    }


}
