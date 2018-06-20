package com.idthk.usercenter.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.idthk.usercenter.R;
import com.idthk.usercenter.bean.MessageEvent;
import com.idthk.usercenter.db.DbManager;
import com.idthk.usercenter.utils.Constant;
import com.idthk.usercenter.utils.ModifyUserType;
import com.idthk.usercenter.utils.SharedPreferenceUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pengyu.
 * 2018/3/29
 */
public class AppOutActivity extends BaseActivity {
    private DbManager dbManager;
    private String key;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_exit;
    }

    @Override
    protected void initView() {
        ViewHolder viewHolder = new ViewHolder(this);
        dbManager = new DbManager(getContext());

        key = getIntent().getStringExtra("key");

        if (key.equals("exit")) {
            viewHolder.tv_message.setText(R.string.sure_exit_system);
        } else if (key.equals("exitlogin")) {
            viewHolder.tv_message.setText(getString(R.string.sure_exit_app_desc));
        }


        viewHolder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        viewHolder.sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (key.equals("exit")) {
//                    Intent i=new Intent();
//                    setResult(RESULT_OK,i);
//                    finish();
                    System.exit(0);
                } else if (key.equals("exitlogin")) {
                    //退出登录
                    exitLogin();
                    finish();
                }
            }
        });
    }

    /**
     * 退出登录
     */
    private void exitLogin() {
        //保存退出登录的状态
        SharedPreferenceUtils.setBoolean(getContext(), Constant.isLogin, false);
        //清空sp
        SharedPreferenceUtils.getSp(getContext()).edit().clear().apply();
        //写入数据库
        dbManager.update(0, "", "", 0,"");
        //通知主界面 用户已退出登录
        EventBus.getDefault().post(new MessageEvent(false, ModifyUserType.MODIFY_EXIT_LOGIN));
    }

    @Override
    protected void initData() {

    }

    static class ViewHolder {
        @BindView(R.id.cancel)
        TextView cancel;
        @BindView(R.id.sure)
        TextView sure;
        @BindView(R.id.tv_message)
        TextView tv_message;

        ViewHolder(Activity view) {
            ButterKnife.bind(this, view);
        }
    }
}
