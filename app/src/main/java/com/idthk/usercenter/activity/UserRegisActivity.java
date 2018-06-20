package com.idthk.usercenter.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.idthk.usercenter.R;
import com.idthk.usercenter.utils.SharedPreferenceUtils;
import com.idthk.usercenter.view.CustomCheckImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by williamyin on 2017/12/25.
 */

public class UserRegisActivity extends BaseActivity implements View.OnClickListener {
    private ViewHolder viewHolder;

    private boolean isOpenDevice = false;

    @Override
    protected int getLayoutId() {
        return R.layout.regis;
    }

    @Override
    protected void initView() {
        viewHolder = new ViewHolder(this);
        viewHolder.btnRegis.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_regis:

                SharedPreferenceUtils.setBoolean(this,"first_opendevice", true);
                finish();

                break;
        }
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            isOpenDevice = intent.getBooleanExtra("isOpen_device", false);
            Log.i("initDadta: ", isOpenDevice + "");
        }
    }

    static class ViewHolder {
        @BindView(R.id.user_image)
        ImageView userImage;
        @BindView(R.id.content_mode)
        EditText contentMode;
        @BindView(R.id.code_get)
        TextView codeGet;
        @BindView(R.id.jihuo_code)
        LinearLayout jihuoCode;
        @BindView(R.id.password_root)
        ImageView passwordRoot;
        @BindView(R.id.kan_pass)
        ImageView kanPass;
        @BindView(R.id.btn_regis)
        Button btnRegis;
        @BindView(R.id.regis_root)
        LinearLayout regisRoot;
        @BindView(R.id.remember_server)
        CustomCheckImageView rememberServer;

        ViewHolder(Activity view) {
            ButterKnife.bind(this, view);
        }
    }
}
