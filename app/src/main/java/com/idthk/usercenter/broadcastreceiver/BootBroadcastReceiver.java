package com.idthk.usercenter.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.idthk.usercenter.APP;
import com.idthk.usercenter.activity.UserRegisActivity;
import com.idthk.usercenter.utils.SharedPreferenceUtils;

/**
 * Created by williamyin on 2017/12/25.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isfirst = SharedPreferenceUtils.getBoolean(APP.getContext(),"first_opendevice", false);
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Log.i("initDadta: ","开机广播");
            if (!isfirst) {
                Intent i = new Intent(context, UserRegisActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("isOpen_device",true);
                context.startActivity(i);

            }
        }
    }
}
