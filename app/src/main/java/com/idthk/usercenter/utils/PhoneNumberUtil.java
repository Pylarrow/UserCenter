package com.idthk.usercenter.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by MVP on 2016/11/26 0026.
 */

public class PhoneNumberUtil {

    /**
     * @author update by dengjh  校验手机号
     * @param str
     * @return
     * @throws PatternSyntaxException
     */

    public static boolean isMobile(String str) throws PatternSyntaxException {
        /**
         * 手机号码:
         * 13[0-9], 14[5,7], 15[0, 1, 2, 3, 5, 6, 7, 8, 9], 17[0, 1, 6, 7, 8], 18[0-9]
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         */
//        String regExp = "^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$";
        return isregExpMobile(str)||regExpUnicom(str)||regExpTelecom(str);
    }


    public static boolean isregExpMobile(String str) throws PatternSyntaxException {
        /**
         * 中国移动：China Mobile
         * 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         */
        String regExpMobile= "^1(3[4-9]|4[7]|5[0-27-9]|7[08]|8[2-478])\\d{8}$";
        Pattern p = Pattern.compile(regExpMobile);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean regExpUnicom(String str) throws PatternSyntaxException {
        /**
         * 中国联通：China Unicom
         * 130,131,132,145,155,156,170,171,175,176,185,186
         */
        String regExpUnicom = "^1(3[0-2]|4[5]|5[56]|7[0156]|8[56])\\d{8}$";
        Pattern p = Pattern.compile(regExpUnicom);
        Matcher m = p.matcher(str);
        return m.matches();
    }


    public static boolean regExpTelecom(String str) throws PatternSyntaxException {
        /**
         * 中国联通：China Unicom
         * 130,131,132,145,155,156,170,171,175,176,185,186
         */
        String regExpTelecom= "^1(3[3]|4[9]|53|7[037]|8[019])\\d{8}$";
        Pattern p = Pattern.compile(regExpTelecom);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static String formatPhone(String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            return "";
        }
        String str = "";
        for (int i = 0; i < mobile.length(); i++) {
            if (i == mobile.length() - 11) {
                str += mobile.charAt(i);
            } else if (i == mobile.length() - 10) {
                str += mobile.charAt(i);
            } else if (i == mobile.length() - 9) {
                str += mobile.charAt(i);
            } else if (i == mobile.length() - 3) {
                str += mobile.charAt(i);
            } else if (i == mobile.length() - 2) {
                str += mobile.charAt(i);
            } else if (i == mobile.length() - 1) {
                str += mobile.charAt(i);
            } else {
                str += "*";
            }
        }
        return str;
    }
}
