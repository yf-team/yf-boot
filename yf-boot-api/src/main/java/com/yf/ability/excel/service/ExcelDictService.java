package com.yf.ability.excel.service;

/**
 * 数据字典的翻译，用于导入导出，如果需要翻译，则必须要实现此类
 * @author van
 */
public interface ExcelDictService {

    /**
     * 翻译字典标题
     * @param code
     * @param key
     * @return
     */
    String findDictText(String code, String key);

    /**
     * 查找其它表的对应信息
     * @param table
     * @param text
     * @param code
     * @param key
     * @return
     */
    String findTableText(String table, String text, String code, String key);
}
