package com.idthk.usercenter.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.idthk.usercenter.R;
import com.idthk.usercenter.bll.UserBll;
import com.idthk.usercenter.utils.CheckUtils;
import com.idthk.usercenter.utils.tools.UtilTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.functions.Action2;

/**
 * 家长验证界面
 * Created by pengyu.
 * 2018/3/7
 */

public class ParentalVericatActivity extends BaseActivity implements View.OnClickListener {
    private ViewHolder viewHolder;
    private String username;
    private String password;

    private String type="tel";
    private String payType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_parent_vericode_layout;
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

        viewHolder.bt_parent_vericode.setOnClickListener(this);
        viewHolder.iv_close.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        //获取支付类型
        payType = getIntent().getStringExtra("payType");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_parent_vericode:
                parentVericate();
                break;
            case R.id.iv_close:
                finish();
                break;
        }
    }

    /**
     * 家长验证
     */
    private void parentVericate() {
        username = viewHolder.et_username.getText().toString().trim();
        password = viewHolder.et_password.getText().toString().trim();
        //非空验证
        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,getString(R.string.phone_email_notnull_desc),Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,getString(R.string.register_password_desc),Toast.LENGTH_SHORT).show();
            return;
        }
        //有效性验证
        if(!CheckUtils.isPhone(username)&&!CheckUtils.isEmail(password)){
            Toast.makeText(this, R.string.please_input_right_phone,Toast.LENGTH_SHORT).show();
            return;
        }
        if(!CheckUtils.isPwd2(password)){
            Toast.makeText(this, R.string.password_vericate_tips,Toast.LENGTH_SHORT).show();
            return;
        }

        if(CheckUtils.isPhone(username)){
            type="tel";
        }

        if(CheckUtils.isEmail(username)){
            type="email";
        }

        //家长验证 调用验证接口 验证通过 跳转到续费界面
        new UserBll().validate(this, type, username, password, new Action1<Object>() {
            @Override
            public void call(Object o) {
//                Intent intent = new Intent(getContext(), PayChoiceActivity.class);
//                startActivity(intent);
//                finish();
                //验证成功 跳转到支付界面
                Intent intent=new Intent(ParentalVericatActivity.this,PayViewActivity.class);
                intent.putExtra("payType",payType);
                startActivity(intent);
                finish();

            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {
                Toast.makeText(ParentalVericatActivity.this,s,Toast.LENGTH_SHORT).show();
            }
        });

    }

    static class ViewHolder {

        @BindView(R.id.bt_parent_vericode)
        Button bt_parent_vericode;
        @BindView(R.id.iv_close)
        ImageView iv_close;
        @BindView(R.id.et_username)
        EditText et_username;
        @BindView(R.id.et_password)
        EditText et_password;

        ViewHolder(Activity view) {
            ButterKnife.bind(this, view);
        }
    }
}
