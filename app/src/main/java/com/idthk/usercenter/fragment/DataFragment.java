package com.idthk.usercenter.fragment;

import android.os.Bundle;
import android.view.View;

import com.idthk.usercenter.R;


/**
 * Created by williamyin on 2017/12/20.
 */

public class DataFragment extends BaseFragment{
    public static DataFragment newInstance(String s) {
        DataFragment dataFragment = new DataFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data", s);
        dataFragment.setArguments(bundle);
        return dataFragment;
    }
    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.data;
    }

    @Override
    protected void initView(View view) {

    }
    @Override
    protected void initData() {
    }
}
