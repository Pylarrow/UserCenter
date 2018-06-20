/*
package com.idthk.usercenter.activity;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.idthk.usercenter.R;
import com.idthk.usercenter.fragment.AgeFragment;
import com.idthk.usercenter.fragment.AppUpdateFragment;
import com.idthk.usercenter.fragment.DataFragment;
import com.idthk.usercenter.fragment.DeviceFragment;
import com.idthk.usercenter.fragment.GradeFragment;
import com.idthk.usercenter.fragment.MailFragment;
import com.idthk.usercenter.fragment.NewUserFragment;
import com.idthk.usercenter.fragment.PhoneFragment;
import com.idthk.usercenter.fragment.UserFragment;
import com.idthk.usercenter.fragment.VpiFragment;
import com.idthk.usercenter.utils.tools.UtilTools;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

*/
/**
 * Created by williamyin on 2017/12/20.
 *//*


public class UserCenterActivity extends BaseActivity implements View.OnClickListener {

    private ViewHolder viewHolder;
    private AgeFragment ageFragment;
    private AppUpdateFragment appUpdateFragment;
    private UserFragment userFragment;
    private DataFragment dataFragment;
    private VpiFragment vpiFragment;
    private MailFragment mailFragment;

    private PhoneFragment phoneFragment;

    private DeviceFragment deviceFragment;
    private NewUserFragment newUserFragment;
    private GradeFragment gradeFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.user_center;
    }

    @Override
    protected void initView() {
        viewHolder = new ViewHolder(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.to_age:
                if (ageFragment == null) {
                    ageFragment = AgeFragment.newInstance("age");
                }
                transaction.replace(R.id.fragment_content, ageFragment);
                break;
            case R.id.to_appupdate:
                if (appUpdateFragment == null) {
                    appUpdateFragment = AppUpdateFragment.newInstance("appupdate");
                }
                transaction.replace(R.id.fragment_content, appUpdateFragment);
                break;
            case R.id.to_data:
                if (dataFragment == null) {
                    dataFragment = DataFragment.newInstance("data");
                }
                transaction.replace(R.id.fragment_content, dataFragment);
                break;
            case R.id.to_mail:
                if (mailFragment == null) {
                    mailFragment = MailFragment.newInstance("mail");
                }
                transaction.replace(R.id.fragment_content, mailFragment);
                break;
            case R.id.to_vpi:
                if (vpiFragment == null) {

                    vpiFragment = VpiFragment.newInstance("vpi");
                }
                transaction.replace(R.id.fragment_content, vpiFragment);
                break;
            case R.id.to_usercenter:
                userFragment = UserFragment.newInstance("user");
                transaction.replace(R.id.fragment_content, userFragment);
                break;
            case R.id.to_phone:
                if (phoneFragment == null) {
                    phoneFragment = PhoneFragment.newInstance("phone");
                }
                transaction.replace(R.id.fragment_content, phoneFragment);
                break;
            case R.id.rl_new_phone:
                if (phoneFragment == null) {
                    phoneFragment = PhoneFragment.newInstance("phone");
                }
                transaction.replace(R.id.fragment_content, phoneFragment);
                break;
            case R.id.rl_new_email:
                if (mailFragment == null) {
                    mailFragment = MailFragment.newInstance("mail");
                }
                transaction.replace(R.id.fragment_content, mailFragment);
                break;
            case R.id.rl_new_grade:
                if (gradeFragment == null) {
                    gradeFragment = GradeFragment.newInstance("grade");
                }
                transaction.replace(R.id.fragment_content, gradeFragment);
                break;
            case R.id.rl_new_device:
                if (deviceFragment == null) {
                    deviceFragment = DeviceFragment.newInstance("device");
                }
                transaction.replace(R.id.fragment_content, deviceFragment);
                break;
            case R.id.rl_user_center:
                if(newUserFragment==null){
                    newUserFragment=NewUserFragment.newInstance(null);
                }
                transaction.replace(R.id.fragment_content,newUserFragment);
                break;
        }
        transaction.commit();
    }

    @Override
    protected void initData() {

        viewHolder.toAppupdate.setOnClickListener(this);
        viewHolder.toAge.setOnClickListener(this);
        viewHolder.toData.setOnClickListener(this);
        viewHolder.toMail.setOnClickListener(this);
        viewHolder.toPhone.setOnClickListener(this);
        viewHolder.toVpi.setOnClickListener(this);
        viewHolder.toUsercenter.setOnClickListener(this);

        viewHolder.rl_new_phone.setOnClickListener(this);
        viewHolder.rl_new_email.setOnClickListener(this);
        viewHolder.rl_new_grade.setOnClickListener(this);
        viewHolder.rl_new_device.setOnClickListener(this);
        viewHolder.rl_user_center.setOnClickListener(this);
//
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        newUserFragment = NewUserFragment.newInstance(null);
        transaction.replace(R.id.fragment_content, newUserFragment);
        transaction.commit();

        Acp.getInstance(this).request(new AcpOptions.Builder()
                        .setPermissions(UtilTools.PERMISSIONS).build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                    }
                });

    }


    static class ViewHolder {
        @BindView(R.id.user_icon)
        SimpleDraweeView userIcon;
        @BindView(R.id.user_text)
        TextView userText;
        @BindView(R.id.to_usercenter)
        RelativeLayout toUsercenter;
        @BindView(R.id.age_icon)
        ImageView ageIcon;
        @BindView(R.id.age_text)
        TextView ageText;
        @BindView(R.id.to_age)
        RelativeLayout toAge;
        @BindView(R.id.phone_icon)
        ImageView phoneIcon;
        @BindView(R.id.phone_text)
        TextView phoneText;
        @BindView(R.id.to_phone)
        RelativeLayout toPhone;
        @BindView(R.id.mail_icon)
        ImageView mailIcon;
        @BindView(R.id.mail_text)
        TextView mailText;
        @BindView(R.id.to_mail)
        RelativeLayout toMail;
        @BindView(R.id.vpi_icon)
        ImageView vpiIcon;
        @BindView(R.id.vpi_text)
        TextView vpiText;
        @BindView(R.id.to_vpi)
        RelativeLayout toVpi;
        @BindView(R.id.appupdate_icon)
        ImageView appupdateIcon;
        @BindView(R.id.appupdate_text)
        TextView appupdateText;
        @BindView(R.id.to_appupdate)
        RelativeLayout toAppupdate;
        @BindView(R.id.data_icon)
        ImageView dataIcon;
        @BindView(R.id.data_text)
        TextView dataText;
        @BindView(R.id.to_data)
        RelativeLayout toData;
        @BindView(R.id.fragment_content)
        LinearLayout fragmentContent;

        @BindView(R.id.rl_new_phone)
        RelativeLayout rl_new_phone;
        @BindView(R.id.rl_new_email)
        RelativeLayout rl_new_email;
        @BindView(R.id.rl_new_grade)
        RelativeLayout rl_new_grade;
        @BindView(R.id.rl_new_device)
        RelativeLayout rl_new_device;
        @BindView(R.id.rl_user_center)
        RelativeLayout rl_user_center;


        ViewHolder(Activity view) {
            ButterKnife.bind(this, view);
        }
    }


}
*/
