package com.idthk.usercenter.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.idthk.usercenter.R;
import com.idthk.usercenter.bll.UserBll;
import com.idthk.usercenter.utils.ToastUtils;
import com.idthk.usercenter.utils.tools.UtilTools;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pengyu.
 * 2018/2/27
 */

public class PayChoiceDialog extends Dialog implements View.OnClickListener {
    private ViewHolder viewHolder;
    private Context context;
    private UserBll bll = new UserBll();


    public PayChoiceDialog(Context context) {
        super(context, R.style.CommonDialog);

        setContentView(R.layout.dialog_pay_layout);
        this.context = context;
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = UtilTools.getScreenWidth(context) - 300;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        show();
        initLayout();
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
                //支付
                break;
            case R.id.iv_close:
                dismiss();
                break;
        }
    }

    private void initLayout() {
        viewHolder = new ViewHolder(this);
        setCanceledOnTouchOutside(false);
        setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dismiss();
            }
        });

        viewHolder.ll_pay_wx.setOnClickListener(this);
        viewHolder.ll_pay_alipay.setOnClickListener(this);
        viewHolder.ll_pay_paypal.setOnClickListener(this);
        viewHolder.bt_pay.setOnClickListener(this);
        viewHolder.iv_close.setOnClickListener(this);
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

        ViewHolder(Dialog view) {
            ButterKnife.bind(this, view);
        }
    }
}
