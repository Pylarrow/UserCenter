package com.idthk.usercenter.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.idthk.usercenter.R;
import com.idthk.usercenter.activity.ParentalVericatActivity;
import com.idthk.usercenter.utils.Constant;
import com.idthk.usercenter.utils.DateUtils;
import com.idthk.usercenter.utils.SharedPreferenceUtils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pengyu.
 * 2018/6/6
 */
public class NewDeviceFragment extends BaseFragment implements View.OnClickListener {

    ViewHolder viewHolder;
    private boolean expired;
    private long activate_time;
    private long expire_time;
    private String language="";
    private String payType = Constant.WEIXIN;//默认微信支付

    public static NewDeviceFragment newInstance(String s) {
        NewDeviceFragment userGradeFragment = new NewDeviceFragment();
        Bundle bundle = new Bundle();
        bundle.putString("device", s);
        userGradeFragment.setArguments(bundle);
        return userGradeFragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_device_layout;
    }

    @Override
    protected void initView(View view) {
        viewHolder = new ViewHolder(view);
        viewHolder.bt_new_renew.setOnClickListener(this);
        viewHolder.ll_pay_wx.setOnClickListener(this);
        viewHolder.ll_pay_paypal.setOnClickListener(this);
        viewHolder.bt_new_renew2.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    @Override
    protected void initData() {
        getDeviceInfo();
        getLocalLanguage();
        getRenewPrice();
        //默认选择微信支付
        viewHolder.ll_pay_wx.setSelected(true);
        viewHolder.ll_pay_paypal.setSelected(false);
        payType = Constant.WEIXIN;
    }


    /**
     * 获取本地语言环境
     */
    private void getLocalLanguage() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = getResources().getConfiguration().locale;
        }

        //获取语言的正确姿势
        String lang = locale.getLanguage() + "-" + locale.getCountry();
        if(lang.contains("en")){
            language="usd";
        }else if(lang.contains("zh")){
            language="rmb";
        }
    }

    /**
     * 获取续费价格
     */
    private void getRenewPrice() {
        String price = SharedPreferenceUtils.getString(getContext(), Constant.renewPrice, "");
        if(language.equals("rmb")){
            viewHolder.tv_new_renew_price.setText(getString(R.string.rmb)+price+getString(R.string.preyear));
        }else if(language.equals("usd")){
            viewHolder.tv_new_renew_price.setText(getString(R.string.usd)+price+getString(R.string.preyear));
        }
    }

    private void getDeviceInfo() {

        expired=SharedPreferenceUtils.getBoolean(getContext(),Constant.expired,false);
        expired=true;
        activate_time=SharedPreferenceUtils.getLong(getContext(),Constant.activate_time,-1);
        expire_time=SharedPreferenceUtils.getLong(getContext(),Constant.expire_time,-1);
        viewHolder.tv_date.setText(getString(R.string.activate_time)+DateUtils.stampToDate(activate_time * 1000 + ""));
        viewHolder.tv_new_end_time.setText(getString(R.string.validity_desc) + DateUtils.stampToDate(activate_time * 1000 + "") + "-" + DateUtils.stampToDate(expire_time * 1000 + ""));
        if (expired) {//设备过期
            viewHolder.iv_overdue.setVisibility(View.VISIBLE);
            viewHolder.bt_new_renew.setTextColor(getResources().getColor(R.color.white));
            viewHolder.bt_new_renew.setEnabled(true);

        } else {//设备未过期
            viewHolder.iv_overdue.setVisibility(View.GONE);
            viewHolder.bt_new_renew.setTextColor(getResources().getColor(R.color.gray));
            viewHolder.bt_new_renew.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_new_renew:
                showRenew();
                break;
            case R.id.ll_pay_wx:
                clearStatus();
                viewHolder.ll_pay_wx.setSelected(true);
                payType=Constant.WEIXIN;
                break;
            case R.id.ll_pay_paypal:
                clearStatus();
                viewHolder.ll_pay_paypal.setSelected(true);
                payType=Constant.PAYPAL;
                break;
            case R.id.bt_new_renew2:
                //先跳转到家长验证界面 同时将支付类型传给下个界面
                Intent intent = new Intent(getContext(), ParentalVericatActivity.class);
                intent.putExtra("payType", payType);
                startActivity(intent);
                break;

        }
    }

    private void clearStatus(){
        viewHolder.ll_pay_wx.setSelected(false);
        viewHolder.ll_pay_paypal.setSelected(false);
    }

    /**
     * 续费
     */
    private void showRenew() {
        viewHolder.rl_device_content1.setVisibility(View.GONE);
        viewHolder.bt_new_renew.setVisibility(View.GONE);

        viewHolder.rl_device_content2.setVisibility(View.VISIBLE);
    }

    static class ViewHolder {

        @BindView(R.id.iv_overdue)
        ImageView iv_overdue;
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.tv_xlh)
        TextView tv_xlh;
        @BindView(R.id.tv_new_end_time)
        TextView tv_new_end_time;
        @BindView(R.id.bt_new_renew)
        Button bt_new_renew;
        @BindView(R.id.rl_device_content1)
        RelativeLayout rl_device_content1;

        @BindView(R.id.tv_new_renew_price)
        TextView tv_new_renew_price;
        @BindView(R.id.tv_detail)
        TextView tv_detail;
        @BindView(R.id.ll_pay_wx)
        LinearLayout ll_pay_wx;
        @BindView(R.id.ll_pay_paypal)
        LinearLayout ll_pay_paypal;
        @BindView(R.id.bt_new_renew2)
        Button bt_new_renew2;
        @BindView(R.id.rl_device_content2)
        LinearLayout rl_device_content2;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
