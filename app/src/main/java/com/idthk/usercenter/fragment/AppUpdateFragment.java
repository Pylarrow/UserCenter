package com.idthk.usercenter.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.idthk.usercenter.R;


/**
 * Created by williamyin on 2017/12/20.
 */

public class AppUpdateFragment extends BaseFragment{
    public static AppUpdateFragment newInstance(String s) {
        AppUpdateFragment appUpdateFragment = new AppUpdateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("appupdate", s);
        appUpdateFragment.setArguments(bundle);
        return appUpdateFragment;
    }
    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_update;
    }

    @Override
    protected void initView(View view) {

    }
    @Override
    protected void initData() {
        Log.i("onClickree: ","来了");
    }
}
