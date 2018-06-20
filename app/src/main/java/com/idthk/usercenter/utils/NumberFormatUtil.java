package com.idthk.usercenter.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberFormatUtil {

    private static DecimalFormat mDecimalFormat = new DecimalFormat("#0.00");
    private static DecimalFormat _mDecimalFormat = new DecimalFormat("#0.0");
    private static DecimalFormat doubleFormat = new DecimalFormat("#,##0.00");
    private static DecimalFormat intFormat = new DecimalFormat("#,###");

    public static double format(double number) {
        BigDecimal _number = new BigDecimal(number);
        return _number.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String formatToStr(double number) {
        BigDecimal _number = new BigDecimal(number);
        return formatDouble(_number.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    public static String formatToString(double number) {
        return mDecimalFormat.format(number) + "";
    }

    public static String _formatToString(double number) {
        return _mDecimalFormat.format(number) + "";
    }

    public static String formatToString(String number){
        BigDecimal _number = new BigDecimal(number);
        return _number.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+"";
    }



    /**
     * 比较两个双精度包装类型值的大小
     * @param d1
     * @param d2
     * @return
     */
    public static boolean compareDouble(Double d1, Double d2){
        try{
            if (null == d1) {
                return d2 == null;
            }
            if (null == d2){
                return false;
            }
            BigDecimal bd1 = new BigDecimal(d1+"");
            BigDecimal bd2 = new BigDecimal(d2+"");
            int re = bd1.compareTo(bd2);
            return (re == 0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 格式化Double类型数据
     * 例：12545.12格式化为 12,545.12
     * @param d
     * @return
     */
    public static String formatDouble(Double d){
        if (null != d){
            return doubleFormat.format(d);
        }
        return "0.00";
    }


    /**
     * 格式化Double类型数据
     * 例：12545.12格式化为 12,545.12
     * @param d
     * @return
     */
    public static String formatBigDecimal(BigDecimal d){
        if (null != d){
            return doubleFormat.format(d);
        }
        return "0.00";
    }

    /**
     * 格式化Double类型数据
     * 例：12545.12格式化为 12,545.12
     * @param d
     * @return
     */
    public static String formatFloat(Float d){
        if (null != d){
            return doubleFormat.format(d);
        }
        return "0.00";
    }

    /**
     * 格式化Integer类型数据
     * 例：12546212格式化为 12,546,212
     * @param i
     * @return
     */
    public static String formatInteger(Integer i){
        if (null != i){
            return intFormat.format(i);
        }
        return "0";
    }

    public static void main(String[] args){
        double d = 0.48;
        System.out.println(d + "-" +formatDouble(d));
        d = 5165465.48;
        System.out.println(d + "-" +formatDouble(d));
        d = 54415.48;
        System.out.println(d + "-" +formatDouble(d));
        d = 5498498;
        System.out.println(d + "-" +formatDouble(d));
        d = 155632.0598;
        System.out.println(d + "-" +formatDouble(d));
    }
}
