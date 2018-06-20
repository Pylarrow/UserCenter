package com.idthk.usercenter.activity;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.idthk.usercenter.R;
import com.idthk.usercenter.utils.tools.UtilTools;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pengyu.
 * 2018/3/7
 */

public class PayChoiceActivity extends BaseActivity implements View.OnClickListener{
    private ViewHolder viewHolder;
    @Override
    protected int getLayoutId() {
        return R.layout.dialog_pay_layout;
    }

    @Override
    protected void initView() {
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = UtilTools.getScreenWidth(getContext()) - 300;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        viewHolder = new ViewHolder(this);

        viewHolder.ll_pay_wx.setOnClickListener(this);
        viewHolder.ll_pay_alipay.setOnClickListener(this);
        viewHolder.ll_pay_paypal.setOnClickListener(this);
        viewHolder.bt_pay.setOnClickListener(this);
        viewHolder.iv_close.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_pay_wx:
                //微信支付
                viewHolder.ll_pay_wx.setSelected(true);
                viewHolder.ll_pay_alipay.setSelected(false);
                viewHolder.ll_pay_paypal.setSelected(false);
                break;
            case R.id.ll_pay_alipay:
                //支付宝支付
                viewHolder.ll_pay_wx.setSelected(false);
                viewHolder.ll_pay_alipay.setSelected(true);
                viewHolder.ll_pay_paypal.setSelected(false);
                break;
            case R.id.ll_pay_paypal:
                viewHolder.ll_pay_wx.setSelected(false);
                viewHolder.ll_pay_alipay.setSelected(false);
                viewHolder.ll_pay_paypal.setSelected(true);
                //paypal支付
                break;
            case R.id.bt_pay:
                //跳转到支付界面
                Toast.makeText(this, R.string.pay,Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_close:
                finish();
                break;
        }
    }


    static class ViewHolder {

        @BindView(R.id.ll_pay_wx)
        LinearLayout ll_pay_wx;
        @BindView(R.id.ll_pay_alipay)
        LinearLayout ll_pay_alipay;
        @BindView(R.id.ll_pay_paypal)
        LinearLayout ll_pay_paypal;
        @BindView(R.id.bt_pay)
        Button bt_pay;
        @BindView(R.id.iv_close)
        ImageView iv_close;

        ViewHolder(Activity view) {
            ButterKnife.bind(this, view);
        }
    }
}
