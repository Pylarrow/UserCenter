package com.idthk.usercenter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.dgrlucky.log.LogX;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.idthk.network.http.AppRetrofit;
import com.idthk.network.http.Builder;
import com.idthk.usercenter.config.parse.OkHttpResponseParse;
import com.idthk.usercenter.utils.HostAddress;
import com.idthk.usercenter.utils.tools.FrescoConfig;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by pengyu.
 * 2018/1/31
 */

public class APP extends Application {
    private static APP mContext;
    private static Context context;
    private List<Activity> mList = new LinkedList<Activity>();
    private static APP instance;

    public static HostAddress environment;

    public static APP getContext() {
        return mContext;
    }

    public static Context getMyContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        environment = HostAddress.TEST;
        mContext = (APP) getApplicationContext();
        AppRetrofit.getSingleton().init(new Builder().setHostPath(environment.getAddress()).setAppClass(CloudApi.class));
        Fresco.initialize(this, FrescoConfig.getsImagePipelineConfig(this));
        LogX.init(BuildConfig.DEBUG, OkHttpResponseParse.class);
        CrashReport.initCrashReport(getApplicationContext(), "310e862781", false);
    }

    public synchronized static APP getInstance() {
        if (null == instance) {
            instance = new APP();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
                //             activity.finishActivity(token, Activity.RESULT_CANCELED, null);//执行销毁
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    private String getVersion() {
        PackageManager manager;
        String version = "";
        PackageInfo info = null;
        manager = this.getPackageManager();
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
            version = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return version;

    }
}
