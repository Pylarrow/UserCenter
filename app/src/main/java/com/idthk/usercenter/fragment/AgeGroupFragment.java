package com.idthk.usercenter.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.idthk.usercenter.R;
import com.idthk.usercenter.bean.MessageEvent;
import com.idthk.usercenter.bll.UserBll;
import com.idthk.usercenter.utils.Constant;
import com.idthk.usercenter.utils.ModifyUserType;
import com.idthk.usercenter.utils.SharedPreferenceUtils;
import com.idthk.usercenter.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.functions.Action2;

/**
 * Created by pengyu.
 * 2018/6/5
 */
public class AgeGroupFragment extends BaseFragment implements View.OnClickListener {
    private ViewHolder viewHolder;
    private int ageType;
    int userId = SharedPreferenceUtils.getInt(getContext(), Constant.userId, -1);

    public static AgeGroupFragment newInstance(String s) {
        AgeGroupFragment ageFragment = new AgeGroupFragment();
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
        return R.layout.fragment_age_group_layout;
    }

    @Override
    protected void initView(View view) {
        viewHolder = new ViewHolder(view);
        viewHolder.rl_one.setOnClickListener(this);
        viewHolder.rl_two.setOnClickListener(this);
        viewHolder.rl_three.setOnClickListener(this);
        viewHolder.rl_four.setOnClickListener(this);
        viewHolder.rl_five.setOnClickListener(this);
        viewHolder.rl_six.setOnClickListener(this);
        viewHolder.bt_save.setOnClickListener(this);

        //默认年龄段
        clearSelectState();
        viewHolder.rl_one.setSelected(true);

        //从本地读取年龄段
        int ageGroup = SharedPreferenceUtils.getInt(getContext(), Constant.ageGroup, 0);
        if (ageGroup == 1) {
            clearSelectState();
            viewHolder.rl_one.setSelected(true);
        } else if (ageGroup == 2) {
            clearSelectState();
            viewHolder.rl_two.setSelected(true);
        } else if (ageGroup == 3) {
            clearSelectState();
            viewHolder.rl_three.setSelected(true);
        } else if (ageGroup == 4) {
            clearSelectState();
            viewHolder.rl_four.setSelected(true);
        } else if (ageGroup == 5) {
            clearSelectState();
            viewHolder.rl_five.setSelected(true);
        } else if (ageGroup == 6) {
            clearSelectState();
            viewHolder.rl_six.setSelected(true);
        }
    }

    @Override
    protected void initData() {
        String[] ages = getContext().getResources().getStringArray(R.array.age);
        viewHolder.tv_one.setText(ages[0]);
        viewHolder.tv_two.setText(ages[1]);
        viewHolder.tv_three.setText(ages[2]);
        viewHolder.tv_four.setText(ages[3]);
        viewHolder.tv_five.setText(ages[4]);
        viewHolder.tv_six.setText(ages[5]);
    }

    private void clearSelectState() {
        viewHolder.rl_one.setSelected(false);
        viewHolder.rl_two.setSelected(false);
        viewHolder.rl_three.setSelected(false);
        viewHolder.rl_four.setSelected(false);
        viewHolder.rl_five.setSelected(false);
        viewHolder.rl_six.setSelected(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_one:
                clearSelectState();
                ageType=Constant.age_5_9;
                viewHolder.rl_one.setSelected(true);
                break;
            case R.id.rl_two:
                clearSelectState();
                ageType=Constant.age_10_14;
                viewHolder.rl_two.setSelected(true);
                break;
            case R.id.rl_three:
                clearSelectState();
                ageType=Constant.age_15_20;
                viewHolder.rl_three.setSelected(true);
                break;
            case R.id.rl_four:
                clearSelectState();
                ageType=Constant.age_21_30;
                viewHolder.rl_four.setSelected(true);
                break;
            case R.id.rl_five:
                clearSelectState();
                ageType=Constant.age_30_50;
                viewHolder.rl_five.setSelected(true);
                break;
            case R.id.rl_six:
                clearSelectState();
                ageType=Constant.age_50;
                viewHolder.rl_six.setSelected(true);
                break;
            case R.id.bt_save:
                save();
                break;
        }
    }


    /**
     * 保存年龄段
     */
    private void save() {
        new UserBll().updateUserInfo(getContext(), userId, "age_group", ageType + "", new Action1<Object>() {
            @Override
            public void call(Object o) {
                //修改本地年龄信息
                ToastUtils.showMessage(getContext(),getString(R.string.save_agegroup_success));
                setFullScreen();
                SharedPreferenceUtils.setInt(getContext(),Constant.ageGroup,ageType);
                String[] ages = getContext().getResources().getStringArray(R.array.age);
                if (ageType <= 6)
                    EventBus.getDefault().post(new MessageEvent(ages[ageType - 1], ModifyUserType.MODIFY_AGE));

            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {

            }
        });
    }

    static class ViewHolder {

        @BindView(R.id.rl_one)
        RelativeLayout rl_one;
        @BindView(R.id.rl_two)
        RelativeLayout rl_two;
        @BindView(R.id.rl_three)
        RelativeLayout rl_three;
        @BindView(R.id.rl_four)
        RelativeLayout rl_four;
        @BindView(R.id.rl_five)
        RelativeLayout rl_five;
        @BindView(R.id.rl_six)
        RelativeLayout rl_six;
        @BindView(R.id.tv_one)
        TextView tv_one;
        @BindView(R.id.tv_two)
        TextView tv_two;
        @BindView(R.id.tv_three)
        TextView tv_three;
        @BindView(R.id.tv_four)
        TextView tv_four;
        @BindView(R.id.tv_five)
        TextView tv_five;
        @BindView(R.id.tv_six)
        TextView tv_six;
        @BindView(R.id.bt_save)
        Button bt_save;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
