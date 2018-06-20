package com.idthk.usercenter.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by qinghua.liu on 2016/5/23.
 */
public class ToastUtils {
    public static void showMessage(Context context, String message) {
        if (context == null || TextUtils.isEmpty(message)) return;
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showMessage(Context context, int id) {
        if (context == null || id < 0) return;
        Toast.makeText(context, context.getString(id), Toast.LENGTH_SHORT).show();
    }

}
