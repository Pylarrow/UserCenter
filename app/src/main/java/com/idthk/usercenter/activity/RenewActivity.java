package com.idthk.usercenter.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.idthk.usercenter.R;
import com.idthk.usercenter.bean.response.DeviceInfoBean;
import com.idthk.usercenter.bll.UserBll;
import com.idthk.usercenter.utils.Constant;
import com.idthk.usercenter.utils.DateUtils;
import com.idthk.usercenter.utils.SharedPreferenceUtils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.functions.Action2;

/**
 * Created by pengyu.
 * 2018/4/13
 */
public class RenewActivity extends BaseActivity implements View.OnClickListener {

    private ViewHolder viewHolder;
    private String payType = Constant.WEIXIN;//默认微信支付
    private String language = "";
    private boolean expired;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_renew_layout;
    }

    @Override
    protected void initView() {
        viewHolder = new ViewHolder(this);
        viewHolder.bt_info_renew.setOnClickListener(this);
        viewHolder.ll_pay_wx.setOnClickListener(this);
        viewHolder.ll_pay_alipay.setOnClickListener(this);
        viewHolder.ll_pay_paypal.setOnClickListener(this);
        viewHolder.bt_renew.setOnClickListener(this);
        viewHolder.iv_back.setOnClickListener(this);

        //默认选择微信支付
        viewHolder.ll_pay_wx.setSelected(true);
        viewHolder.ll_pay_alipay.setSelected(false);
        viewHolder.ll_pay_paypal.setSelected(false);
        payType = Constant.WEIXIN;

        getLocalLanguage();
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
        if (lang.contains("en")) {
            language = "usd";
        } else if (lang.contains("zh")) {
            language = "rmb";
        }
    }

    @Override
    protected void initData() {
        getDeviceInfo();
    }

    private void getDeviceInfo() {
        //获取设备ID
        int deviceId = SharedPreferenceUtils.getInt(getContext(), Constant.deviceId);
        //获取设备信息
        new UserBll().getDeviceInfo(getContext(), deviceId, new Action1<DeviceInfoBean>() {
            @Override
            public void call(DeviceInfoBean deviceInfoBean) {
                expired = deviceInfoBean.isExpired();
                expired = true;
                long activate_time = deviceInfoBean.getActivate_time();//第一次激活时间
                long expire_time = deviceInfoBean.getExpire_time();//到期时间
                //将时间转换成时间戳
                viewHolder.tv_date.setText(DateUtils.stampToDate(activate_time * 1000 + ""));
                viewHolder.tv_end_time.setText(getString(R.string.validity_desc) + DateUtils.stampToDate(activate_time * 1000 + "") + "-" + DateUtils.stampToDate(expire_time * 1000 + ""));

                //获取设备信息成功
                if (expired) {//设备过期
                    viewHolder.iv_expired.setVisibility(View.VISIBLE);
                    viewHolder.bt_renew.setVisibility(View.VISIBLE);
                } else {//设备未过期
                    viewHolder.iv_expired.setVisibility(View.GONE);
                    viewHolder.bt_renew.setVisibility(View.GONE);
                }
            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_info_renew:
                //先跳转到家长验证界面 同时将支付类型传给下个界面
                Intent intent = new Intent(getContext(), ParentalVericatActivity.class);
                intent.putExtra("payType", payType);
                startActivity(intent);
                break;
            case R.id.ll_pay_wx://微信支付
                viewHolder.ll_pay_wx.setSelected(true);
                viewHolder.ll_pay_alipay.setSelected(false);
                viewHolder.ll_pay_paypal.setSelected(false);
                payType = Constant.WEIXIN;
                break;
            case R.id.ll_pay_alipay://支付宝支付
                viewHolder.ll_pay_wx.setSelected(false);
                viewHolder.ll_pay_alipay.setSelected(true);
                viewHolder.ll_pay_paypal.setSelected(false);
                payType = Constant.ALIPAY;
                break;
            case R.id.ll_pay_paypal://paypal支付
                viewHolder.ll_pay_wx.setSelected(false);
                viewHolder.ll_pay_alipay.setSelected(false);
                viewHolder.ll_pay_paypal.setSelected(true);
                payType = Constant.PAYPAL;
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    static class ViewHolder {

        @BindView(R.id.bt_info_renew)
        Button bt_info_renew;
        @BindView(R.id.ll_pay_wx)
        LinearLayout ll_pay_wx;
        @BindView(R.id.ll_pay_alipay)
        LinearLayout ll_pay_alipay;
        @BindView(R.id.ll_pay_paypal)
        LinearLayout ll_pay_paypal;
        @BindView(R.id.iv_expired)
        ImageView iv_expired;
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.tv_xlh)
        TextView tv_xlh;
        @BindView(R.id.tv_end_time)
        TextView tv_end_time;
        @BindView(R.id.bt_renew)
        Button bt_renew;
        @BindView(R.id.rl_device)
        RelativeLayout rl_device;
        @BindView(R.id.rl_info_device)
        LinearLayout rl_info_device;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.iv_back)
        ImageView iv_back;

        ViewHolder(Activity view) {
            ButterKnife.bind(this, view);
        }
    }
}
