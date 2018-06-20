package com.idthk.usercenter.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
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
public class NextStep2Activity extends BaseActivity implements View.OnClickListener {
    private ViewHolder viewHolder;
    private String sex = Constant.male;
    int userId = SharedPreferenceUtils.getInt(this, Constant.userId, -1);
    @Override
    protected int getLayoutId() {
        return R.layout.activity_next_step2_layout;
    }

    @Override
    protected void initView() {
        viewHolder=new ViewHolder(this);

        viewHolder.ll_mail.setOnClickListener(this);
        viewHolder.ll_femail.setOnClickListener(this);
        viewHolder.tv_next_step.setOnClickListener(this);

        viewHolder.ll_mail.setSelected(true);
    }

    @Override
    protected void initData() {

    }

    private void clearStatus(){
        viewHolder.ll_mail.setSelected(false);
        viewHolder.ll_femail.setSelected(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_mail:
                clearStatus();
                sex=Constant.male;
                viewHolder.ll_mail.setSelected(true);
                break;
            case R.id.ll_femail:
                clearStatus();
                sex=Constant.female;
                viewHolder.ll_femail.setSelected(true);
                break;
            case R.id.tv_next_step://下一步
                next();
                break;
        }
    }

    private void next() {
        new UserBll().updateUserInfo(NextStep2Activity.this, userId, Constant.sex, sex, new Action1<Object>() {
            @Override
            public void call(Object o) {
//                ToastUtils.showMessage(getContext(),getString(R.string.save_sex_success));
                SharedPreferenceUtils.setString(getContext(),Constant.sex,sex);
//                EventBus.getDefault().post(new MessageEvent(sex, ModifyUserType.MODIFY_SEX));
                Intent intent=new Intent(NextStep2Activity.this,NextStep3Activity.class);
                startActivity(intent);
                finish();
            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {

            }
        });
    }

    static class ViewHolder{
        @BindView(R.id.ll_mail)
        LinearLayout ll_mail;
        @BindView(R.id.ll_femail)
        LinearLayout ll_femail;
        @BindView(R.id.tv_next_step)
        TextView tv_next_step;
        public ViewHolder(Activity view){
            ButterKnife.bind(this,view);
        }
    }
}
