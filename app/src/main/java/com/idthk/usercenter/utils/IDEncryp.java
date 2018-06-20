package com.idthk.usercenter.utils;

/**
 * 自定义用户ID可逆加密算法
 * <p>
 * 逻辑简单，数字置换
 * 0 -> 1
 * 1 -> 0
 * 2 -> 7
 * 3 -> 6
 * 4 -> 4
 * 5 -> 9
 * 6 -> 3
 * 7 -> 2
 * 8 -> 8
 * 9 -> 5
 * <p>
 * <p>
 * 之后再使用随机数异或加密，避免相同信息出现
 * </p>
 *
 * @author huangrui
 */
public class IDEncryp {

    private static int code_length = 10;

    /**
     * 加密id生成10位数字
     *
     * @param code
     * @return
     */
    public static String encode(String code, String key)  {
        if (code == null || "".equals(code)) {
            return code;
        }
        return encrypt(kernel(wrap(code)), key);
    }

    /**
     * 解密还原id
     *
     * @param code
     * @return
     */
    public static String decode(String code, String key)  {
        if (code == null || "".equals(code)) {
            return code;
        }
        return filter(kernel(encrypt(code, key)));
    }

    // 核心算法
    private static String kernel(String code) {
        StringBuffer sb = new StringBuffer(10);
        for (int i = 0; i < code.length(); i++) {
            sb.append(inversion(charToInt(code.charAt(i))));
        }
        return sb.toString();
    }


    private static int charToInt(char c) {
        char[] cArray = {c};
        return Integer.parseInt(new String(cArray));
    }

    private static int inversion(int input) {
        if (input == 0)
            return 1;
        if (input == 1)
            return 0;
        if (input == 2)
            return 7;
        if (input == 3)
            return 6;
        if (input == 4)
            return 4;
        if (input == 5)
            return 9;
        if (input == 6)
            return 3;
        if (input == 7)
            return 2;
        if (input == 8)
            return 8;
        if (input == 9)
            return 5;
        else
            return input;
    }

    // 不够指定位数前面补0
    private static String wrap(String code) {
        StringBuffer sb = new StringBuffer();
        if (code.length() < code_length) {
            for (int i = 0; i < code_length - code.length(); i++) {
                sb.append("0");
            }
        }
        sb.append(code);
        return sb.toString();
    }

    // 去掉字符串前面的补零
    private static String filter(String code) {
        StringBuffer sb = new StringBuffer();
        boolean firstFlag = true;
        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);
            int fc = charToInt(c);
            if (firstFlag && fc == 0) {
                continue;
            } else {
                firstFlag = false;
                sb.append(fc);
            }
        }
        return sb.toString();
    }

    // 在进行简单异或加密
    public static String encrypt(String code, String key) {
        try {
            int codeInt = Integer.parseInt(code);
            int keyInt = Integer.parseInt(key);
            int result = codeInt ^ keyInt;
            return result + "";
        } catch (Exception e) {
            return code;
        }
    }

    public static String encrypt2(String strOld, String strKey) {
        StringBuffer result = new StringBuffer();
        char[] data = strOld.toCharArray();
        char[] keyData = strKey.toCharArray();
        int keyIndex = 0 ;
        for(int x = 0 ; x < strOld.length() ; x++) {
            data[x] = (char) (data[x] ^ keyData[keyIndex]);
            if (++keyIndex == keyData.length){
                keyIndex = 0;
            }
            result.append((int)data[x]);
        }
        return result.toString();
    }


    public static void main(String[] args) {
        String id = "3097497777";
        String key = "5211";
        String encode = encode(id, key);
        System.out.println("加密前：" + id);
        System.out.println("加密后：" + encode);
        System.out.println("解密后：" + decode(encode, key));

    }

}
