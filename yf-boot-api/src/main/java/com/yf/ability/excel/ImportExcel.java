package com.yf.ability.excel;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.yf.ability.excel.annotation.ExcelField;
import com.yf.base.api.exception.ServiceException;
import com.yf.base.utils.jackson.JsonHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导入Excel表格，支持.xls和.xlsx，基于hutool进行封装的
 *
 * @author bool
 */
@Log4j2
public class ImportExcel {

    /**
     * 标题行号
     */
    private int headerNum;

    /**
     * 名称对应实体字段映射
     */
    private List<String> headerList = new ArrayList<>();

    /**
     * 提取出的数据列表
     */
    private List<Map<String, Object>> dataList = new ArrayList<>();


    /**
     * 处理数据
     *
     * @return
     */
    private RowHandler createRowHandler() {
        return (sheetIndex, rowIndex, rowList) -> {

            // 提取表头出来
            if (rowIndex == headerNum) {
                for (Object obj : rowList) {
                    String title = String.valueOf(obj);
                    headerList.add(title);
                }
                return;
            }

            // 没有表头直接返回
            if (CollectionUtils.isEmpty(headerList)) {
                return;
            }


            // 对应数据成名称-值
            Map<String, Object> map = new HashMap<>();
            for (int i = 0; i < rowList.size(); i++) {
                if(i >= headerList.size()){
                    break;
                }
                map.put(headerList.get(i), rowList.get(i));
            }


            // 加入列表
            this.addToList(map);

        };
    }

    /**
     * 将Map放入List
     * @param map
     */
    private void addToList(Map<String, Object> map){
        boolean empty = true;

        // 全部key值为空，则可能是表格某个格子
        if(map == null || map.isEmpty()){
            return;
        }

        // 循环值
        for(String key: map.keySet()){
            Object val = map.get(key);
            if(val!=null && !StringUtils.isEmpty(val.toString())){
                empty = false;
            }
        }

        if(!empty){
            dataList.add(map);
        }
    }

	/**
	 * 读取文件，默认第二行为表头
	 * @param file
	 */
	public ImportExcel(MultipartFile file){
		// 默认从第二行开始读表头
		this(file, 1);
	}


	/**
	 * 读取文件，表头行号索引从0开始，即excel行号-1
	 * @param file
	 * @param headerNum
	 */
    public ImportExcel(MultipartFile file, int headerNum) {

        // 头部行货
        this.headerNum = headerNum;

        String fileName = file.getOriginalFilename();
        if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
            throw new ServiceException("导入的文件格式错误，必须为.xls或.xlsx");
        }

		try {
			ExcelUtil.readBySax(file.getInputStream(), -1, createRowHandler());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServiceException("数据流读取失败！");
		}
	}


	/**
	 * 反射java类，形成一个表头名称<-->java字段的Map
	 * @param clazz
	 * @return
	 */
	private Map<String, Field> processFields(Class clazz) {

        // 获取当前类字段
        Field[] fields = clazz.getDeclaredFields();

        // 标题对应注解
        Map<String, Field> fieldMap = new HashMap<>();


        for (Field field : fields) {
            if (!field.isAccessible()) {
                // 关闭反射访问安全检查，为了提高速度
                field.setAccessible(true);
            }

            // 只导出有注解的字段
            boolean export = field.isAnnotationPresent(ExcelField.class);
            if (!export) {
                continue;
            }

            // 加入列表
            ExcelField ann = field.getAnnotation(ExcelField.class);
            fieldMap.put(ann.title(), field);
        }

        return fieldMap;

    }

    /**
     * 返回对应实体列表数据
     * @param cls
     * @param <E>
     * @return
     */
    public <E> List<E> getDataList(Class<E> cls) {

        // 名称对应关系
        Map<String, Field> fieldMap = this.processFields(cls);

        List<E> list = new ArrayList<>();

        for (Map<String, Object> map : dataList) {

            // 使用JSON对象进行数据接收
            Map<String,Object> json = new HashMap<>(16);

            for (String key : map.keySet()) {

                // 丢弃不存在的映射
                if (!fieldMap.containsKey(key)) {
                    continue;
                }

                // 创建实例并赋值
                Field field = fieldMap.get(key);

                // 原始表格数据
                Object val = map.get(key);

                // 注解参数
                ExcelField ann = field.getAnnotation(ExcelField.class);

                // 处理静态数据过滤：此处和导出一样，过滤器需要与导出写反的，如：正常=0,禁用=1
                this.transFilter(ann, val);

                // TODO 数据字典反向，暂时不处理，就是根据数据字典标题查值

                // 使用JSON转换，使用反射也可，效率问题待研究
                json.put(field.getName(), val);
            }

            list.add(JsonHelper.parseObject(json, cls));
        }
        return list;
    }


    /**
     * 数据字典转换
     * @param ann
     * @param val
     * @return
     */
    private Object transFilter(ExcelField ann, Object val){

        String filter = ann.filter();

        // 不需要处理
        if(StringUtils.isEmpty(filter)){
            return val;
        }

        String [] arr = filter.split(",");

        if(arr.length == 0){
            return val;
        }

        for(String item: arr){
            String [] arr1 = item.split("=");
            if(String.valueOf(val).equals(arr1[0])){
                return arr1[1];
            }
        }

        // 进行转换
        return val;
    }

}
