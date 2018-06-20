package com.idthk.usercenter.utils;

import android.util.Log;

import java.util.Date;

/**
 * 生成21位付款码类
 * Created by huangrui on 2016/11/21.
 */

public class CodeFacade {

    private static String tag = CodeFacade.class.getSimpleName();

    private static final String PREFIX = "m"; // 一点管家扫描识别标示

    private static int randomNumberDigit = 4; // 4位随机数

    private static String time = "01";

    private static String returnDigits = "8"; // 生成otp位数

    private final static int ONLINE = 0;
    private final static int OFFLINE = 1;

    /**
     * 生成付款码
     * 格式：  mddddddddddxxxxxxxxfrrrr 24位字符串
     * 在线使用时间戳(秒)生成otp
     * 离线使用4位随机数生成otp
     *
     * @param token
     * @param userId
     * @param online
     * @return
     */
    public static String generateCode(String token, String userId, boolean online) {
        try {
            StringBuilder code = new StringBuilder();
            code.append(PREFIX);
            // 生成4位随机数
            String randomNum = RandomNumberUtil.getRandomNumber(randomNumberDigit);

            // 可逆加密userId
            code.append(IDEncryp.encode(userId, randomNum));

            // 生成otp
            if (online) {
                // 在线模式 token + timestamp
                code.append(onlineModel(token, new Date()));
            } else {
                // 离线模式 token + 4random
                code.append(offlineModel(token, randomNum));
            }
            // 标示位
            code.append(online ? ONLINE : OFFLINE);

            // 随机数
            code.append(randomNum);
            return code.toString();
        } catch (Exception e) {
            Log.e(tag, "生成付款码失败，失败原因： " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // 离线模式生成的otp
    private static String offlineModel(String token, String randomNum) {
        return TOTP.generateTOTP(token + randomNum, time, returnDigits);
    }

    // 在线模式生成的otp
    private static String onlineModel(String token, Date currentTime) {
        long timestamp = DateTimeUtil.getSecond(currentTime);
        String key = token + timestamp;
        return TOTP.generateTOTP(key, time, returnDigits);
    }
}
