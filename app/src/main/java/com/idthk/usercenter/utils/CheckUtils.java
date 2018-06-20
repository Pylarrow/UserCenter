package com.idthk.usercenter.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pengyu.
 * 2018/3/8
 */

public class CheckUtils {
    /**
     * 字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() <= 0;
    }

    /**
     * 字符串是否不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 字符串是否为手机号
     *
     * @param str
     * @return
     */
    public static boolean isPhone(String str) {
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 字符串是否为邮箱
     *
     * @param str
     * @return
     */
    public static boolean isEmail(String str) {
        //Pattern p = Pattern.compile("^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$");
        Pattern p = Pattern.compile("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,3}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 密码是否合法（6-28位,只含数字和字母,且必须包含数字和字母）
     *
     * @param str
     * @return
     */
    public static boolean isPasswordLegal(String str) {
        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 匹配字母加数字
     */
    public static boolean isPwd(String str) {
        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     *  匹配字母或者数字
     */
    public static boolean isPwd2(String str){
        Pattern p=Pattern.compile("^[A-Za-z0-9]{8,16}$");
        Matcher m=p.matcher(str);
        return m.matches();
    }

    /**
     * 匹配字符或数字
     */
    public static boolean checkName(String str) {
        Pattern p = Pattern.compile("^[A-Za-z0-9]+$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 匹配姓名
     *
     * @return
     */
    public static boolean checkCashierName(String str) {
        Pattern p = Pattern.compile("^[\\u4e00-\\u9fa5_a-zA-Z0-9_]{1,8}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
