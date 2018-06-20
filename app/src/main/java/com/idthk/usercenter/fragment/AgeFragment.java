package com.idthk.usercenter.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.idthk.usercenter.R;
import com.idthk.usercenter.view.NumericWheelAdapter;
import com.idthk.usercenter.view.OnWheelScrollListener;
import com.idthk.usercenter.view.WheelView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by williamyin on 2017/12/20.
 */

public class AgeFragment extends BaseFragment {
    private  ViewHolder viewHolder;
    private   int item_age=0;
    public static AgeFragment newInstance(String s) {
        AgeFragment ageFragment = new AgeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("age", s);
        ageFragment.setArguments(bundle);
        return ageFragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.age;
    }

    @Override
    protected void initView(View view) {
         viewHolder=new ViewHolder(view);
    }


    @Override
    protected void initData() {
        item_age=6;
        viewHolder.ageView.setAdapter(new NumericWheelAdapter(1, 100, "%02d"));
        viewHolder.ageView.setVisibleItems(3);
        viewHolder.ageView.setCyclic(true);
        viewHolder.ageView.setCurrentItem(item_age);
        OnWheelScrollListener local1 = new OnWheelScrollListener() {
            public void onScrollingFinished(WheelView paramWheelView) {
                item_age = viewHolder.ageView.getCurrentItem();
            }
            public void onScrollingStarted(WheelView paramWheelView) {
            }
        };
        viewHolder.ageView.addScrollingListener(local1);
    }

    static class ViewHolder {
        @BindView(R.id.text_title)
        TextView textTitle;
        @BindView(R.id.age_view)
        WheelView ageView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
