package com.idthk.usercenter.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.dgrlucky.log.LogX;
import com.idthk.usercenter.R;
import com.idthk.usercenter.bean.response.LoginRspBean;
import com.idthk.usercenter.bll.UserBll;
import com.idthk.usercenter.http.HttpResultFuncNoList;
import com.idthk.usercenter.http.ResponseModel;
import com.idthk.usercenter.http.RetrofitFactory;
import com.idthk.usercenter.utils.MD5Utils;
import com.idthk.usercenter.utils.tools.UtilTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.schedulers.Schedulers;

/**
 * Created by pengyu.
 * 2018/2/5
 */

public class NewLoginDialog extends Dialog implements View.OnClickListener {
    private ViewHolder viewHolder;
    private Context context;

    private UserBll bll=new UserBll();

    private String username = "18827014238";//用户名
    private String type = "tel";//类型 tel or email
    private String password = "123";//密码
    private String serial_number = "abcd";//设备序列号
    private String device_number = "dcba";//设备号


    public NewLoginDialog(Context context) {
        super(context, R.style.CommonDialog);

        setContentView(R.layout.dialog_new_login);
        this.context = context;
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = UtilTools.getScreenWidth(context) - 300;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        show();
        initLayout();
        initData();
    }

    private void initData() {
        String str = context.getResources().getString(R.string.forget_password_desc);
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new UnderlineSpan(), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.top_color));
        spannableString.setSpan(colorSpan, 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        viewHolder.tv_forget_password.setText(spannableString);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forget_password:
                //跳转到忘记密码界面
                new ForgetPassWordDialog(context);
                break;
            case R.id.bt_register:
                //注册
                new RegisterDialog(context);
                this.dismiss();
                break;
            case R.id.bt_login:
                //登录
                login(username,type,password,serial_number,device_number);
                break;
        }
    }

    /**
     * 登录
     *
     * @param username
     * @param type
     * @param password
     * @param serial_number
     * @param device_number
     */
    private void login(String username, String type, String password, String serial_number, String device_number) {
        //校验用户信息

        bll.login(context, username, type, password, serial_number, device_number, new Action1<LoginRspBean>() {
            @Override
            public void call(LoginRspBean loginRspBean) {
                //登录成功
            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {

            }
        });
    }

    private void initLayout() {
        viewHolder = new ViewHolder(this);
        viewHolder.bt_register.setOnClickListener(this);
        viewHolder.tv_forget_password.setOnClickListener(this);
        viewHolder.bt_login.setOnClickListener(this);
        setCanceledOnTouchOutside(false);
        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dismiss();
            }
        });
    }

    static class ViewHolder {

        @BindView(R.id.tv_forget_password)
        TextView tv_forget_password;
        @BindView(R.id.bt_register)
        Button bt_register;
        @BindView(R.id.bt_login)
        Button bt_login;

        ViewHolder(Dialog view) {
            ButterKnife.bind(this, view);
        }
    }
}
