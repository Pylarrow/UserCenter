package com.idthk.usercenter.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dgrlucky.log.LogX;
import com.idthk.usercenter.R;
import com.idthk.usercenter.bean.MessageEvent;
import com.idthk.usercenter.bean.response.LoginRspBean;
import com.idthk.usercenter.bean.response.SendSmsRspBean;
import com.idthk.usercenter.bll.UserBll;
import com.idthk.usercenter.db.DbManager;
import com.idthk.usercenter.utils.CheckUtils;
import com.idthk.usercenter.utils.Constant;
import com.idthk.usercenter.utils.SharedPreferenceUtils;
import com.idthk.usercenter.utils.StringUtils;
import com.idthk.usercenter.utils.tools.UtilTools;

import org.angmarch.views.NiceSpinner;
import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.functions.Action2;

/**
 * Created by pengyu.
 * 2018/3/15
 */

public class NewRegisterActivity extends BaseActivity implements View.OnClickListener {

    private ViewHolder viewHolder;

    private UserBll bll = new UserBll();

    private String type="tel";
    private String serial_number="111";
    private String device_number="222";

    private String sendVericode="";
    private int selectedIndex;
    private DbManager dbManager;
    @Override
    protected int getLayoutId() {
        return R.layout.dialog_register_layout;
    }

    @Override
    protected void initView() {
        viewHolder=new ViewHolder(this);

        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = UtilTools.getScreenWidth(this) - 300;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        dbManager=new DbManager(NewRegisterActivity.this);
        viewHolder.bt_send.setOnClickListener(this);
        viewHolder.bt_register.setOnClickListener(this);
        viewHolder.iv_back.setOnClickListener(this);
        selectedIndex = viewHolder.nice_spinner.getSelectedIndex()+1;
        viewHolder.nice_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedIndex=position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void initData() {
        Resources res = this.getResources();
        String[] ages = res.getStringArray(R.array.age);
        List<String> dataset = new LinkedList<>(Arrays.asList(ages));
        viewHolder.nice_spinner.attachDataSource(dataset);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_send://发送验证码
                sendSms();
                break;
            case R.id.bt_register://注册
                register();
                break;
            case R.id.iv_back://返回按钮
                finish();
                break;
        }
    }

