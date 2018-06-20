package com.idthk.usercenter.utils;

import java.math.BigDecimal;

/**
 * Created by qinghua.liu on 2015/11/23.
 */
public class BigDecimalUtils {
    /**
     * 四舍五入，保留两位小数
     *
     * @param numble
     * @return
     */
    public static double sswr_double(BigDecimal numble) {
        return roun_half_up_double(2, numble);
    }

    /**
     * 四舍五入，保留两位小数
     *
     * @param numble
     * @return
     */
    public static double sswr_double(double numble) {
        return roun_half_up_double(2, numble);
    }

    /**
     * 四舍五入，保留两位小数
     *
     * @param numble
     * @return
     */
    public static String sswr(double numble) {
        return roun_half_up(2, numble);
    }

    /**
     * 四舍五入，保留两位小数
     *
     * @param numble
     * @return
     */
    public static String sswr(BigDecimal numble) {
        return roun_half_up(2, numble);
    }


    /**
     * 四舍五入保留scan位小数
     *
     * @param scan   小数位数
     * @param numble 数值
     * @return
     */
    public static BigDecimal roun_half_up_decimal(int scan, double numble) {
        BigDecimal big = new BigDecimal(numble);
        big = big.setScale(scan, BigDecimal.ROUND_HALF_UP);// 两位小数，四舍五入
        return big;
    }

    /**
     * 四舍五入保留scan位小数
     *
     * @param scan   小数位数
     * @param numble 数值
     * @return
     */
    public static String roun_half_up(int scan, double numble) {
        BigDecimal big = new BigDecimal(numble);
        big = big.setScale(scan, BigDecimal.ROUND_HALF_UP);// 两位小数，四舍五入
        return big.toString();
    }

    public static String roun_half_up(int scan, BigDecimal numble) {
        try {
            BigDecimal bigDecimal = numble.setScale(scan, BigDecimal.ROUND_HALF_UP);// 两位小数，四舍五入
            return bigDecimal.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static double roun_half_up_double(int scan, double numble) {
        BigDecimal big = new BigDecimal(numble);
        big = big.setScale(scan, BigDecimal.ROUND_HALF_UP);// 两位小数，四舍五入
        return big.doubleValue();
    }

    public static double roun_half_up_double(int scan, BigDecimal numble) {
        if (numble == null) return 0;
        BigDecimal bigDecimal = numble.setScale(scan, BigDecimal.ROUND_HALF_UP);// 两位小数，四舍五入
        return bigDecimal.doubleValue();
    }

    /**
     * 截取两位小数
     *
     * @param scan
     * @param numble
     * @return
     */
    public static String round_down_string(int scan, double numble) {
        BigDecimal big = new BigDecimal(numble);
        big = big.setScale(scan, BigDecimal.ROUND_DOWN);// 两位小数
        return big.toString();
    }

    /**
     * 截取两位小数
     *
     * @param scan
     * @param numble
     * @return
     */
    public static double round_down_double(int scan, double numble) {
        BigDecimal big = new BigDecimal(numble);
        big = big.setScale(scan, BigDecimal.ROUND_DOWN);// 两位小数
        return big.doubleValue();
    }

    /**
     * 截取两位小数
     *
     * @param scan
     * @param numble
     * @return
     */
    public static double round_down_double(int scan, BigDecimal numble) {
        if (numble == null) return 0;
        numble = numble.setScale(scan, BigDecimal.ROUND_DOWN);// 两位小数
        return numble.doubleValue();
    }

    /**
     * * 两个Double数相加 *
     *
     * @param v1 *
     * @param v2 *
     * @return Double
     */
    public static Double add(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.add(b2).doubleValue());
    }

    /**
     * * 两个Double数相减 *
     *
     * @param v1 *
     * @param v2 *
     * @return Double
     */
    public static Double sub(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.subtract(b2).doubleValue());
    }

    /**
     * * 两个Double数相乘 *
     *
     * @param v1 *
     * @param v2 *
     * @return Double
     */
    public static Double mul(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.multiply(b2).doubleValue());
    }

    /**
     * * 两个Double数相除 *
     *
     * @param v1 *
     * @param v2 *
     * @return Double
     */
    public static Double div(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.divide(b2, 10, BigDecimal.ROUND_HALF_UP)
                .doubleValue());
    }
}
