package com.idthk.usercenter.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.idthk.usercenter.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by williamyin on 2017/12/26.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private ViewHolder viewHolder;
    @Override
    protected int getLayoutId() {
        return R.layout.login;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newuser_regis:
                //跳转到注册界面
                Intent i = new Intent(LoginActivity.this, UserRegisActivity.class);
                i.putExtra("isOpen_device",false);
                startActivity(i);
                finish();
                break;
            case R.id.forget_password:
                Intent intent = new Intent(LoginActivity.this, ForgetPassWordActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    protected void initView() {

        viewHolder=new ViewHolder(this);
    }

    @Override
    protected void initData() {
        viewHolder.newuserRegis.setOnClickListener(this);
        viewHolder.forgetPassword.setOnClickListener(this);
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

        ViewHolder(Activity view) {
            ButterKnife.bind(this, view);
        }
    }
}
