
package com.idthk.usercenter.utils;

import java.math.BigDecimal;

/**
 * User: Aaron
 * Date: 2016/5/10
 * Time: 17:43
 */
public class Calculate {

    public static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;

    private Calculate() {
    }

    /**
     * 减法
     */
    public static double subtract(double arg1, double arg2) {

        BigDecimal arg1Decimal = new BigDecimal(arg1+"");
        BigDecimal arg2Decimal = new BigDecimal(arg2+"");
        return arg1Decimal.subtract(arg2Decimal).setScale(2, ROUNDING_MODE).doubleValue();
    }

    /**
     * 减法
     */
    public static BigDecimal subtract(BigDecimal arg1, BigDecimal arg2) {
        return arg1.subtract(arg2).setScale(2, ROUNDING_MODE);
    }

    /**
     * 加法
     */
    public static double add(double arg1, double arg2) {
        BigDecimal arg1Decimal = new BigDecimal(arg1+"");
        BigDecimal arg2Decimal = new BigDecimal(arg2+"");
        return arg1Decimal.add(arg2Decimal).setScale(2, ROUNDING_MODE).doubleValue();
    }

    /**
     * 加法
     */
    public static BigDecimal add(BigDecimal arg1, BigDecimal arg2){
        return arg1.add(arg2).setScale(2, ROUNDING_MODE);
    }

    /**
     * 乘法 processor
     */
    public static double multiply(double arg1, double arg2) {
        BigDecimal arg1Decimal = new BigDecimal(arg1+"");
        BigDecimal arg2Decimal = new BigDecimal(arg2+"");
        return arg1Decimal.multiply(arg2Decimal).doubleValue();
    }

    /**
     * 乘法 processor
     */
    public static BigDecimal multiplyBigDecimal(double arg1, double arg2) {
        BigDecimal arg1Decimal = new BigDecimal(arg1+"");
        BigDecimal arg2Decimal = new BigDecimal(arg2+"");
        return arg1Decimal.multiply(arg2Decimal);
    }

    /**
     * 乘法 processor
     */
    public static double multiply(BigDecimal arg1, BigDecimal arg2) {
        return arg1.multiply(arg2).doubleValue();
    }

    /**
     * 除法
     */
    public static double divide(double arg1, double arg2) {
        BigDecimal arg1Decimal = new BigDecimal(arg1+"");
        BigDecimal arg2Decimal = new BigDecimal(arg2+"");
        return arg1Decimal.divide(arg2Decimal).doubleValue();
    }
}
