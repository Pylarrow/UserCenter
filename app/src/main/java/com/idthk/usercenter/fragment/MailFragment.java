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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.idthk.usercenter.R;
import com.idthk.usercenter.bean.MessageEvent;
import com.idthk.usercenter.bean.response.UserInfoRsp;
import com.idthk.usercenter.bll.UserBll;
import com.idthk.usercenter.utils.CheckUtils;
import com.idthk.usercenter.utils.Constant;
import com.idthk.usercenter.utils.ModifyUserType;
import com.idthk.usercenter.utils.SharedPreferenceUtils;
import com.idthk.usercenter.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.functions.Action2;


/**
 *  MailFragment
 */

public  class MailFragment extends BaseFragment implements View.OnClickListener {
    private ViewHolder viewHolder;
    private String type="";
    int userId = SharedPreferenceUtils.getInt(getContext(), Constant.userId, -1);
    public static MailFragment newInstance(String s) {
        MailFragment mailFragment = new MailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("mail", s);
        mailFragment.setArguments(bundle);
        return mailFragment;
    }
    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.mail;
    }

    @Override
    protected void initView(View view) {
        viewHolder=new ViewHolder(view);

        viewHolder.iv_edit.setOnClickListener(this);
        viewHolder.bt_modify.setOnClickListener(this);

        viewHolder.et_new_mail.setOnClickListener(this);
        viewHolder.bt_save.setOnClickListener(this);

    }
    @Override
    protected void initData() {
        String mail = (String) getArguments().get("mail");
        viewHolder.et_email.setText(mail);

        //获取用户名类型
        String userNameType = SharedPreferenceUtils.getString(getContext(), Constant.userNameType, "");
        if(userNameType.equals("email")){
            viewHolder.bt_modify.setEnabled(false);
            viewHolder.bt_modify.setBackgroundColor(getResources().getColor(R.color.gray));
        }else{
            viewHolder.bt_modify.setEnabled(true);
            viewHolder.bt_modify.setBackgroundColor(getResources().getColor(R.color.top_color));
        }

        //展示邮箱信息
        String email = SharedPreferenceUtils.getString(getContext(), Constant.email, "");
        viewHolder.et_new_mail.setText(email);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_edit:
                //让Edittext为可编辑状态
                clickEdit();
                break;
            case R.id.bt_modify:
                //修改邮箱
                modifyEmail();
                break;
            case R.id.et_new_mail:
                viewHolder.et_new_mail.setEnabled(true);
                viewHolder.et_new_mail.setFocusable(true);
                viewHolder.et_new_mail.requestFocus();

                clickEdit();
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                break;
            case R.id.bt_save:
                save();
                break;

        }
    }

    private void save() {
        final String email = viewHolder.et_new_mail.getText().toString().trim();
        //有效性验证
        if(!CheckUtils.isEmail(email)){
            Toast.makeText(getContext(), R.string.input_right_email,Toast.LENGTH_SHORT).show();
            return;
        }
        new UserBll().updateUserInfo(getContext(), userId, "email", email, new Action1<Object>() {
            @Override
            public void call(Object o) {
                Toast.makeText(getContext(), R.string.bind_email_sucess,Toast.LENGTH_SHORT).show();
                //postToMain(ModifyUserType.MODIFY_EMAIL);
                SharedPreferenceUtils.setString(getContext(),Constant.email,email);
                EventBus.getDefault().post(new MessageEvent(email,ModifyUserType.MODIFY_EMAIL));
                setFullScreen();

            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {

            }
        });
    }

    /**
     * 修改邮箱
     */
    private void modifyEmail() {
        final String email = viewHolder.et_email.getText().toString().trim();
        //有效性验证
        if(!CheckUtils.isEmail(email)){
            Toast.makeText(getContext(), R.string.input_right_email,Toast.LENGTH_SHORT).show();
            return;
        }
        new UserBll().updateUserInfo(getContext(), userId, "email", email, new Action1<Object>() {
            @Override
            public void call(Object o) {
                Toast.makeText(getContext(), R.string.bind_email_sucess,Toast.LENGTH_SHORT).show();
                //postToMain(ModifyUserType.MODIFY_EMAIL);
                setFullScreen();
                SharedPreferenceUtils.setString(getContext(),Constant.email,email);
                EventBus.getDefault().post(new MessageEvent(email,ModifyUserType.MODIFY_EMAIL));

            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {

            }
        });
    }

    private void postToMain(final int type) {
        new UserBll().getUserInfo(getContext(), userId, new Action1<UserInfoRsp>() {
            @Override
            public void call(UserInfoRsp userInfoRsp) {
                //1 登录 2 修改用户名 3 修改性别 4 修改年龄 5 修改手机号 6 修改邮箱 7 修改级别
                if(type== ModifyUserType.MODIFY_USERNAME){
                    EventBus.getDefault().post(new MessageEvent(userInfoRsp.getUsername(),type));
                }else if(type==ModifyUserType.MODIFY_SEX){
                    EventBus.getDefault().post(new MessageEvent(userInfoRsp.getSex(),type));
                }else if(type==ModifyUserType.MODIFY_AGE){
                    String[] ages = getContext().getResources().getStringArray(R.array.age);
                    int ageGroup = userInfoRsp.getAge_group();
                    if(ageGroup<=6)
                        EventBus.getDefault().post(new MessageEvent(ages[ageGroup-1],type));
                }else if(type==ModifyUserType.MODIFY_PHONE){
                    EventBus.getDefault().post(new MessageEvent(userInfoRsp.getTel(),type));
                }else if(type==ModifyUserType.MODIFY_EMAIL){
                    EventBus.getDefault().post(new MessageEvent(userInfoRsp.getEmail(),type));
                }else if(type==ModifyUserType.MODIFY_LEVEL){
                    EventBus.getDefault().post(new MessageEvent(userInfoRsp.getLevel(),type));
                }
            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {

            }
        });
    }


    private void clickEdit() {
        viewHolder.et_new_mail.setEnabled(true);
        viewHolder.et_new_mail.setFocusable(true);
        viewHolder.et_new_mail.setFocusableInTouchMode(true);

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            viewHolder.et_new_mail.requestFocus();
            imm.showSoftInput(viewHolder.et_new_mail, 0);
        }
    }

    static class ViewHolder{

        @BindView(R.id.et_email)
        EditText et_email;
        @BindView(R.id.iv_edit)
        ImageView iv_edit;
        @BindView(R.id.bt_modify)
        Button bt_modify;
        @BindView(R.id.rl_mail)
        RelativeLayout rl_mail;
        @BindView(R.id.et_new_mail)
        EditText et_new_mail;
        @BindView(R.id.bt_save)
        Button bt_save;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
