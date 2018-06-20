package com.idthk.usercenter.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.idthk.network.dialog.WaitDialog;
import com.idthk.usercenter.R;
import com.idthk.usercenter.utils.Constant;
import com.idthk.usercenter.utils.SharedPreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pengyu.
 * 2018/3/26
 */

public class PayViewActivity extends BaseActivity implements View.OnClickListener {

    private ViewHolder viewHolder;

    private WaitDialog dialog;
    private String payType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_payview_layout;
    }

    @Override
    protected void initView() {
        viewHolder = new ViewHolder(this);

        viewHolder.iv_back.setOnClickListener(this);

        //支持javascript
        viewHolder.wv_payview.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        viewHolder.wv_payview.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        viewHolder.wv_payview.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        viewHolder.wv_payview.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        viewHolder.wv_payview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        viewHolder.wv_payview.getSettings().setLoadWithOverviewMode(true);


        //如果不设置WebViewClient，请求会跳转系统浏览器
        viewHolder.wv_payview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //该方法在Build.VERSION_CODES.LOLLIPOP以前有效，从Build.VERSION_CODES.LOLLIPOP起，建议使用shouldOverrideUrlLoading(WebView, WebResourceRequest)} instead
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242

//                if (url.toString().contains("sina.cn")) {
//                    view.loadUrl("http://ask.csdn.net/questions/178242");
//                    return true;
//                }

                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//                    return true;
//                }

                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if(payType.equals(Constant.WEIXIN)){
                    dialog.dismiss();
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if(payType.equals(Constant.WEIXIN)){
                    dialog = new WaitDialog.Builder(PayViewActivity.this, "").create();
                    dialog.show();
                }
            }
        });
        //获取支付方式
        payType = getIntent().getStringExtra("payType");
        //获取用户id和设备id
        int userId = SharedPreferenceUtils.getInt(getContext(), Constant.userId, -1);
        int deviceId = SharedPreferenceUtils.getInt(getContext(), Constant.deviceId, -1);
        if(payType.equals(Constant.WEIXIN)){
            //微信支付
            viewHolder.wv_payview.loadUrl(Constant.payUrl + Constant.APPEND_USER_ID + userId + Constant.APPEND_DEVICE_ID + deviceId);
        }else if(payType.equals(Constant.ALIPAY)){
            //支付宝支付
            Toast.makeText(getContext(),"alipay",Toast.LENGTH_SHORT).show();
        }else if(payType.equals(Constant.PAYPAL)){
            //paypal支付
            viewHolder.wv_payview.loadUrl(Constant.payPalUrl + Constant.APPEND_USER_ID + userId + Constant.APPEND_DEVICE_ID + deviceId+Constant.PAYPAL_TYPE);
        }

    }

    @Override
    protected void initData() {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }

    static class ViewHolder {

        @BindView(R.id.wv_payview)
        WebView wv_payview;
        @BindView(R.id.iv_back)
        ImageView iv_back;

        ViewHolder(Activity view) {
            ButterKnife.bind(this, view);
        }
    }
}
