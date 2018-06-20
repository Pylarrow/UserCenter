package com.idthk.usercenter.activity;

import android.app.Activity;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dgrlucky.log.LogX;
import com.idthk.usercenter.R;
import com.idthk.usercenter.bean.response.SendSmsRspBean;
import com.idthk.usercenter.bll.UserBll;
import com.idthk.usercenter.utils.CheckUtils;
import com.idthk.usercenter.utils.tools.UtilTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.functions.Action2;

/**
 * Created by pengyu.
 * 2018/3/12
 */

public class NewForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private ViewHolder viewHolder;
    private String sendVericode = "";
    private String type = "tel";

    private boolean isPwdType = true;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_forget_password_layout;
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
        viewHolder.bt_send.setOnClickListener(this);
        viewHolder.bt_reset_password.setOnClickListener(this);
        viewHolder.iv_eye_close.setOnClickListener(this);
        viewHolder.iv_back.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_send://发送验证码
                sendVericode();
                break;
            case R.id.bt_reset_password://重置密码
                resetPwd();
                break;
            case R.id.iv_eye_close:
                switchPwdType();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    /**
     * 切换明文跟密文
     */
    private void switchPwdType() {
        isPwdType = !isPwdType;
        if (isPwdType) {
            viewHolder.et_new_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            viewHolder.iv_eye_close.setImageDrawable(getResources().getDrawable(R.drawable.eye_close));
        } else {
            viewHolder.et_new_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            viewHolder.iv_eye_close.setImageDrawable(getResources().getDrawable(R.drawable.eye));
        }
    }

    /**
     * 重置密码
     */
    private void resetPwd() {
        String username = viewHolder.et_username.getText().toString();
        String password = viewHolder.et_new_password.getText().toString();
        String vericode = viewHolder.et_confirm.getText().toString().trim();
        //验证码非空验证
        if (TextUtils.isEmpty(vericode)) {
            Toast.makeText(getContext(), getResources().getString(R.string.register_vericode_notnull_desc), Toast.LENGTH_SHORT).show();
            return;
        }
        //验证码有效性验证
        if (!vericode.equals(sendVericode)) {
            Toast.makeText(getContext(), getResources().getString(R.string.register_vericode_desc), Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getContext(), getResources().getString(R.string.register_username_desc), Toast.LENGTH_SHORT).show();
            return;
        }
        if (CheckUtils.isPhone(username)) {
            type = "tel";
        }
        if (CheckUtils.isEmail(username)) {
            type = "email";
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), getResources().getString(R.string.register_password_desc), Toast.LENGTH_SHORT).show();
            return;
        }
        if (CheckUtils.isPwd(password)) {
            Toast.makeText(getContext(), getResources().getString(R.string.password_vericate_tips), Toast.LENGTH_SHORT).show();
        }

        new UserBll().resetPwd(getContext(), type, username, password, new Action1<Object>() {
            @Override
            public void call(Object o) {
                Toast.makeText(getContext(), R.string.reset_password_success, Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        NewForgetPasswordActivity.this.finish();
                    }
                }, 1500);    //延时1s执行
            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {
                if (integer == 400) {
                    Toast.makeText(NewForgetPasswordActivity.this, R.string.not_register_user, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(NewForgetPasswordActivity.this,s,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 发送验证码
     */
    private void sendVericode() {
        final String phone_email = viewHolder.et_username.getText().toString().trim();
        if (TextUtils.isEmpty(phone_email)) {
            Toast.makeText(this, R.string.register_username_desc, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!CheckUtils.isPhone(phone_email) && !CheckUtils.isEmail(phone_email)) {
            Toast.makeText(this, getResources().getString(R.string.please_input_right_phone), Toast.LENGTH_SHORT).show();
            return;
        }
        //判断输入的号码是否是注册用户  不是是注册用户不发短信或邮件
        new UserBll().validateBeforeResetPassword(NewForgetPasswordActivity.this, type, phone_email, new Action1<Object>() {
            @Override
            public void call(Object o) {
                //验证成功 发送验证码
                send(phone_email);
            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {
                //验证失败，该用户不是注册用户
                if(integer==400){
                    Toast.makeText(NewForgetPasswordActivity.this, R.string.not_register_user, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(NewForgetPasswordActivity.this,s,Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void send(String phone_email) {
        //判断用户名是手机号还是邮箱 手机号发短信 邮箱发邮件
        if (CheckUtils.isEmail(phone_email)) {
            //发邮件
            new UserBll().sendEmail(NewForgetPasswordActivity.this, phone_email, new Action1<SendSmsRspBean>() {
                @Override
                public void call(SendSmsRspBean sendSmsRspBean) {
                    sendVericode = sendSmsRspBean.getRandNum();
                    //验证码发送成功后
                    /** 倒计时180秒，一次1秒 */
                    CountDownTimer timer = new CountDownTimer(60 * 1000 * 3, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // TODO Auto-generated method stub
                            viewHolder.bt_send.setText(millisUntilFinished / 1000 + getString(R.string.sencond_desc));
                            viewHolder.bt_send.setEnabled(false);
                        }

                        @Override
                        public void onFinish() {
                            viewHolder.bt_send.setText(getResources().getString(R.string.send_sms));
                            viewHolder.bt_send.setEnabled(true);
                        }
                    }.start();
                }
            }, new Action2<Integer, String>() {
                @Override
                public void call(Integer integer, String s) {
                    Toast.makeText(NewForgetPasswordActivity.this,s,Toast.LENGTH_SHORT).show();
                }
            });
        } else if (CheckUtils.isPhone(phone_email)) {
            //发短信
            new UserBll().sendSms(NewForgetPasswordActivity.this, phone_email, new Action1<SendSmsRspBean>() {
                @Override
                public void call(SendSmsRspBean sendSmsRspBean) {
                    LogX.e(sendSmsRspBean);
                    sendVericode = sendSmsRspBean.getRandNum();
                    //验证码发送成功后
                    /** 倒计时180秒，一次1秒 */
                    CountDownTimer timer = new CountDownTimer(60 * 1000 * 3, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // TODO Auto-generated method stub
                            viewHolder.bt_send.setText(millisUntilFinished / 1000 + getString(R.string.sencond_desc));
                            viewHolder.bt_send.setEnabled(false);
                        }

                        @Override
                        public void onFinish() {
                            viewHolder.bt_send.setText(getResources().getString(R.string.send_sms));
                            viewHolder.bt_send.setEnabled(true);
                        }
                    }.start();

                }
            }, new Action2<Integer, String>() {
                @Override
                public void call(Integer integer, String s) {
                    if (integer == 400) {
                        Toast.makeText(NewForgetPasswordActivity.this, R.string.send_sms_error_info, Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(NewForgetPasswordActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    static class ViewHolder {

        @BindView(R.id.et_username)
        EditText et_username;
        @BindView(R.id.et_confirm)
        EditText et_confirm;
        @BindView(R.id.et_new_password)
        EditText et_new_password;
        @BindView(R.id.bt_send)
        Button bt_send;
        @BindView(R.id.bt_reset_password)
        Button bt_reset_password;
        @BindView(R.id.iv_eye_close)
        ImageView iv_eye_close;
        @BindView(R.id.iv_back)
        ImageView iv_back;

        ViewHolder(Activity view) {
            ButterKnife.bind(this, view);
        }
    }
}
