package com.idthk.usercenter.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.idthk.usercenter.R;
import com.idthk.usercenter.bean.MessageEvent;
import com.idthk.usercenter.bll.UserBll;
import com.idthk.usercenter.utils.Constant;
import com.idthk.usercenter.utils.ModifyUserType;
import com.idthk.usercenter.utils.SharedPreferenceUtils;
import com.idthk.usercenter.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.functions.Action2;

/**
 * Created by pengyu.
 * 2018/6/11
 */
public class NextStep1Activity extends BaseActivity implements View.OnClickListener {

    ViewHolder viewHolder;
    int userId = SharedPreferenceUtils.getInt(this, Constant.userId, -1);
    @Override
    protected int getLayoutId() {
        return R.layout.activity_next_step1_layout;
    }

    @Override
    protected void initView() {
        viewHolder=new ViewHolder(this);

        viewHolder.tv_next_step.setOnClickListener(this);
        viewHolder.et_user_name.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_next_step:
                //下一步
                next();
                break;
            case R.id.et_user_name:
                clickEdit();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                break;
        }
    }

    private void next() {
        final String nickname=viewHolder.et_user_name.getText().toString().trim();
        if(TextUtils.isEmpty(nickname)){
            ToastUtils.showMessage(getContext(),getString(R.string.nickname_notnull_desc));
            return;
        }
        new UserBll().updateUserInfo(NextStep1Activity.this, userId, "username", nickname, new Action1<Object>() {
            @Override
            public void call(Object o) {
                //ToastUtils.showMessage(getContext(),getString(R.string.save_success));
                SharedPreferenceUtils.setString(getContext(), Constant.username,nickname);
                //EventBus.getDefault().post(new MessageEvent(nickname, ModifyUserType.MODIFY_USERNAME));
                Intent intent=new Intent(NextStep1Activity.this,NextStep2Activity.class);
                startActivity(intent);
                finish();
            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {

            }
        });
    }

    private void clickEdit() {
        viewHolder.et_user_name.setEnabled(true);
        viewHolder.et_user_name.setFocusable(true);
        viewHolder.et_user_name.setFocusableInTouchMode(true);

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            viewHolder.et_user_name.requestFocus();
            imm.showSoftInput(viewHolder.et_user_name, 0);
        }

    }

    static class ViewHolder{
        @BindView(R.id.tv_next_step)
        TextView tv_next_step;
        @BindView(R.id.et_user_name)
        EditText et_user_name;
        public ViewHolder(Activity view){
            ButterKnife.bind(this,view);
        }
    }
}
