package com.idthk.usercenter.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.idthk.usercenter.R;
import com.idthk.usercenter.bean.MessageEvent;
import com.idthk.usercenter.db.DbManager;
import com.idthk.usercenter.utils.Constant;
import com.idthk.usercenter.utils.SharedPreferenceUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pengyu.
 * 2018/6/12
 */
public class NextStep4Activity extends BaseActivity implements View.OnClickListener {
    private ViewHolder viewHolder;
    private DbManager dbManager;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_next_step4_layout;
    }

    @Override
    protected void initView() {
        viewHolder=new ViewHolder(this);

        viewHolder.open_usercenter.setOnClickListener(this);

        Animation anim =new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setFillAfter(true); // 设置保持动画最后的状态
        anim.setDuration(6000); // 设置动画时间
        anim.setRepeatCount(Animation.INFINITE);
        anim.setRepeatMode(Animation.INFINITE);
        anim.setInterpolator(new LinearInterpolator()); // 设置插入器
        viewHolder.iv_ball.startAnimation(anim);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.open_usercenter:
                openUserCenter();
                break;
        }
    }

    private void openUserCenter() {
        //跳转到主页
        int userId = SharedPreferenceUtils.getInt(this, Constant.userId, -1);
        String username=SharedPreferenceUtils.getString(this,Constant.username,"");
        String email=SharedPreferenceUtils.getString(this,Constant.email,"");
        String phone=SharedPreferenceUtils.getString(this,Constant.phone,"");
        int ageGroup=1;
        String sex=SharedPreferenceUtils.getString(this,Constant.sex,"");
        String level=SharedPreferenceUtils.getString(this,Constant.level,"");
        int devideId=SharedPreferenceUtils.getInt(this,Constant.deviceId,0);
        EventBus.getDefault().post(new MessageEvent(userId, username, email, phone, ageGroup, sex, level, devideId, 1));
        Intent intent=new Intent(NextStep4Activity.this,NewUserCenterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    static class ViewHolder{
        @BindView(R.id.open_usercenter)
        Button open_usercenter;
        @BindView(R.id.iv_ball)
        ImageView iv_ball;
        public ViewHolder(Activity view){
            ButterKnife.bind(this,view);
        }
    }
}
