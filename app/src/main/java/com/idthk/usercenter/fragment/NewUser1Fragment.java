package com.idthk.usercenter.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

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

import static com.idthk.usercenter.utils.Constant.phone;

/**
 * Created by pengyu.
 * 2018/6/4
 */
public class NewUser1Fragment extends BaseFragment implements View.OnClickListener {

    private ViewHolder viewHolder;
    int userId = SharedPreferenceUtils.getInt(getContext(), Constant.userId, -1);

    public static NewUser1Fragment newInstance(String s) {
        NewUser1Fragment userCenterFragment = new NewUser1Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("user", s);
        userCenterFragment.setArguments(bundle);
        return userCenterFragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_user1;
    }

    @Override
    protected void initView(View view) {
        viewHolder = new ViewHolder(view);

        viewHolder.et_user_name.setOnClickListener(this);
        viewHolder.bt_save.setOnClickListener(this);

        String username = SharedPreferenceUtils.getString(getContext(), Constant.username, "");
        viewHolder.et_user_name.setText(username);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_save:
                save();
                break;
            case R.id.et_user_name:
                viewHolder.et_user_name.setEnabled(true);
                viewHolder.et_user_name.setFocusable(true);
                viewHolder.et_user_name.requestFocus();

                clickEdit();
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                break;
        }
    }

    private void save() {
        final String nickname=viewHolder.et_user_name.getText().toString().trim();
        if(TextUtils.isEmpty(nickname)){
            ToastUtils.showMessage(getContext(),getString(R.string.nickname_notnull_desc));
            return;
        }
        new UserBll().updateUserInfo(getContext(), userId, "username", nickname, new Action1<Object>() {
            @Override
            public void call(Object o) {
                ToastUtils.showMessage(getContext(),getString(R.string.save_success));
                SharedPreferenceUtils.setString(getContext(), Constant.username,nickname);
                EventBus.getDefault().post(new MessageEvent(nickname, ModifyUserType.MODIFY_USERNAME));
                setFullScreen();
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

    static class ViewHolder {

        @BindView(R.id.rl_edit_username)
        RelativeLayout rl_edit_username;
        @BindView(R.id.et_user_name)
        EditText et_user_name;
        @BindView(R.id.bt_save)
        Button bt_save;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
