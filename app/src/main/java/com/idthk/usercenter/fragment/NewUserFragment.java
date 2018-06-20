package com.idthk.usercenter.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.idthk.usercenter.APP;
import com.idthk.usercenter.R;
import com.idthk.usercenter.activity.AppOutActivity;
import com.idthk.usercenter.activity.NewLoginActivity;
import com.idthk.usercenter.activity.NewUserCenterActivity;
import com.idthk.usercenter.bean.MessageEvent;
import com.idthk.usercenter.bean.response.UserInfoRsp;
import com.idthk.usercenter.bll.UserBll;
import com.idthk.usercenter.db.DbManager;
//import com.idthk.usercenter.permission.PermissionsChecker;
import com.idthk.usercenter.permission.acp.Acp;
import com.idthk.usercenter.permission.acp.AcpListener;
import com.idthk.usercenter.permission.acp.AcpOptions;
import com.idthk.usercenter.utils.Constant;
import com.idthk.usercenter.utils.ModifyUserType;
import com.idthk.usercenter.utils.SharedPreferenceUtils;
import com.idthk.usercenter.utils.tools.UtilTools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.functions.Action2;

/**
 * Created by pengyu.
 * 2018/2/2
 */

public class NewUserFragment extends BaseFragment implements View.OnClickListener, View.OnFocusChangeListener {

    public static NewUserFragment newInstance(String s) {
        NewUserFragment userCenterFragment = new NewUserFragment();
        Bundle bundle = new Bundle();
        bundle.putString("user", s);
        userCenterFragment.setArguments(bundle);
        return userCenterFragment;
    }

