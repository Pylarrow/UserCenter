package com.idthk.usercenter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.idthk.usercenter.APP;
import com.idthk.usercenter.R;
import com.idthk.usercenter.bean.MessageEvent;
import com.idthk.usercenter.bean.response.UserInfoRsp;
import com.idthk.usercenter.bll.UserBll;
import com.idthk.usercenter.utils.CheckUtils;
import com.idthk.usercenter.utils.Constant;
import com.idthk.usercenter.utils.ModifyUserType;
import com.idthk.usercenter.utils.SharedPreferenceUtils;
import com.idthk.usercenter.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.functions.Action2;

/**
 * phoneFragment
 */

public class PhoneFragment extends BaseFragment implements View.OnClickListener {

    private ViewHolder viewHolder;
    int userId = SharedPreferenceUtils.getInt(getContext(), Constant.userId, -1);
    private String phone;

    public static PhoneFragment newInstance(String s) {
        PhoneFragment phoneFragment = new PhoneFragment();
        Bundle bundle = new Bundle();
        bundle.putString("phone", s);
        phoneFragment.setArguments(bundle);
        return phoneFragment;
    }
    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.phone;
    }

    @Override
    protected void initView(View view) {
        viewHolder=new ViewHolder(view);

        viewHolder.bt_modify.setOnClickListener(this);
        viewHolder.iv_edit.setOnClickListener(this);
        viewHolder.et_phone.setOnClickListener(this);
        viewHolder.bt_save.setOnClickListener(this);
    }
    @Override
    protected void initData() {
        String phone = (String) getArguments().get("phone");
        viewHolder.et_user_name.setText(phone);

        String newPhone = SharedPreferenceUtils.getString(getContext(), Constant.phone, "");
        viewHolder.et_phone.setText(newPhone);

        //获取用户名类型
        String userNameType = SharedPreferenceUtils.getString(getContext(), Constant.userNameType, "");
        if(userNameType.equals("tel")){
            viewHolder.bt_modify.setEnabled(false);
            //viewHolder.bt_modify.setBackgroundColor(getResources().getColor(R.color.gray));
            viewHolder.bt_modify.setVisibility(View.GONE);
            viewHolder.iv_edit.setVisibility(View.GONE);
        }else{
            viewHolder.bt_modify.setEnabled(true);
            viewHolder.bt_modify.setBackgroundColor(getResources().getColor(R.color.top_color));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_modify:
                modifyPhone();
                break;
            case R.id.iv_edit:
                //clickEdit();
                break;
            case R.id.et_phone:
                clickEdit();
                break;
            case R.id.bt_save:
                save();
                break;
        }
    }

    private void save() {
        final String phone = viewHolder.et_phone.getText().toString().trim();
        //修改手机号
        //Toast.makeText(getContext(),"modify phone",Toast.LENGTH_SHORT).show();
        //手机号非空验证
        if(TextUtils.isEmpty(phone)){
            Toast.makeText(getContext(), R.string.phone_not_null,Toast.LENGTH_SHORT).show();
            return;
        }
        //手机有效性验证
        if(!CheckUtils.isPhone(phone)){
            Toast.makeText(getContext(), R.string.input_right_phone,Toast.LENGTH_SHORT).show();
            return;
        }
        new UserBll().updateUserInfo(getContext(), userId, "tel", phone, new Action1<Object>() {
            @Override
            public void call(Object o) {
                ToastUtils.showMessage(APP.getContext(),getString(R.string.save_phone_success));
                SharedPreferenceUtils.setString(getContext(),Constant.phone,phone);
                EventBus.getDefault().post(new MessageEvent(phone,ModifyUserType.MODIFY_PHONE));
                setFullScreen();
            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {

            }
        });
    }


    /**
     * 修改手机号
     */
    private void modifyPhone() {
        final String phone = viewHolder.et_user_name.getText().toString().trim();
        //修改手机号
        //Toast.makeText(getContext(),"modify phone",Toast.LENGTH_SHORT).show();
        //手机号非空验证
        if(TextUtils.isEmpty(phone)){
            Toast.makeText(getContext(), R.string.phone_not_null,Toast.LENGTH_SHORT).show();
            return;
        }
        //手机有效性验证
        if(!CheckUtils.isPhone(phone)){
            Toast.makeText(getContext(), R.string.input_right_phone,Toast.LENGTH_SHORT).show();
            return;
        }
        new UserBll().updateUserInfo(getContext(), userId, "tel", phone, new Action1<Object>() {
            @Override
            public void call(Object o) {
                //postToMain(ModifyUserType.MODIFY_PHONE);
                SharedPreferenceUtils.setString(getContext(),Constant.phone,phone);
                EventBus.getDefault().post(new MessageEvent(phone,ModifyUserType.MODIFY_PHONE));
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
        viewHolder.et_phone.setEnabled(true);
        viewHolder.et_phone.setFocusable(true);
        viewHolder.et_phone.setFocusableInTouchMode(true);

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            viewHolder.et_phone.requestFocus();
            imm.showSoftInput(viewHolder.et_phone, 0);
        }
    }

    static class ViewHolder{

        @BindView(R.id.et_user_name)
        EditText et_user_name;
        @BindView(R.id.iv_edit)
        ImageView iv_edit;
        @BindView(R.id.bt_modify)
        Button bt_modify;
        @BindView(R.id.et_phone)
        EditText et_phone;
        @BindView(R.id.bt_save)
        Button bt_save;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