    /**
     * 注册
     */
    private void register() {

        final String phone_email = viewHolder.et_phone_email.getText().toString().trim();
        if (TextUtils.isEmpty(phone_email)) {
            Toast.makeText(this, R.string.register_username_desc,Toast.LENGTH_SHORT).show();
            return;
        }
        String vericode = viewHolder.et_confirm.getText().toString().trim();
        if(TextUtils.isEmpty(vericode)){
            Toast.makeText(this, R.string.register_vericode_notnull_desc,Toast.LENGTH_SHORT).show();
            return;
        }
        //验证验证码是否正确
        if(!vericode.equals(sendVericode)){
            Toast.makeText(this, R.string.register_vericode_desc,Toast.LENGTH_SHORT).show();
            return;
        }
        final String password=viewHolder.et_password.getText().toString().trim();
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, R.string.register_password_desc,Toast.LENGTH_SHORT).show();
            return;
        }
        String age=viewHolder.nice_spinner.getText().toString().trim();
        if(TextUtils.isEmpty(age)){
            Toast.makeText(this, R.string.register_age_desc,Toast.LENGTH_SHORT).show();
            return;
        }

        if (CheckUtils.isPhone(phone_email)) {
            type = "tel";
        }
        if (CheckUtils.isEmail(phone_email)) {
            type = "email";
        }

        String currentTime=System.currentTimeMillis()+"";

        bll.register(this, phone_email, type, password, currentTime,selectedIndex, new Action1() {
            @Override
            public void call(Object o) {
                //注册成功,调登录接口
                bll.login(NewRegisterActivity.this, phone_email, type, password, serial_number, device_number, new Action1<LoginRspBean>() {
                    @Override
                    public void call(LoginRspBean loginRspBean) {
                        //登录成功
                        int userId = loginRspBean.getUser_id();
                        String username = loginRspBean.getUsername();
                        String email = loginRspBean.getEmail();
                        String phone = loginRspBean.getTel();
                        int ageGroup = loginRspBean.getAge_group();
                        String sex = loginRspBean.getSex();
                        String level = loginRspBean.getLevel();
                        int devideId = loginRspBean.getDevice_id();

                        //登录成功 保存登录状态
                        SharedPreferenceUtils.setBoolean(getContext(), Constant.isLogin, true);
                        //保存用户名类型
                        SharedPreferenceUtils.setString(getContext(), Constant.userNameType, type);
                        saveLoginInfo(userId, username, email, phone, ageGroup, sex, level, devideId);
                        //写入数据库
                        dbManager.update(0, "mmm", ""+devideId, 1,level);
                        Intent intent=new Intent(NewRegisterActivity.this,NextStep1Activity.class);
                        startActivity(intent);
                        NewRegisterActivity.this.finish();
                    }
                }, new Action2<Integer, String>() {
                    @Override
                    public void call(Integer integer, String s) {

                    }
                });
            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {
                if(integer==400){
                    Toast.makeText(NewRegisterActivity.this,getString(R.string.alreadly_register_desc),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(NewRegisterActivity.this,s,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void saveLoginInfo(int userId, String username, String email, String phone, int ageGroup, String sex, String level, int devideId) {
        SharedPreferenceUtils.setInt(NewRegisterActivity.this, Constant.userId, userId);
        SharedPreferenceUtils.setString(NewRegisterActivity.this, Constant.username, username);
        SharedPreferenceUtils.setString(NewRegisterActivity.this, Constant.email, email);
        SharedPreferenceUtils.setString(NewRegisterActivity.this, Constant.phone, phone);
        SharedPreferenceUtils.setInt(NewRegisterActivity.this, Constant.ageGroup, ageGroup);
        SharedPreferenceUtils.setString(NewRegisterActivity.this, Constant.sex, sex);
        SharedPreferenceUtils.setString(NewRegisterActivity.this, Constant.level, level);
        SharedPreferenceUtils.setInt(NewRegisterActivity.this, Constant.deviceId, devideId);
    }

    /**
     * 发送验证码
     */
    private void sendSms() {
        final String phone_email = viewHolder.et_phone_email.getText().toString().trim();
        if (TextUtils.isEmpty(phone_email)) {
            Toast.makeText(this, R.string.register_username_desc,Toast.LENGTH_SHORT).show();
            return;
        }
        send(phone_email);
        //发验证码前先验证用户名是否是注册用户 不是注册用户则不发短信或邮箱
//        new UserBll().validateBeforeResetPassword(NewRegisterActivity.this, type, phone_email, new Action1<Object>() {
//            @Override
//            public void call(Object o) {
//                send(phone_email);
//            }
//        }, new Action2<Integer, String>() {
//            @Override
//            public void call(Integer integer, String s) {
//                if (integer == 400) {
//                    Toast.makeText(NewRegisterActivity.this, R.string.not_register_user, Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(NewRegisterActivity.this,s,Toast.LENGTH_SHORT).show();
//                }
//            }
//        });



    }

    private void send(String phone_email){
        //判断用户名是手机号还是邮箱 手机号发短信 邮箱发邮件
        if(CheckUtils.isEmail(phone_email)){
            //发邮件
            bll.sendEmail(NewRegisterActivity.this, phone_email, new Action1<SendSmsRspBean>() {
                @Override
                public void call(SendSmsRspBean sendSmsRspBean) {
                    sendVericode =sendSmsRspBean.getRandNum();
                    //验证码发送成功后
                    /** 倒计时180秒，一次1秒 */
                    CountDownTimer timer = new CountDownTimer(60 * 1000 * 3, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // TODO Auto-generated method stub
                            viewHolder.bt_send.setText(millisUntilFinished / 1000 + getResources().getString(R.string.sencond_desc));
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
                    Toast.makeText(NewRegisterActivity.this,s,Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            //发短信
            bll.sendSms(NewRegisterActivity.this, phone_email, new Action1<SendSmsRspBean>() {
                @Override
                public void call(SendSmsRspBean sendSmsRspBean) {
                    LogX.e(sendSmsRspBean);
                    sendVericode =sendSmsRspBean.getRandNum();
                    //验证码发送成功后
                    /** 倒计时180秒，一次1秒 */
                    CountDownTimer timer = new CountDownTimer(60 * 1000 * 3, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // TODO Auto-generated method stub
                            viewHolder.bt_send.setText(millisUntilFinished / 1000 + getResources().getString(R.string.sencond_desc));
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
                    LogX.e(s);
                }
            });
        }

    }

    static class ViewHolder {

        @BindView(R.id.nice_spinner)
        NiceSpinner nice_spinner;
        @BindView(R.id.et_phone_email)
        EditText et_phone_email;
        @BindView(R.id.et_confirm)
        EditText et_confirm;
        @BindView(R.id.et_password)
        EditText et_password;
        @BindView(R.id.bt_send)
        Button bt_send;
        @BindView(R.id.bt_register)
        Button bt_register;
        @BindView(R.id.iv_back)
        ImageView iv_back;

        ViewHolder(Activity view) {
            ButterKnife.bind(this, view);
        }
    }
}
