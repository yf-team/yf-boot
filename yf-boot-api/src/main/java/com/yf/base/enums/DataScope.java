package com.yf.base.enums;

/**
 * 数据权限枚举
 * @author bool
 */
public interface DataScope {

    /**
     * 本人数据
     */
    Integer SCOPE_SELF = 1;
    /**
     * 本部门数据
     */
    Integer SCOPE_DEPT = 2;
    /**
     * 本部门及以下数据
     */
    Integer SCOPE_DEPT_DOWN = 3;
    /**
     * 全部数据
     */
    Integer SCOPE_ALL = 4;
}
