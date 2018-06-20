package com.idthk.usercenter.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.idthk.usercenter.R;
import com.idthk.usercenter.bean.MessageEvent;
import com.idthk.usercenter.bean.response.LoginRspBean;
import com.idthk.usercenter.bll.UserBll;
import com.idthk.usercenter.db.DbManager;
import com.idthk.usercenter.utils.CheckUtils;
import com.idthk.usercenter.utils.Constant;
import com.idthk.usercenter.utils.SharedPreferenceUtils;
import com.idthk.usercenter.utils.StringUtils;
import com.idthk.usercenter.utils.tools.UtilTools;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.functions.Action2;

/**
 * Created by pengyu.
 * 2018/3/1
 */

    public class NewLoginActivity extends BaseActivity implements View.OnClickListener {
        private ViewHolder viewHolder;

    private UserBll bll = new UserBll();

    private String username = "";//用户名
    private String type = "tel";//类型 tel or email
    private String password = "";//密码
    private String serial_number = "abcd";//设备序列号
    private String device_number = "dcba";//设备号

    private boolean isPwdType = true;
    private DbManager dbManager;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_new_login;
    }

    @Override
    protected void initView() {
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = UtilTools.getScreenWidth(getContext()) - 300;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        dbManager = new DbManager(NewLoginActivity.this);

        viewHolder = new ViewHolder(this);
        viewHolder.bt_register.setOnClickListener(this);
        viewHolder.tv_forget_password.setOnClickListener(this);
        viewHolder.bt_login.setOnClickListener(this);
        viewHolder.iv_eye.setOnClickListener(this);
        viewHolder.iv_back.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        String str = getResources().getString(R.string.forget_password_desc);
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new UnderlineSpan(), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.top_color));
        spannableString.setSpan(colorSpan, 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        viewHolder.tv_forget_password.setText(spannableString);


    }
    boolean isflag=false;
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.tv_forget_password:
                //跳转到忘记密码界面
                //new ForgetPassWordDialog(this);
                intent = new Intent(NewLoginActivity.this, NewForgetPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_register:
                //注册
                //new RegisterDialog(this);
                intent = new Intent(this, NewRegisterActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.bt_login:
//                dbManager.update(0, "abc", "18925286395", 1);
                login();
                break;
            case R.id.iv_eye:
                //密码明文密文切换
                switchPwdType();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    /**
     * 切换密码类型
     */
    private void switchPwdType() {
        isPwdType = !isPwdType;
        if (isPwdType) {
            viewHolder.et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            viewHolder.iv_eye.setImageDrawable(getResources().getDrawable(R.drawable.eye_close));
        } else {
            viewHolder.et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            viewHolder.iv_eye.setImageDrawable(getResources().getDrawable(R.drawable.eye));
        }
    }

    /**
     * 登录
     */
    private void login() {
        username = viewHolder.et_username.getText().toString().trim();
        password = viewHolder.et_password.getText().toString().trim();
        //非空验证
        if (StringUtils.isEmpty(username)) {
            Toast.makeText(this, getResources().getString(R.string.phone_or_email_tips), Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(password)) {
            Toast.makeText(this, getResources().getString(R.string.register_password_desc), Toast.LENGTH_SHORT).show();
            return;
        }

        //判断用户名 是手机号还是邮箱
        if (CheckUtils.isPhone(username)) {
            type = "tel";
        }
        if (CheckUtils.isEmail(username)) {
            type = "email";
        }

        bll.login(this, username, type, password, serial_number, device_number, new Action1<LoginRspBean>() {
            @Override
            public void call(LoginRspBean loginRspBean) {
                //登录成功 将用户信息保存到本地,跳转到首页
                Toast.makeText(NewLoginActivity.this, R.string.login_sucess, Toast.LENGTH_SHORT).show();
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

                //通知界面更新界面
                EventBus.getDefault().post(new MessageEvent(userId, username, email, phone, ageGroup, sex, level, devideId, 1));
                finish();
            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {
                if (integer == 400) {
                    Toast.makeText(NewLoginActivity.this, R.string.username_error, Toast.LENGTH_SHORT).show();
                } else if (integer == 401) {
                    Toast.makeText(NewLoginActivity.this, R.string.password_error, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(NewLoginActivity.this,s,Toast.LENGTH_SHORT).show();
                }
                //清空用户名密码
                clearUser();
            }
        });
    }

    /**
     * 清空用户名和密码
     */
    private void clearUser() {
        viewHolder.et_username.setText("");
        viewHolder.et_password.setText("");
    }

    /**
     * 保存用户信息
     *
     * @param userId
     * @param username
     * @param email
     * @param phone
     * @param ageGroup
     * @param sex
     * @param level
     * @param devideId
     */
    private void saveLoginInfo(int userId, String username, String email, String phone, int ageGroup, String sex, String level, int devideId) {
        SharedPreferenceUtils.setInt(NewLoginActivity.this, Constant.userId, userId);
        SharedPreferenceUtils.setString(NewLoginActivity.this, Constant.username, username);
        SharedPreferenceUtils.setString(NewLoginActivity.this, Constant.email, email);
        SharedPreferenceUtils.setString(NewLoginActivity.this, Constant.phone, phone);
        SharedPreferenceUtils.setInt(NewLoginActivity.this, Constant.ageGroup, ageGroup);
        SharedPreferenceUtils.setString(NewLoginActivity.this, Constant.sex, sex);
        SharedPreferenceUtils.setString(NewLoginActivity.this, Constant.level, level);
        SharedPreferenceUtils.setInt(NewLoginActivity.this, Constant.deviceId, devideId);
    }

    static class ViewHolder {

        @BindView(R.id.tv_forget_password)
        TextView tv_forget_password;
        @BindView(R.id.bt_register)
        Button bt_register;
        @BindView(R.id.bt_login)
        Button bt_login;
        @BindView(R.id.et_username)
        EditText et_username;
        @BindView(R.id.et_password)
        EditText et_password;
        @BindView(R.id.iv_eye)
        ImageView iv_eye;
        @BindView(R.id.iv_back)
        ImageView iv_back;

        ViewHolder(Activity view) {
            ButterKnife.bind(this, view);
        }
    }
}
