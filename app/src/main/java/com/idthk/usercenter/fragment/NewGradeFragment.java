package com.idthk.usercenter.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.idthk.usercenter.APP;
import com.idthk.usercenter.R;
import com.idthk.usercenter.activity.NewLoginActivity;
import com.idthk.usercenter.bean.MessageEvent;
import com.idthk.usercenter.bll.UserBll;
import com.idthk.usercenter.db.DbManager;
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
 * 2018/6/6
 */
public class NewGradeFragment extends BaseFragment implements View.OnClickListener {

    ViewHolder viewHolder;
    private String grade= Constant.junior;//初级
    int userId = SharedPreferenceUtils.getInt(getContext(), Constant.userId, -1);
    private DbManager dbManager;
    int devideId=SharedPreferenceUtils.getInt(getContext(),Constant.deviceId,-1);

    public static NewGradeFragment newInstance(String s) {
        NewGradeFragment userGradeFragment = new NewGradeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("grade", s);
        userGradeFragment.setArguments(bundle);
        return userGradeFragment;
    }
    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_grade_layout;
    }

    @Override
    protected void initView(View view) {
        viewHolder=new ViewHolder(view);

        dbManager = new DbManager(getContext());

        viewHolder.rl_first.setOnClickListener(this);
        viewHolder.rl_middle.setOnClickListener(this);
        viewHolder.rl_high.setOnClickListener(this);
        viewHolder.bt_save.setOnClickListener(this);

        String level = SharedPreferenceUtils.getString(getContext(), Constant.level, "");
        if(level.equals(Constant.junior)){
            clearStatus();
            viewHolder.rl_first.setSelected(true);
            grade=Constant.junior;
        }else if(level.equals(Constant.intermediate)){
            clearStatus();
            viewHolder.rl_middle.setSelected(true);
            grade=Constant.intermediate;
        }else if(level.equals(Constant.senior)){
            clearStatus();
            viewHolder.rl_high.setSelected(true);
            grade=Constant.senior;
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_first:
                clearStatus();
                grade=Constant.junior;
                viewHolder.rl_first.setSelected(true);
                break;
            case R.id.rl_middle:
                clearStatus();
                grade=Constant.intermediate;
                viewHolder.rl_middle.setSelected(true);
                break;
            case R.id.rl_high:
                clearStatus();
                grade=Constant.senior;
                viewHolder.rl_high.setSelected(true);
                break;
            case R.id.bt_save:
                save();
                break;
        }
    }


    private void save() {
        new UserBll().updateUserInfo(getContext(), userId, "level", grade, new Action1<Object>() {
            @Override
            public void call(Object o) {
                ToastUtils.showMessage(APP.getContext(),getString(R.string.save_grade_success));

                SharedPreferenceUtils.setString(getContext(),Constant.level,grade);
                if(grade.equals(Constant.junior)) {
                    EventBus.getDefault().post(new MessageEvent(getContext().getResources().getString(R.string.show_junior), ModifyUserType.MODIFY_LEVEL));
                }else if(grade.equals(Constant.intermediate)){
                    EventBus.getDefault().post(new MessageEvent(getContext().getResources().getString(R.string.show_intermediate), ModifyUserType.MODIFY_LEVEL));
                }else if(grade.equals(Constant.senior)){
                    EventBus.getDefault().post(new MessageEvent(getContext().getResources().getString(R.string.show_senior), ModifyUserType.MODIFY_LEVEL));
                }
                dbManager.update(0, "mmm", ""+devideId, 1,grade);
                setFullScreen();
            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {
                Toast.makeText(APP.getContext(), s,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearStatus(){
        viewHolder.rl_first.setSelected(false);
        viewHolder.rl_middle.setSelected(false);
        viewHolder.rl_high.setSelected(false);
    }

    static class ViewHolder{

        @BindView(R.id.rl_first)
        RelativeLayout rl_first;
        @BindView(R.id.rl_middle)
        RelativeLayout rl_middle;
        @BindView(R.id.rl_high)
        RelativeLayout rl_high;
        @BindView(R.id.bt_save)
        Button bt_save;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
