package com.idthk.usercenter.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.idthk.usercenter.R;
import com.idthk.usercenter.bean.MessageEvent;
import com.idthk.usercenter.bean.response.DeviceInfoBean;
import com.idthk.usercenter.bean.response.RenewBean;
import com.idthk.usercenter.bean.response.UserInfoRsp;
import com.idthk.usercenter.bll.UserBll;
import com.idthk.usercenter.fragment.AgeGroupFragment;
import com.idthk.usercenter.fragment.DeviceFragment;
import com.idthk.usercenter.fragment.GradeFragment;
import com.idthk.usercenter.fragment.MailFragment;
import com.idthk.usercenter.fragment.NewDeviceFragment;
import com.idthk.usercenter.fragment.NewGradeFragment;
import com.idthk.usercenter.fragment.NewUser1Fragment;
import com.idthk.usercenter.fragment.NewUserFragment;
import com.idthk.usercenter.fragment.NotLoginFragment;
import com.idthk.usercenter.fragment.PhoneFragment;
import com.idthk.usercenter.fragment.SixFragment;
import com.idthk.usercenter.utils.Constant;
import com.idthk.usercenter.utils.ModifyUserType;
import com.idthk.usercenter.utils.SharedPreferenceUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import rx.functions.Action1;
import rx.functions.Action2;

/**
 * Created by pengyu.
 * 2018/2/3
 */

