package com.idthk.usercenter.fragment;

import android.annotation.TargetApi;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
public class SixFragment extends BaseFragment implements View.OnClickListener {

    private ViewHolder viewHolder;
    private String sex = Constant.male;
    int userId = SharedPreferenceUtils.getInt(getContext(), Constant.userId, -1);

    public static SixFragment newInstance(String s) {
        SixFragment ageFragment = new SixFragment();
        Bundle bundle = new Bundle();
        bundle.putString("six", s);
        ageFragment.setArguments(bundle);
        return ageFragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_six_layout;
    }

    @Override
    protected void initView(View view) {
        viewHolder=new ViewHolder(view);

        viewHolder.ll_mail.setOnClickListener(this);
        viewHolder.ll_femail.setOnClickListener(this);
        viewHolder.bt_save.setOnClickListener(this);

        String sex = SharedPreferenceUtils.getString(getContext(), Constant.sex, "");
        if(sex.equals(Constant.male)){
            viewHolder.iv_mail.setSelected(true);
            viewHolder.iv_femail.setSelected(false);
            viewHolder.tv_mail.setTextColor(getResources().getColor(R.color.top_color));
            viewHolder.tv_femail.setTextColor(getResources().getColor(R.color.color_111111));
        }else{
            viewHolder.iv_mail.setSelected(false);
            viewHolder.iv_femail.setSelected(true);
            viewHolder.tv_mail.setTextColor(getResources().getColor(R.color.color_111111));
            viewHolder.tv_femail.setTextColor(getResources().getColor(R.color.top_color));
        }
    }

    @Override
    protected void initData() {

    }



    private void save(final String sex) {
        new UserBll().updateUserInfo(getContext(), userId, Constant.sex, sex, new Action1<Object>() {
            @Override
            public void call(Object o) {
                ToastUtils.showMessage(getContext(),getString(R.string.save_sex_success));
                SharedPreferenceUtils.setString(getContext(),Constant.sex,sex);
                EventBus.getDefault().post(new MessageEvent(sex, ModifyUserType.MODIFY_SEX));
                setFullScreen();
            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {

            }
        });
    }

    private void clearStatus(){
        viewHolder.iv_mail.setSelected(false);
        viewHolder.iv_femail.setSelected(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_mail:
                clearStatus();
                sex=Constant.male;
                viewHolder.iv_mail.setSelected(true);
                viewHolder.tv_mail.setTextColor(getResources().getColor(R.color.top_color));
                viewHolder.tv_femail.setTextColor(getResources().getColor(R.color.color_111111));
                break;
            case R.id.ll_femail:
                clearStatus();
                sex=Constant.female;
                viewHolder.iv_femail.setSelected(true);
                viewHolder.tv_mail.setTextColor(getResources().getColor(R.color.color_111111));
                viewHolder.tv_femail.setTextColor(getResources().getColor(R.color.top_color));
                break;
            case R.id.bt_save:
                save(sex);
                break;
        }
    }

    static class ViewHolder {

        @BindView(R.id.bt_save)
        Button bt_save;
        @BindView(R.id.ll_mail)
        LinearLayout ll_mail;
        @BindView(R.id.iv_mail)
        ImageView iv_mail;
        @BindView(R.id.tv_mail)
        TextView tv_mail;
        @BindView(R.id.ll_femail)
        LinearLayout ll_femail;
        @BindView(R.id.iv_femail)
        ImageView iv_femail;
        @BindView(R.id.tv_femail)
        TextView tv_femail;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
