package com.idthk.usercenter.fragment;

import android.os.Bundle;
import android.view.View;

import com.idthk.usercenter.R;


/**
 * Created by williamyin on 2017/12/20.
 */

public class VpiFragment extends BaseFragment{
    public static VpiFragment newInstance(String s) {
        VpiFragment vpiFragment = new VpiFragment();
        Bundle bundle = new Bundle();
        bundle.putString("vpi", s);
        vpiFragment.setArguments(bundle);
        return vpiFragment;
    }
    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.vpi;
    }

    @Override
    protected void initView(View view) {

    }
    @Override
    protected void initData() {
    }
}
