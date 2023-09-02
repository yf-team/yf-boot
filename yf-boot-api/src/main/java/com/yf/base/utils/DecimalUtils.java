package com.yf.base.utils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Decimal相关的工具类，用于一些常规的比较
 *
 * @author Van
 * @date 2019/1/22 16:39
 */
@Slf4j
public class DecimalUtils {

    private static final BigDecimal ZERO = new BigDecimal(0);

    /**
     * 返回一个值为0的BigDecimal
     */
    public static BigDecimal zero() {
        return ZERO;
    }

    /**
     * 如果参数a>b,则返回true，否则返回false
     */
    public static boolean gt(BigDecimal arg0, BigDecimal arg1) {
        if (arg0 == null || arg1 == null) {
            warnValue("gt", arg0, arg1);
            return false;
        }
        return arg0.compareTo(arg1) > 0;
    }

    /**
     * 如果参数a>b,则返回true，否则返回false
     */
    public static boolean gt(BigDecimal arg0, Double arg1) {
        return gt(arg0, arg1 == null ? null : new BigDecimal(arg1.toString()));
    }

    /**
     * 如果参数a>b,则返回true，否则返回false
     */
    public static boolean gt(BigDecimal arg0, Integer arg1) {
        return gt(arg0, arg1 == null ? null : new BigDecimal(arg1));
    }

    /**
     * 如果参数a>=b,则返回true，否则返回false
     */
    public static boolean ge(BigDecimal arg0, BigDecimal arg1) {
        if (arg0 == null || arg1 == null) {
            warnValue("ge", arg0, arg1);
            return false;
        }
        return arg0.compareTo(arg1) >= 0;
    }

    /**
     * 如果参数a>=b,则返回true，否则返回false
     */
    public static boolean ge(BigDecimal arg0, Double arg1) {
        return gt(arg0, arg1 == null ? null : new BigDecimal(arg1.toString()));
    }

    /**
     * 如果参数a<b,则返回true，否则返回false
     */
    public static boolean lt(BigDecimal arg0, BigDecimal arg1) {
        if (arg0 == null || arg1 == null) {
            warnValue("lt", arg0, arg1);
            return false;
        }
        return arg0.compareTo(arg1) < 0;
    }

    /**
     * 如果参数a<b,则返回true，否则返回false
     */
    public static boolean lt(BigDecimal arg0, Double arg1) {
        return lt(arg0, arg1 == null ? null : new BigDecimal(arg1.toString()));
    }

    /**
     * 如果参数a<=b,则返回true，否则返回false
     */
    public static boolean le(BigDecimal arg0, BigDecimal arg1) {
        if (arg0 == null || arg1 == null) {
            warnValue("le", arg0, arg1);
            return false;
        }
        return arg0.compareTo(arg1) <= 0;
    }

    /**
     * 如果参数a<=b,则返回true，否则返回false
     */
    public static boolean le(BigDecimal arg0, Double arg1) {
        return lt(arg0, arg1 == null ? null : new BigDecimal(arg1.toString()));
    }

    /**
     * 比较两个BigDecimal值是否一致
     */
    public static boolean eq(BigDecimal arg0, BigDecimal arg1) {
        if (arg0 == null || arg1 == null) {
            warnValue("eq", arg0, arg1);
            return false;
        }
        return arg0.compareTo(arg1) == 0;
    }

    /**
     * 该值是否是0
     */
    public static boolean isZero(BigDecimal value) {
        return eq(value, ZERO);
    }

    /**
     * 该值是否小于0
     */
    public static boolean ltZero(BigDecimal value) {
        return lt(value, ZERO);
    }

    /**
     * 改值是否大于0
     */
    public static boolean gtZero(BigDecimal value) {
        return gt(value, ZERO);
    }

    /**
     * 为空或者0
     */
    public static boolean nullOrZero(BigDecimal value) {
        return value == null || isZero(value);
    }

    /**
     * 加
     */
    public static BigDecimal add(BigDecimal arg0, BigDecimal arg1) {
        if (arg0 == null && arg1 == null) {
            warnNull("divide", "arg0, arg1");
            return ZERO;
        }
        if (arg0 == null) {
            warnNull("divide", "arg0");
            return arg1;
        }
        if (arg1 == null) {
            warnNull("divide", "arg1");
            return arg0;
        }
        return arg0.add(arg1);
    }

    /**
     * 除
     */
    public static BigDecimal divide(BigDecimal arg0, BigDecimal arg1) {
        if (arg0 == null && arg1 == null) {
            warnNull("divide", "arg0, arg1");
            return ZERO;
        }
        if (arg0 == null) {
            warnNull("divide", "arg0");
            return ZERO;
        }
        if (arg1 == null) {
            warnNull("divide", "arg1");
            return arg0;
        }
        return arg0.divide(arg1,10 ,RoundingMode.DOWN);
    }

    /**
     * 除
     */
    public static BigDecimal divide(BigDecimal arg0, Integer arg1) {
        return divide(arg0, arg1 == null ? null : new BigDecimal(arg1));
    }

    /**
     * 除
     */
    public static BigDecimal divide(BigDecimal arg0, Double arg1) {
        return divide(arg0, arg1 == null ? null : new BigDecimal(arg1.toString()));
    }

    /**
     * A*B 乘法
     */
    public static BigDecimal multiply(BigDecimal arg0, BigDecimal arg1) {
        if (arg0 == null && arg1 == null) {
            warnNull("multiply", "arg0, arg1");
            return ZERO;
        }
        if (arg0 == null) {
            warnNull("multiply", "arg0");
            return arg1;
        }
        if (arg1 == null) {
            warnNull("multiply", "arg1");
            return arg0;
        }
        return arg0.multiply(arg1);
    }

    /**
     * A*B 乘法
     */
    public static BigDecimal multiply(BigDecimal arg0, Integer arg1) {
        return multiply(arg0, arg1 == null ? null : new BigDecimal(arg1));
    }

    /**
     * 减
     */
    public static BigDecimal subtract(BigDecimal arg0, BigDecimal arg1) {
        if (arg0 == null && arg1 == null) {
            warnNull("subtract", "arg0, arg1");
            return ZERO;
        }
        if (arg1 == null) {
            warnNull("subtract", "arg1");
            return arg0;
        }
        if (arg0 == null) {
            warnNull("subtract", "arg0");
            arg0 = ZERO;
        }
        return arg0.subtract(arg1);
    }

    /**
     * 空日志警告
     *
     * @param methodName 方法名称
     * @param parmName   参数名称
     */
    private static void warnNull(String methodName, String parmName) {
        log.warn("DecimalUtils {} {} is null", methodName, parmName);
    }

    /**
     * 空日志警告
     *
     * @param methodName 方法名称
     * @param value      参数
     */
    private static void warnValue(String methodName, BigDecimal... value) {
        log.warn("DecimalUtils {} parameter(s) {}", methodName, value);
    }

    /**
     * 合法化 如果为null则返回0
     */
    public static BigDecimal legal(BigDecimal value) {
        return value == null ? ZERO : value;
    }
}
