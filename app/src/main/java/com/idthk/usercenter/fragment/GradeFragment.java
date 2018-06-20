package com.idthk.usercenter.fragment;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.idthk.usercenter.APP;
import com.idthk.usercenter.R;
import com.idthk.usercenter.bean.MessageEvent;
import com.idthk.usercenter.bean.response.UserInfoRsp;
import com.idthk.usercenter.bll.UserBll;
import com.idthk.usercenter.utils.Constant;
import com.idthk.usercenter.utils.ModifyUserType;
import com.idthk.usercenter.utils.SharedPreferenceUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.functions.Action2;

/**
 * Created by pengyu.
 * 2018/2/2
 */

public class GradeFragment extends BaseFragment implements View.OnClickListener {

    public static GradeFragment newInstance(String s) {
        GradeFragment userGradeFragment = new GradeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("grade", s);
        userGradeFragment.setArguments(bundle);
        return userGradeFragment;
    }

    private ViewHolder viewHolder;
    int userId = SharedPreferenceUtils.getInt(getContext(), Constant.userId, -1);
//    private PermissionsChecker mPermissionsChecker; // 权限检测器
    static final String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    private String grade=Constant.junior;//初级

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_grade_layout;
    }

    @Override
    protected void initView(View view) {
        viewHolder = new ViewHolder(view);
        viewHolder.ll_one_grade.setOnClickListener(this);
        viewHolder.ll_two_grade.setOnClickListener(this);
        viewHolder.ll_three_grade.setOnClickListener(this);

        clearCheckBoxState();
        viewHolder.cb_one_grade.setChecked(true);

    }

    private void clearCheckBoxState() {
        viewHolder.cb_one_grade.setChecked(false);
        viewHolder.cb_two_grade.setChecked(false);
        viewHolder.cb_three_grade.setChecked(false);
    }

    @Override
    protected void initData() {
//        mPermissionsChecker = new PermissionsChecker(APP.getContext());
        String gradeStr = (String) getArguments().get("grade");
        if(gradeStr.equals(Constant.junior)){
            clearCheckBoxState();
            viewHolder.cb_one_grade.setChecked(true);
            grade=Constant.junior;
        }else if(gradeStr.equals(Constant.intermediate)){
            clearCheckBoxState();
            viewHolder.cb_two_grade.setChecked(true);
            grade=Constant.intermediate;
        }else if(gradeStr.equals(Constant.senior)){
            clearCheckBoxState();
            viewHolder.cb_three_grade.setChecked(true);
            grade=Constant.senior;
        }
    }

    @Override
    public void onClick(View v) {
        clearCheckBoxState();
        switch (v.getId()){
            case R.id.ll_one_grade:
                clearCheckBoxState();
                viewHolder.cb_one_grade.setChecked(true);
                grade=Constant.junior;
                modifyLevel(grade);
                break;
            case R.id.ll_two_grade:
                clearCheckBoxState();
                viewHolder.cb_two_grade.setChecked(true);
                grade=Constant.intermediate;
                modifyLevel(grade);
                break;
            case R.id.ll_three_grade:
                clearCheckBoxState();
                viewHolder.cb_three_grade.setChecked(true);
                grade=Constant.senior;
                modifyLevel(grade);
                break;
        }
    }

    /**
     * 修改等级
     * @param grade
     */
    private void modifyLevel(final String grade) {
        new UserBll().updateUserInfo(getContext(), userId, "level", grade, new Action1<Object>() {
            @Override
            public void call(Object o) {
                //postToMain(ModifyUserType.MODIFY_LEVEL);
                SharedPreferenceUtils.setString(getContext(),Constant.level,grade);
                if(grade.equals(Constant.junior)) {
                    EventBus.getDefault().post(new MessageEvent(getContext().getResources().getString(R.string.show_junior), ModifyUserType.MODIFY_LEVEL));
                }else if(grade.equals(Constant.intermediate)){
                    EventBus.getDefault().post(new MessageEvent(getContext().getResources().getString(R.string.show_intermediate), ModifyUserType.MODIFY_LEVEL));
                }else if(grade.equals(Constant.senior)){
                    EventBus.getDefault().post(new MessageEvent(getContext().getResources().getString(R.string.show_senior), ModifyUserType.MODIFY_LEVEL));
                }
            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {
                Toast.makeText(APP.getContext(), s,Toast.LENGTH_SHORT).show();
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
                    if(userInfoRsp.getLevel().equals(Constant.junior)) {
                        EventBus.getDefault().post(new MessageEvent(getContext().getResources().getString(R.string.show_junior), type));
                    }else if(userInfoRsp.getLevel().equals(Constant.intermediate)){
                        EventBus.getDefault().post(new MessageEvent(getContext().getResources().getString(R.string.show_intermediate), type));
                    }else if(userInfoRsp.getLevel().equals(Constant.senior)){
                        EventBus.getDefault().post(new MessageEvent(getContext().getResources().getString(R.string.show_senior), type));
                    }
                }
            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {

            }
        });
    }

    static class ViewHolder {


        @BindView(R.id.cb_one_grade)
        CheckBox cb_one_grade;
        @BindView(R.id.cb_two_grade)
        CheckBox cb_two_grade;
        @BindView(R.id.cb_three_grade)
        CheckBox cb_three_grade;
        @BindView(R.id.ll_one_grade)
        LinearLayout ll_one_grade;
        @BindView(R.id.ll_two_grade)
        LinearLayout ll_two_grade;
        @BindView(R.id.ll_three_grade)
        LinearLayout ll_three_grade;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
