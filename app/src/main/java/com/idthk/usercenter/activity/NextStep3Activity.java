package com.idthk.usercenter.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.idthk.usercenter.APP;
import com.idthk.usercenter.R;
import com.idthk.usercenter.bll.UserBll;
import com.idthk.usercenter.db.DbManager;
import com.idthk.usercenter.utils.Constant;
import com.idthk.usercenter.utils.SharedPreferenceUtils;
import com.idthk.usercenter.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.functions.Action2;

/**
 * Created by pengyu.
 * 2018/6/11
 */
public class NextStep3Activity extends BaseActivity implements View.OnClickListener {
    ViewHolder viewHolder;
    int devideId=SharedPreferenceUtils.getInt(getContext(),Constant.deviceId,-1);
    int userId = SharedPreferenceUtils.getInt(getContext(), Constant.userId, -1);
    private DbManager dbManager;
    private String grade= Constant.junior;//初级
    @Override
    protected int getLayoutId() {
        return R.layout.activity_next_step3_layout;
    }

    @Override
    protected void initView() {
        viewHolder=new ViewHolder(this);

        dbManager=new DbManager(NextStep3Activity.this);
        viewHolder.rl_first.setOnClickListener(this);
        viewHolder.rl_middle.setOnClickListener(this);
        viewHolder.rl_high.setOnClickListener(this);
        viewHolder.tv_next_step.setOnClickListener(this);

        clearStatus();
        grade=Constant.junior;
        viewHolder.rl_first.setSelected(true);
    }

    private void clearStatus(){
        viewHolder.rl_first.setSelected(false);
        viewHolder.rl_middle.setSelected(false);
        viewHolder.rl_high.setSelected(false);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_first://初级
                clearStatus();
                grade=Constant.junior;
                viewHolder.rl_first.setSelected(true);
                break;
            case R.id.rl_middle://中级
                clearStatus();
                grade=Constant.intermediate;
                viewHolder.rl_middle.setSelected(true);
                break;
            case R.id.rl_high://高级
                clearStatus();
                grade=Constant.senior;
                viewHolder.rl_high.setSelected(true);
                break;
            case R.id.tv_next_step:
                next();
                break;
        }
    }

    private void next() {
        new UserBll().updateUserInfo(NextStep3Activity.this, userId, "level", grade, new Action1<Object>() {
            @Override
            public void call(Object o) {
                ToastUtils.showMessage(APP.getContext(),getString(R.string.save_grade_success));

                SharedPreferenceUtils.setString(getContext(), Constant.level,grade);
//                if(grade.equals(Constant.junior)) {
//                    EventBus.getDefault().post(new MessageEvent(getContext().getResources().getString(R.string.show_junior), ModifyUserType.MODIFY_LEVEL));
//                }else if(grade.equals(Constant.intermediate)){
//                    EventBus.getDefault().post(new MessageEvent(getContext().getResources().getString(R.string.show_intermediate), ModifyUserType.MODIFY_LEVEL));
//                }else if(grade.equals(Constant.senior)){
//                    EventBus.getDefault().post(new MessageEvent(getContext().getResources().getString(R.string.show_senior), ModifyUserType.MODIFY_LEVEL));
//                }
                dbManager.update(0, "mmm", ""+devideId, 1,grade);
                Intent intent=new Intent(NextStep3Activity.this,NextStep4Activity.class);
                startActivity(intent);
            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {
                Toast.makeText(APP.getContext(), s,Toast.LENGTH_SHORT).show();
            }
        });
    }

    static class ViewHolder{
        @BindView(R.id.tv_next_step)
        TextView tv_next_step;
        @BindView(R.id.rl_first)
        RelativeLayout rl_first;
        @BindView(R.id.rl_middle)
        RelativeLayout rl_middle;
        @BindView(R.id.rl_high)
        RelativeLayout rl_high;

        public ViewHolder(Activity view){
            ButterKnife.bind(this,view);
        }
    }
}