public class NewUserCenterActivity extends BaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks,
        EasyPermissions.RationaleCallbacks {
    private ViewHolder viewHolder;
    private MailFragment mailFragment;

    private PhoneFragment phoneFragment;

    private DeviceFragment deviceFragment;
    private NewUserFragment newUserFragment;
    private NewUser1Fragment newUser1Fragment;
    private GradeFragment gradeFragment;
    private NotLoginFragment notLoginFragment;
    private AgeGroupFragment ageGroupFragment;
    private SixFragment sixFragmet;
    private NewGradeFragment newGradeFragment;
    private NewDeviceFragment newDeviceFragment;

    //是否登录标志位
    private boolean isLogin = false;
    private UserInfoRsp userData;
    private String type = "";
    private String language = "";

    static final String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    private static final int RC_READ_PERM = 123;
    private static final int RC_WRITE_PERM = 124;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_usercenter1_layout;
    }

    @Override
    protected void initView() {
        viewHolder = new ViewHolder(this);
        readTask();
        writeTask();

        isLogin = SharedPreferenceUtils.getBoolean(getContext(), Constant.isLogin, false);
        if (isLogin) {
            viewHolder.tv_exit_login.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tv_exit_login.setVisibility(View.GONE);
        }
        //获取本地语言环境
        getLocalLanguage();
        getUserType();
        showUser();
        Intent intent = getIntent();
        if (intent != null) {
            String sgv638_login = intent.getStringExtra("sgv638_login");
            if (sgv638_login != null && sgv638_login.equals("tologin")) {
                Intent intent1 = new Intent(NewUserCenterActivity.this, NewLoginActivity.class);
                startActivity(intent1);
                finish();
            } else if (sgv638_login != null && sgv638_login.equals("torenewal")) {
                Intent intent2 = new Intent(NewUserCenterActivity.this, RenewActivity.class);
                startActivity(intent2);
                finish();
            }
        }

    }

    private void getDeviceInfo() {
        //获取设备ID
        int deviceId = SharedPreferenceUtils.getInt(getContext(), Constant.deviceId);
        //获取设备信息
        new UserBll().getDeviceInfo(getContext(), deviceId, new Action1<DeviceInfoBean>() {
            @Override
            public void call(DeviceInfoBean deviceInfoBean) {

                SharedPreferenceUtils.setBoolean(NewUserCenterActivity.this, Constant.expired, deviceInfoBean.isExpired());
                long activate_time = deviceInfoBean.getActivate_time();//第一次激活时间
                long expire_time = deviceInfoBean.getExpire_time();//到期时间
                SharedPreferenceUtils.setLong(NewUserCenterActivity.this, Constant.activate_time, activate_time);
                SharedPreferenceUtils.setLong(NewUserCenterActivity.this, Constant.expire_time, expire_time);

            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {

            }
        });
    }

    @AfterPermissionGranted(RC_WRITE_PERM)
    private void writeTask() {
        if (hasWritePermission()) {
            // Have permission, do the thing!
            //Toast.makeText(this, "TODO: Write things", Toast.LENGTH_LONG).show();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    "",
                    RC_WRITE_PERM,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @AfterPermissionGranted(RC_READ_PERM)
    private void readTask() {
        if (hasReadPermission()) {
            // Have permission, do the thing!
            //Toast.makeText(this, "TODO: Read things", Toast.LENGTH_LONG).show();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    "",
                    RC_READ_PERM,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private boolean hasReadPermission() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private boolean hasWritePermission() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 获取用户数据
     */
    private void showUser() {
        if (isLogin) {
            //从本地获取用户信息
            String username = SharedPreferenceUtils.getString(NewUserCenterActivity.this, Constant.username, "");
            String sex = SharedPreferenceUtils.getString(NewUserCenterActivity.this, Constant.sex, "");
            int ageGroup = SharedPreferenceUtils.getInt(NewUserCenterActivity.this, Constant.ageGroup, -1);
            String phone = SharedPreferenceUtils.getString(NewUserCenterActivity.this, Constant.phone, "");
            String email = SharedPreferenceUtils.getString(NewUserCenterActivity.this, Constant.email, "");
            String level = SharedPreferenceUtils.getString(NewUserCenterActivity.this, Constant.level, "");

            viewHolder.tv_user_name.setText(getString(R.string.nickname_desc) + "  " + username);
//            if (sex.equals(Constant.male)) {
//                viewHolder.iv_sex_icon.setImageDrawable(getResources().getDrawable(R.drawable.male));
//                viewHolder.iv_sex_icon.setVisibility(View.GONE);
//            } else {
//                viewHolder.iv_sex_icon.setImageDrawable(getResources().getDrawable(R.drawable.femail));
//                viewHolder.iv_sex_icon.setVisibility(View.GONE);
//            }
            String[] ages = getContext().getResources().getStringArray(R.array.age);
            if (ageGroup <= 6 && ageGroup > 0)
                viewHolder.tv_age_group.setText(ages[ageGroup - 1]);
            if (sex.equals(Constant.male)) {
                viewHolder.tv_sex.setText(getResources().getString(R.string.man_desc));
                viewHolder.iv_icon.setImageDrawable(getResources().getDrawable(R.drawable.man_big));
            } else {
                viewHolder.tv_sex.setText(getResources().getString(R.string.girl_desc));
                viewHolder.iv_icon.setImageDrawable(getResources().getDrawable(R.drawable.femail_big));
            }

            viewHolder.tv_phone.setText(phone);
            viewHolder.tv_email.setText(email);
            if (level.equals(Constant.junior)) {
                viewHolder.tv_grade.setText(getResources().getString(R.string.show_junior));
            } else if (level.equals(Constant.intermediate)) {
                viewHolder.tv_grade.setText(getResources().getString(R.string.show_intermediate));
            } else if (level.equals(Constant.senior)) {
                viewHolder.tv_grade.setText(getResources().getString(R.string.show_senior));
            }
//            final int userId = SharedPreferenceUtils.getInt(getContext(), Constant.userId, -1);
//            new UserBll().getUserInfo(getContext(), userId, new Action1<UserInfoRsp>() {
//                @Override
//                public void call(UserInfoRsp userInfoRsp) {
//                    userData=userInfoRsp;
//                    viewHolder.tv_user_name.setText(userInfoRsp.getUsername());
//                    if (userInfoRsp.getSex().equals(Constant.male)) {
//                        viewHolder.iv_sex_icon.setImageDrawable(getResources().getDrawable(R.drawable.male));
//                    } else {
//                        viewHolder.iv_sex_icon.setImageDrawable(getResources().getDrawable(R.drawable.femail));
//                    }
//                    String[] ages = getContext().getResources().getStringArray(R.array.age);
//                    int ageGroup = userInfoRsp.getAge_group();
//                    if (ageGroup <= 6)
//                        viewHolder.tv_age.setText(ages[ageGroup - 1]);
//                    viewHolder.tv_phone.setText(userInfoRsp.getTel());
//                    viewHolder.tv_email.setText(userInfoRsp.getEmail());
//                    if (userInfoRsp.getLevel().equals(Constant.junior)) {
//                        viewHolder.tv_grade.setText(getResources().getString(R.string.show_junior));
//                    } else if (userInfoRsp.getLevel().equals(Constant.intermediate)) {
//                        viewHolder.tv_grade.setText(getResources().getString(R.string.show_intermediate));
//                    } else if (userInfoRsp.getLevel().equals(Constant.senior)) {
//                        viewHolder.tv_grade.setText(getResources().getString(R.string.show_senior));
//                    }
//                    //setUserData(userInfoRsp);
//                    EventBus.getDefault().post(new MessageEvent(userInfoRsp));
//                }
//            }, new Action2<Integer, String>() {
//                @Override
//                public void call(Integer integer, String s) {
//                    Toast.makeText(NewUserCenterActivity.this,s,Toast.LENGTH_SHORT).show();
//                }
//            });
        } else {
            viewHolder.tv_phone.setText("");
            viewHolder.tv_email.setText("");
            viewHolder.tv_grade.setText("");
            viewHolder.tv_age_group.setText("");
            viewHolder.tv_sex.setText("");
        }
    }

    private void getLocalLanguage() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = getResources().getConfiguration().locale;
        }

        //获取语言的正确姿势
        String lang = locale.getLanguage() + "-" + locale.getCountry();
        if (lang.contains("en")) {
            language = "usd";
        } else if (lang.contains("zh")) {
            language = "rmb";
        }
    }

    private void getRenewPrice() {
        new UserBll().renewPrice(getContext(), language, new Action1<RenewBean>() {
            @Override
            public void call(RenewBean renewBean) {
                SharedPreferenceUtils.setString(getContext(), Constant.renewPrice, renewBean.getPrice() + "");
            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //1 登录 2 修改用户名 3 修改性别 4 修改年龄 5 修改手机号 6 修改邮箱 7 修改级别
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent message) {
        if (message.getType() == ModifyUserType.MODIFY_LOGIN) {//登录

            isLogin = true;
            viewHolder.tv_not_login_desc.setVisibility(View.GONE);
            viewHolder.tv_user_name.setVisibility(View.VISIBLE);
            viewHolder.iv_sex_icon.setVisibility(View.VISIBLE);
            viewHolder.tv_age.setVisibility(View.GONE);

            viewHolder.tv_exit_login.setVisibility(View.VISIBLE);

            showUserInfo(message);
            getDeviceInfo();
            getRenewPrice();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            newUser1Fragment = NewUser1Fragment.newInstance("usercenter");
            transaction.replace(R.id.fragment_content, newUser1Fragment);
            //EventBus.getDefault().post(message);
            transaction.commit();

        } else if (message.getType() == ModifyUserType.MODIFY_USERNAME) {//修改用户名
            viewHolder.tv_user_name.setText(getString(R.string.nickname_desc) + "  " + message.getMessage());
        } else if (message.getType() == ModifyUserType.MODIFY_SEX) {//修改性别
            if (message.getMessage().equals(Constant.male)) {
//                viewHolder.iv_sex_icon.setImageDrawable(getResources().getDrawable(R.drawable.male));
                viewHolder.iv_icon.setImageDrawable(getResources().getDrawable(R.drawable.man_big));
            } else {
//                viewHolder.iv_sex_icon.setImageDrawable(getResources().getDrawable(R.drawable.femail));
                viewHolder.iv_icon.setImageDrawable(getResources().getDrawable(R.drawable.femail_big));
            }
            if (message.getMessage().equals(Constant.male)) {
                viewHolder.tv_sex.setText(getResources().getString(R.string.man_desc));
            } else {
                viewHolder.tv_sex.setText(getResources().getString(R.string.girl_desc));
            }
        } else if (message.getType() == ModifyUserType.MODIFY_AGE) {//修改年龄
            viewHolder.tv_age_group.setText(message.getMessage());

        } else if (message.getType() == ModifyUserType.MODIFY_PHONE) {//修改手机号
            viewHolder.tv_phone.setText(message.getMessage());
        } else if (message.getType() == ModifyUserType.MODIFY_EMAIL) {//修改邮箱
            viewHolder.tv_email.setText(message.getMessage());
        } else if (message.getType() == ModifyUserType.MODIFY_LEVEL) {//修改级别
            viewHolder.tv_grade.setText(message.getMessage());
        } else if (message.getType() == ModifyUserType.MODIFY_EXIT_LOGIN) {//退出登录
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            isLogin = SharedPreferenceUtils.getBoolean(getContext(), Constant.isLogin, false);
            if (isLogin) {
                viewHolder.tv_not_login_desc.setVisibility(View.GONE);
                viewHolder.tv_user_name.setVisibility(View.VISIBLE);
                viewHolder.iv_sex_icon.setVisibility(View.GONE);
                viewHolder.tv_age.setVisibility(View.GONE);
                newUser1Fragment = NewUser1Fragment.newInstance(null);

                transaction.replace(R.id.fragment_content, newUser1Fragment);
                showUser();
            } else {
                viewHolder.rl_new_phone.setEnabled(true);
                viewHolder.rl_new_email.setEnabled(true);
                viewHolder.iv_arrow_email.setVisibility(View.VISIBLE);
                viewHolder.iv_arrow_tel.setVisibility(View.VISIBLE);
                viewHolder.tv_exit_login.setVisibility(View.GONE);
                viewHolder.tv_not_login_desc.setVisibility(View.VISIBLE);
                viewHolder.tv_user_name.setVisibility(View.GONE);
                viewHolder.iv_sex_icon.setVisibility(View.GONE);
                viewHolder.tv_age.setVisibility(View.GONE);
                if (notLoginFragment == null) {
                    notLoginFragment = NotLoginFragment.newInstance(getString(R.string.not_login_desc));
                }
                transaction.replace(R.id.fragment_content, notLoginFragment);
                showUser();
            }
            transaction.commit();
        }
    }

    /**
     * 登录成功后显示用户信息
     *
     * @param message
     */
    private void showUserInfo(MessageEvent message) {
        int userId = message.getUserId();
        String username = message.getUsername();
        String email = message.getEmail();
        String tel = message.getTel();
        int ageGroup = message.getAgeGroup();
        String sex = message.getSex();
        String level = message.getLevel();
        int deviceId = message.getDeviceId();

        //显示用户信息
        getUserType();
        viewHolder.tv_user_name.setText(getString(R.string.nickname_desc) + "  " + username);
        if (sex.equals(Constant.male)) {
            viewHolder.iv_icon.setImageDrawable(getResources().getDrawable(R.drawable.man_big));
        } else if (sex.equals(Constant.female)) {
            viewHolder.iv_icon.setImageDrawable(getResources().getDrawable(R.drawable.femail_big));
        }
        if (sex.equals(Constant.male)) {
            viewHolder.tv_sex.setText(getResources().getString(R.string.man_desc));
        } else {
            viewHolder.tv_sex.setText(getResources().getString(R.string.girl_desc));
        }
        String[] ages = getContext().getResources().getStringArray(R.array.age);
        if (ageGroup == 1) {
            viewHolder.tv_age_group.setText(ages[0]);
        } else if (ageGroup == 2) {
            viewHolder.tv_age_group.setText(ages[1]);
        } else if (ageGroup == 3) {
            viewHolder.tv_age_group.setText(ages[2]);
        } else if (ageGroup == 4) {
            viewHolder.tv_age_group.setText(ages[3]);
        } else if (ageGroup == 5) {
            viewHolder.tv_age_group.setText(ages[4]);
        } else if (ageGroup == 6) {
            viewHolder.tv_age_group.setText(ages[5]);
        }


        viewHolder.tv_phone.setText(tel);
        viewHolder.tv_email.setText(email);
        if (level.equals(Constant.junior)) {//初级
            viewHolder.tv_grade.setText(Constant.show_junior);
        } else if (level.equals(Constant.intermediate)) {//中级
            viewHolder.tv_grade.setText(Constant.show_intermediate);
        } else if (level.equals(Constant.senior)) {//高级
            viewHolder.tv_grade.setText(Constant.show_senior);
        }
    }

    private void getUserType() {
        String userNameType = SharedPreferenceUtils.getString(getContext(), Constant.userNameType, "");
        if (userNameType.equals("email")) {
            viewHolder.rl_new_email.setEnabled(false);
            viewHolder.rl_new_phone.setEnabled(true);
            viewHolder.iv_arrow_tel.setVisibility(View.VISIBLE);
            viewHolder.iv_arrow_email.setVisibility(View.INVISIBLE);
        } else if(userNameType.equals("tel")){
            viewHolder.rl_new_email.setEnabled(true);
            viewHolder.rl_new_phone.setEnabled(false);
            viewHolder.iv_arrow_tel.setVisibility(View.INVISIBLE);
            viewHolder.iv_arrow_email.setVisibility(View.VISIBLE);
        }else{
            viewHolder.rl_new_email.setEnabled(true);
            viewHolder.rl_new_phone.setEnabled(true);
            viewHolder.iv_arrow_tel.setVisibility(View.VISIBLE);
            viewHolder.iv_arrow_email.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initData() {
        viewHolder.rl_user_center.setOnClickListener(this);
        viewHolder.rl_new_phone.setOnClickListener(this);
        viewHolder.rl_new_email.setOnClickListener(this);
        viewHolder.rl_new_grade.setOnClickListener(this);
        viewHolder.rl_new_device.setOnClickListener(this);
        viewHolder.rl_age_group.setOnClickListener(this);
        viewHolder.rl_sex.setOnClickListener(this);

        viewHolder.iv_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewUserCenterActivity.this, AppOutActivity.class);
                intent.putExtra("key", "exit");
                //startActivityForResult(intent, 800);
                startActivity(intent);

            }
        });
        viewHolder.tv_exit_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AppOutActivity.class);
                intent.putExtra("key", "exitlogin");
                startActivity(intent);
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (isLogin) {
            viewHolder.tv_not_login_desc.setVisibility(View.GONE);
            viewHolder.tv_user_name.setVisibility(View.VISIBLE);
            viewHolder.iv_sex_icon.setVisibility(View.VISIBLE);
            viewHolder.tv_age.setVisibility(View.GONE);
            newUser1Fragment = NewUser1Fragment.newInstance(null);

            transaction.replace(R.id.fragment_content, newUser1Fragment);
            showUser();
        } else {
            viewHolder.tv_not_login_desc.setVisibility(View.VISIBLE);
            viewHolder.tv_user_name.setVisibility(View.GONE);
            viewHolder.iv_sex_icon.setVisibility(View.GONE);
            viewHolder.tv_age.setVisibility(View.GONE);
            if (notLoginFragment == null) {
                notLoginFragment = NotLoginFragment.newInstance(getString(R.string.not_login_desc));
            }
            transaction.replace(R.id.fragment_content, notLoginFragment);
        }
        transaction.commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 800:
                finish();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.rl_user_center:
                if (isLogin) {
                    if (newUser1Fragment == null) {
                        newUser1Fragment = NewUser1Fragment.newInstance(null);
                    }
                    transaction.replace(R.id.fragment_content, newUser1Fragment);
                } else {
                    if (notLoginFragment == null) {
                        notLoginFragment = NotLoginFragment.newInstance(getString(R.string.not_login_desc));
                    }
                    transaction.replace(R.id.fragment_content, notLoginFragment);
                }

                //viewHolder.rl_user_center.setBackgroundResource(R.color.color_left_full);
                viewHolder.rl_new_phone.setBackgroundResource(R.color.white);
                viewHolder.rl_new_email.setBackgroundResource(R.color.white);
                viewHolder.rl_new_grade.setBackgroundResource(R.color.white);
                viewHolder.rl_new_device.setBackgroundResource(R.color.white);
                viewHolder.rl_sex.setBackgroundResource(R.color.white);
                viewHolder.rl_age_group.setBackgroundResource(R.color.white);

                break;
            case R.id.rl_new_phone:
                if (isLogin) {
                    if (phoneFragment == null) {
                        if (userData != null)
                            phoneFragment = PhoneFragment.newInstance(userData.getTel());
                        else {
                            String phone = SharedPreferenceUtils.getString(NewUserCenterActivity.this, Constant.phone, "");
                            phoneFragment = PhoneFragment.newInstance(phone);
                        }
                    }
                    //EventBus.getDefault().post(new MessageEvent(userData));
                    transaction.replace(R.id.fragment_content, phoneFragment);
                } else {
                    if (notLoginFragment == null) {
                        notLoginFragment = NotLoginFragment.newInstance(getString(R.string.not_login_desc));
                    }
                    transaction.replace(R.id.fragment_content, notLoginFragment);
                }

                //viewHolder.rl_user_center.setBackgroundResource(R.color.white);
                viewHolder.rl_new_phone.setBackgroundResource(R.color.color_left_full);
                viewHolder.rl_new_email.setBackgroundResource(R.color.white);
                viewHolder.rl_new_grade.setBackgroundResource(R.color.white);
                viewHolder.rl_new_device.setBackgroundResource(R.color.white);
                viewHolder.rl_sex.setBackgroundResource(R.color.white);
                viewHolder.rl_age_group.setBackgroundResource(R.color.white);

                break;
            case R.id.rl_new_email:
                if (isLogin) {
                    if (mailFragment == null) {
                        if (userData != null) {
                            mailFragment = MailFragment.newInstance(userData.getEmail());
                        } else {
                            String email = SharedPreferenceUtils.getString(NewUserCenterActivity.this, Constant.email, "");
                            mailFragment = MailFragment.newInstance(email);
                        }
                    }
                    transaction.replace(R.id.fragment_content, mailFragment);
                } else {
                    if (notLoginFragment == null) {
                        notLoginFragment = NotLoginFragment.newInstance(getString(R.string.not_login_desc));
                    }
                    transaction.replace(R.id.fragment_content, notLoginFragment);
                }

                //viewHolder.rl_user_center.setBackgroundResource(R.color.white);
                viewHolder.rl_new_phone.setBackgroundResource(R.color.white);
                viewHolder.rl_new_email.setBackgroundResource(R.color.color_left_full);
                viewHolder.rl_new_grade.setBackgroundResource(R.color.white);
                viewHolder.rl_new_device.setBackgroundResource(R.color.white);
                viewHolder.rl_sex.setBackgroundResource(R.color.white);
                viewHolder.rl_age_group.setBackgroundResource(R.color.white);

                break;
            case R.id.rl_new_grade:
                if (isLogin) {
                    if (newGradeFragment == null) {
//                        if(userData!=null){
//                            newGradeFragment=NewGradeFragment.newInstance(userData.getLevel());
//                        }else{
//                            newGradeFragment=NewGradeFragment.newInstance("");
//                        }
                        newGradeFragment = NewGradeFragment.newInstance("");
                    }
                    transaction.replace(R.id.fragment_content, newGradeFragment);
                } else {
                    if (notLoginFragment == null) {
                        notLoginFragment = NotLoginFragment.newInstance(getString(R.string.not_login_desc));
                    }
                    transaction.replace(R.id.fragment_content, notLoginFragment);
                }

                //viewHolder.rl_user_center.setBackgroundResource(R.color.white);
                viewHolder.rl_new_phone.setBackgroundResource(R.color.white);
                viewHolder.rl_new_email.setBackgroundResource(R.color.white);
                viewHolder.rl_new_grade.setBackgroundResource(R.color.color_left_full);
                viewHolder.rl_new_device.setBackgroundResource(R.color.white);
                viewHolder.rl_sex.setBackgroundResource(R.color.white);
                viewHolder.rl_age_group.setBackgroundResource(R.color.white);

                break;
            case R.id.rl_new_device:
                if (isLogin) {
                    if (newDeviceFragment == null) {
                        newDeviceFragment = NewDeviceFragment.newInstance("device");
                    }
                    transaction.replace(R.id.fragment_content, newDeviceFragment);
                } else {
                    if (notLoginFragment == null) {
                        notLoginFragment = NotLoginFragment.newInstance(getString(R.string.not_login_desc));
                    }
                    transaction.replace(R.id.fragment_content, notLoginFragment);
                }

                //viewHolder.rl_user_center.setBackgroundResource(R.color.white);
                viewHolder.rl_new_phone.setBackgroundResource(R.color.white);
                viewHolder.rl_new_email.setBackgroundResource(R.color.white);
                viewHolder.rl_new_grade.setBackgroundResource(R.color.white);
                viewHolder.rl_new_device.setBackgroundResource(R.color.color_left_full);
                viewHolder.rl_sex.setBackgroundResource(R.color.white);
                viewHolder.rl_age_group.setBackgroundResource(R.color.white);
                break;
            case R.id.rl_age_group:
                if (isLogin) {
                    if (ageGroupFragment == null) {
                        ageGroupFragment = AgeGroupFragment.newInstance("age");
                    }
                    transaction.replace(R.id.fragment_content, ageGroupFragment);
                } else {
                    if (notLoginFragment == null) {
                        notLoginFragment = NotLoginFragment.newInstance(getString(R.string.not_login_desc));
                    }
                    transaction.replace(R.id.fragment_content, notLoginFragment);
                }
                viewHolder.rl_new_phone.setBackgroundResource(R.color.white);
                viewHolder.rl_new_email.setBackgroundResource(R.color.white);
                viewHolder.rl_new_grade.setBackgroundResource(R.color.white);
                viewHolder.rl_age_group.setBackgroundResource(R.color.color_left_full);
                viewHolder.rl_new_device.setBackgroundResource(R.color.white);
                viewHolder.rl_sex.setBackgroundResource(R.color.white);
                break;
            case R.id.rl_sex:
                if (isLogin) {
                    if (sixFragmet == null) {
                        sixFragmet = SixFragment.newInstance("six");
                    }
                    transaction.replace(R.id.fragment_content, sixFragmet);
                } else {
                    if (notLoginFragment == null) {
                        notLoginFragment = NotLoginFragment.newInstance(getString(R.string.not_login_desc));
                    }
                    transaction.replace(R.id.fragment_content, notLoginFragment);
                }
                viewHolder.rl_new_phone.setBackgroundResource(R.color.white);
                viewHolder.rl_new_email.setBackgroundResource(R.color.white);
                viewHolder.rl_new_grade.setBackgroundResource(R.color.white);
                viewHolder.rl_age_group.setBackgroundResource(R.color.white);
                viewHolder.rl_new_device.setBackgroundResource(R.color.white);
                viewHolder.rl_sex.setBackgroundResource(R.color.color_left_full);
        }
        transaction.commit();
    }

    public void setUserData(UserInfoRsp userData) {
        this.userData = userData;
    }

    public UserInfoRsp getUserData() {
        return userData;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onRationaleAccepted(int requestCode) {

    }

    @Override
    public void onRationaleDenied(int requestCode) {

    }

    static class ViewHolder {

        @BindView(R.id.rl_user_center)
        RelativeLayout rl_user_center;
        @BindView(R.id.rl_new_phone)
        RelativeLayout rl_new_phone;
        @BindView(R.id.rl_new_email)
        RelativeLayout rl_new_email;
        @BindView(R.id.rl_new_grade)
        RelativeLayout rl_new_grade;
        @BindView(R.id.rl_new_device)
        RelativeLayout rl_new_device;
        @BindView(R.id.tv_not_login_desc)
        TextView tv_not_login_desc;
        @BindView(R.id.tv_user_name)
        TextView tv_user_name;
        @BindView(R.id.iv_sex_icon)
        ImageView iv_sex_icon;
        @BindView(R.id.tv_age)
        TextView tv_age;
        @BindView(R.id.iv_exit)
        ImageView iv_exit;
        @BindView(R.id.tv_exit_login)
        TextView tv_exit_login;
        @BindView(R.id.tv_phone)
        TextView tv_phone;
        @BindView(R.id.tv_email)
        TextView tv_email;
        @BindView(R.id.tv_grade)
        TextView tv_grade;
        @BindView(R.id.iv_icon)
        ImageView iv_icon;
        @BindView(R.id.rl_age_group)
        RelativeLayout rl_age_group;
        @BindView(R.id.tv_age_group)
        TextView tv_age_group;
        @BindView(R.id.rl_sex)
        RelativeLayout rl_sex;
        @BindView(R.id.tv_sex)
        TextView tv_sex;
        @BindView(R.id.iv_arrow_tel)
        ImageView iv_arrow_tel;
        @BindView(R.id.iv_arrow_email)
        ImageView iv_arrow_email;

        public ViewHolder(Activity view) {
            ButterKnife.bind(this, view);
        }
    }
}