    private ViewHolder viewHolder;
//    private PermissionsChecker mPermissionsChecker; // 权限检测器
    static final String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    int userId = SharedPreferenceUtils.getInt(getContext(), Constant.userId, -1);
    private String sex = Constant.male;


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_user;
    }

    @Override
    protected void initView(View view) {
        viewHolder = new NewUserFragment.ViewHolder(view);
        //viewHolder.et_user_name.setFocusable(false);

        String[] ages = getContext().getResources().getStringArray(R.array.age);
        viewHolder.tv_5_9.setText(ages[0]);
        viewHolder.tv_10_14.setText(ages[1]);
        viewHolder.tv_15_20.setText(ages[2]);
        viewHolder.tv_21_30.setText(ages[3]);
        viewHolder.tv_30_50.setText(ages[4]);
        viewHolder.tv_50.setText(ages[5]);

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent message) {
        UserInfoRsp userInfoRsp = message.getUserInfoRsp();
        showData(userInfoRsp);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("aaa", "onResume");
        new UserBll().getUserInfo(getContext(), userId, new Action1<UserInfoRsp>() {

            @Override
            public void call(UserInfoRsp userInfoRsp) {
                showData(userInfoRsp);
            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {

            }
        });
    }

    /**
     * 初始化用户数据
     */
    private void showData(UserInfoRsp userData) {
        if(userData==null)
            return;
        if (userData.getUsername() != null)
            viewHolder.et_user_name.setText(userData.getUsername());
        if (userData.getSex().equals(Constant.male)) {
            viewHolder.cb_man.setChecked(true);
            viewHolder.tv_man.setTextColor(getResources().getColor(R.color.man_selector_sex_color));
            viewHolder.cb_girl.setChecked(false);
            viewHolder.tv_girl.setTextColor(getResources().getColor(R.color.black));
            sex = Constant.male;
        } else if (userData.getSex().equals(Constant.female)) {
            viewHolder.cb_man.setChecked(false);
            viewHolder.tv_man.setTextColor(getResources().getColor(R.color.black));
            viewHolder.cb_girl.setChecked(true);
            viewHolder.tv_girl.setTextColor(getResources().getColor(R.color.man_selector_sex_color));
            sex = Constant.female;
        }
        if (userData.getAge_group() == Constant.age_5_9) {
            clearCheckBoxState();
            viewHolder.cb_5_9.setChecked(true);
        } else if (userData.getAge_group() == Constant.age_10_14) {
            clearCheckBoxState();
            viewHolder.cb_10_14.setChecked(true);
        } else if (userData.getAge_group() == Constant.age_15_20) {
            clearCheckBoxState();
            viewHolder.cb_15_20.setChecked(true);
        } else if (userData.getAge_group() == Constant.age_21_30) {
            clearCheckBoxState();
            viewHolder.cb_21_30.setChecked(true);
        } else if (userData.getAge_group() == Constant.age_30_50) {
            clearCheckBoxState();
            viewHolder.cb_30_50.setChecked(true);
        } else if (userData.getAge_group() == Constant.age_50) {
            clearCheckBoxState();
            viewHolder.cb_50.setChecked(true);
        }
    }


    private void clearCheckBoxState() {
        viewHolder.cb_5_9.setChecked(false);
        viewHolder.cb_10_14.setChecked(false);
        viewHolder.cb_15_20.setChecked(false);
        viewHolder.cb_21_30.setChecked(false);
        viewHolder.cb_30_50.setChecked(false);
        viewHolder.cb_50.setChecked(false);
    }


    @Override
    protected void initData() {
//        mPermissionsChecker = new PermissionsChecker(APP.getContext());

        viewHolder.cb_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.cb_man.setChecked(true);
                viewHolder.tv_man.setTextColor(getResources().getColor(R.color.man_selector_sex_color));
                viewHolder.cb_girl.setChecked(false);
                viewHolder.tv_girl.setTextColor(getResources().getColor(R.color.black));
                sex = Constant.male;
                modifySex(sex);
            }
        });
        viewHolder.cb_girl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.cb_man.setChecked(false);
                viewHolder.tv_man.setTextColor(getResources().getColor(R.color.black));
                viewHolder.cb_girl.setChecked(true);
                viewHolder.tv_girl.setTextColor(getResources().getColor(R.color.man_selector_sex_color));
                sex = Constant.female;
                modifySex(sex);
            }
        });

        viewHolder.ll_5_9.setOnClickListener(this);
        viewHolder.ll_10_14.setOnClickListener(this);
        viewHolder.ll_15_20.setOnClickListener(this);
        viewHolder.ll_21_30.setOnClickListener(this);
        viewHolder.ll_30_50.setOnClickListener(this);
        viewHolder.ll_50.setOnClickListener(this);
        viewHolder.iv_edit.setOnClickListener(this);
        viewHolder.bt_exit.setOnClickListener(this);

        viewHolder.et_user_name.setOnFocusChangeListener(this);
        viewHolder.et_user_name.setOnClickListener(this);

    }

    /**
     * 修改性别
     *
     * @param sex
     */
    private void modifySex(final String sex) {
        new UserBll().updateUserInfo(getContext(), userId, Constant.sex, sex, new Action1<Object>() {
            @Override
            public void call(Object o) {
                //postToMain(ModifyUserType.MODIFY_SEX);
                SharedPreferenceUtils.setString(getContext(),Constant.sex,sex);
                EventBus.getDefault().post(new MessageEvent(sex, ModifyUserType.MODIFY_SEX));
            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_5_9:
                clearCheckBoxState();
                viewHolder.cb_5_9.setChecked(true);
                modifyAge(Constant.age_5_9);
                break;
            case R.id.ll_10_14:
                clearCheckBoxState();
                viewHolder.cb_10_14.setChecked(true);
                modifyAge(Constant.age_10_14);
                break;
            case R.id.ll_15_20:
                clearCheckBoxState();
                viewHolder.cb_15_20.setChecked(true);
                modifyAge(Constant.age_15_20);
                break;
            case R.id.ll_21_30:
                clearCheckBoxState();
                viewHolder.cb_21_30.setChecked(true);
                modifyAge(Constant.age_21_30);
                break;
            case R.id.ll_30_50:
                clearCheckBoxState();
                viewHolder.cb_30_50.setChecked(true);
                modifyAge(Constant.age_30_50);
                break;
            case R.id.ll_50:
                clearCheckBoxState();
                viewHolder.cb_50.setChecked(true);
                modifyAge(Constant.age_50);
                break;
            case R.id.iv_edit:
                //clickEdit();
                break;
            case R.id.bt_exit:
                //exitLogin();
                Intent intent = new Intent(getContext(), AppOutActivity.class);
                intent.putExtra("key","exitlogin");
                startActivity(intent);
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

    /**
     * 退出登录
     */
    private void exitLogin() {
        //保存退出登录的状态
        SharedPreferenceUtils.setBoolean(getContext(), Constant.isLogin, false);
        //清空sp
        SharedPreferenceUtils.getSp(getContext()).edit().clear().commit();
        //通知主界面 用户已退出登录
        EventBus.getDefault().post(new MessageEvent(false, ModifyUserType.MODIFY_EXIT_LOGIN));
    }

    /**
     * 修改年龄
     */
    private void modifyAge(final int ageType) {
        new UserBll().updateUserInfo(getContext(), userId, "age_group", ageType + "", new Action1<Object>() {
            @Override
            public void call(Object o) {
                //postToMain(ModifyUserType.MODIFY_AGE);
                //修改本地年龄信息
                SharedPreferenceUtils.setInt(getContext(),Constant.ageGroup,ageType);
                String[] ages = getContext().getResources().getStringArray(R.array.age);
                if (ageType <= 6)
                    EventBus.getDefault().post(new MessageEvent(ages[ageType - 1], ModifyUserType.MODIFY_AGE));

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


    /**
     * 将message post到宿主activity
     *
     * @param type
     */
    private void postToMain(final int type) {
        new UserBll().getUserInfo(getContext(), userId, new Action1<UserInfoRsp>() {
            @Override
            public void call(UserInfoRsp userInfoRsp) {
                //1 登录 2 修改用户名 3 修改性别 4 修改年龄 5 修改手机号 6 修改邮箱 7 修改级别
                if (type == ModifyUserType.MODIFY_USERNAME) {
                    EventBus.getDefault().post(new MessageEvent(userInfoRsp.getUsername(), type));
                } else if (type == ModifyUserType.MODIFY_SEX) {
                    EventBus.getDefault().post(new MessageEvent(userInfoRsp.getSex(), type));
                } else if (type == ModifyUserType.MODIFY_AGE) {
                    String[] ages = getContext().getResources().getStringArray(R.array.age);
                    int ageGroup = userInfoRsp.getAge_group();
                    if (ageGroup <= 6)
                        EventBus.getDefault().post(new MessageEvent(ages[ageGroup - 1], type));
                } else if (type == ModifyUserType.MODIFY_PHONE) {
                    EventBus.getDefault().post(new MessageEvent(userInfoRsp.getTel(), type));
                } else if (type == ModifyUserType.MODIFY_EMAIL) {
                    EventBus.getDefault().post(new MessageEvent(userInfoRsp.getEmail(), type));
                } else if (type == ModifyUserType.MODIFY_LEVEL) {
                    EventBus.getDefault().post(new MessageEvent(userInfoRsp.getLevel(), type));
                }
            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {

            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            viewHolder.et_user_name.setCursorVisible(false);
            new UserBll().updateUserInfo(getContext(), userId, Constant.username, viewHolder.et_user_name.getText().toString().trim(), new Action1<Object>() {
                @Override
                public void call(Object o) {
                    //更改用户信息成功,将更改成功的信息发送到NewUserCenterActivity
                    //postToMain(ModifyUserType.MODIFY_USERNAME);

                    //更新本地用户名名信息
                    SharedPreferenceUtils.setString(getContext(),Constant.username,viewHolder.et_user_name.getText().toString().trim());
                    EventBus.getDefault().post(new MessageEvent(viewHolder.et_user_name.getText().toString().trim(), ModifyUserType.MODIFY_USERNAME));
                }
            }, new Action2<Integer, String>() {
                @Override
                public void call(Integer integer, String s) {

                }
            });
        }else{
            viewHolder.et_user_name.requestFocus(); //获取焦点,光标出现
            viewHolder.et_user_name.setFocusable(true);
            viewHolder.et_user_name.setCursorVisible(true);
        }
    }


    static class ViewHolder {

        @BindView(R.id.et_user_name)
        EditText et_user_name;
        @BindView(R.id.iv_edit)
        ImageView iv_edit;
        @BindView(R.id.cb_man)
        CheckBox cb_man;
        @BindView(R.id.tv_man)
        TextView tv_man;
        @BindView(R.id.cb_girl)
        CheckBox cb_girl;
        @BindView(R.id.tv_girl)
        TextView tv_girl;
        @BindView(R.id.ll_5_9)
        LinearLayout ll_5_9;
        @BindView(R.id.cb_5_9)
        CheckBox cb_5_9;
        @BindView(R.id.ll_10_14)
        LinearLayout ll_10_14;
        @BindView(R.id.cb_10_14)
        CheckBox cb_10_14;
        @BindView(R.id.ll_15_20)
        LinearLayout ll_15_20;
        @BindView(R.id.cb_15_20)
        CheckBox cb_15_20;
        @BindView(R.id.ll_21_30)
        LinearLayout ll_21_30;
        @BindView(R.id.cb_21_30)
        CheckBox cb_21_30;
        @BindView(R.id.ll_30_50)
        LinearLayout ll_30_50;
        @BindView(R.id.cb_30_50)
        CheckBox cb_30_50;
        @BindView(R.id.ll_50)
        LinearLayout ll_50;
        @BindView(R.id.cb_50)
        CheckBox cb_50;
        @BindView(R.id.bt_exit)
        Button bt_exit;
        @BindView(R.id.tv_5_9)
        TextView tv_5_9;
        @BindView(R.id.tv_10_14)
        TextView tv_10_14;
        @BindView(R.id.tv_15_20)
        TextView tv_15_20;
        @BindView(R.id.tv_21_30)
        TextView tv_21_30;
        @BindView(R.id.tv_30_50)
        TextView tv_30_50;
        @BindView(R.id.tv_50)
        TextView tv_50;
        @BindView(R.id.rl_edit_username)
        RelativeLayout rl_edit_username;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
