package com.idthk.usercenter.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.dgrlucky.log.LogX;
import com.idthk.usercenter.R;
import com.idthk.usercenter.activity.ForgetPassWordActivity;
import com.idthk.usercenter.activity.UserRegisActivity;
import com.idthk.usercenter.http.HttpResultFuncNoList;
import com.idthk.usercenter.http.ResponseModel;
import com.idthk.usercenter.http.RetrofitFactory;
import com.idthk.usercenter.utils.ToastUtils;
import com.idthk.usercenter.utils.tools.UtilTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by williamyin on 2017/12/27.
 */

public class LoginDialog extends Dialog implements View.OnClickListener {
    private ViewHolder viewHolder;
    private Context context;

    private String username="18827014238";//用户名
    private String type="tel";//类型 tel or email
    private String password="123";//密码
    private String serial_number="abcd";//设备序列号
    private String device_number="dcba";//设备号



    public LoginDialog(Context context) {
        super(context, R.style.CommonDialog);

        setContentView(R.layout.login);
        this.context=context;
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
        switch (v.getId()){
            case R.id.newuser_regis:
                //跳转到注册界面
                Intent i = new Intent(context, UserRegisActivity.class);
                i.putExtra("isOpen_device",false);
                context.startActivity(i);
                dismiss();
                break;
            case R.id.forget_password:
                //跳转到忘记密码界面
                Intent intent = new Intent(context, ForgetPassWordActivity.class);
                context. startActivity(intent);
                dismiss();
                break;
            case R.id.login:
                //登录
                login(username,type,password,serial_number,device_number);
                break;
        }
    }

    /**
     * 登录
     * @param username
     * @param type
     * @param password
     * @param serial_number
     * @param device_number
     */
    private void login(String username, String type, String password, String serial_number, String device_number) {

//        RetrofitFactory.getInstance().getUserLogin(username,type,password,serial_number,device_number)
//                .map(new HttpResultFuncNoList())
//                .subscribeOn(Schedulers.io())//在工作线程请求网络
//                .observeOn(AndroidSchedulers.mainThread())//在主线程处理结果
//                .subscribe(new Subscriber<ResponseModel>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(ResponseModel responseModel) {
//                        LogX.e(responseModel);
//                    }
//                });
    }

    private void initLayout() {
        viewHolder=new ViewHolder(this);
        viewHolder.newuserRegis.setOnClickListener(this);
        viewHolder.forgetPassword.setOnClickListener(this);
        viewHolder.login.setOnClickListener(this);
        setCanceledOnTouchOutside(false);
        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dismiss();
            }
        });
    }

    static class ViewHolder {
        @BindView(R.id.user_text)
        EditText userText;
        @BindView(R.id.user_password)
        EditText userPassword;
        @BindView(R.id.login)
        Button login;
        @BindView(R.id.forget_password)
        Button forgetPassword;
        @BindView(R.id.newuser_regis)
        Button newuserRegis;

        ViewHolder(Dialog view) {
            ButterKnife.bind(this, view);
        }
    }
}
