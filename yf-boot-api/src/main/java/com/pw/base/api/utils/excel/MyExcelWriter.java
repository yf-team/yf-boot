package com.pw.base.api.utils.excel;

import cn.hutool.core.exceptions.DependencyException;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import org.apache.poi.xssf.streaming.SXSSFSheet;

public class MyExcelWriter extends BigExcelWriter {

    public static MyExcelWriter getBigWriter() {
        try {
            return new MyExcelWriter();
        } catch (NoClassDefFoundError var1) {
            throw new DependencyException((Throwable) ObjectUtil.defaultIfNull(var1.getCause(), var1), "You need to add dependency of 'poi-ooxml' to your project, and version >= 4.1.2", new Object[0]);
        }
    }


    @Override
    public BigExcelWriter autoSizeColumnAll() {
        final SXSSFSheet sheet = (SXSSFSheet)this.sheet;
        sheet.trackAllColumnsForAutoSizing();
        super.autoSizeColumnAll();
        for (int i = 0; i <sheet.getRow(sheet.getLastRowNum()).getPhysicalNumberOfCells(); i++) {
            // 解决自动设置列宽中文失效的问题
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 3);
        }
        sheet.untrackAllColumnsForAutoSizing();
        return this;
    }

}
