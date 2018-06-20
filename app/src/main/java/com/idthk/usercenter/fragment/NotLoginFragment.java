package com.idthk.usercenter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.idthk.usercenter.R;
import com.idthk.usercenter.activity.NewLoginActivity;
import com.idthk.usercenter.dialog.NewLoginDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pengyu.
 * 2018/2/5
 */

public class NotLoginFragment extends BaseFragment {

    private ViewHolder viewHolder;

    public static NotLoginFragment newInstance(String s) {
        NotLoginFragment notLoginFragment = new NotLoginFragment();
        Bundle bundle = new Bundle();
        bundle.putString("not_login", s);
        notLoginFragment.setArguments(bundle);
        return notLoginFragment;
    }
    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.not_login_layout;
    }

    @Override
    protected void initView(View view) {
        viewHolder=new ViewHolder(view);

        String str = getResources().getString(R.string.not_login);
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new UnderlineSpan(), str.length()-2, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //跳转到登录界面
                //new NewLoginDialog(getActivity());
                Intent intent=new Intent(getActivity(), NewLoginActivity.class);
                startActivity(intent);
            }
        };
        spannableString.setSpan(clickableSpan, str.length()-2,str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.top_color));
        spannableString.setSpan(colorSpan, str.length()-2,str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        viewHolder.tv_not_login.setMovementMethod(LinkMovementMethod.getInstance());
        viewHolder.tv_not_login.setText(spannableString);
    }

    @Override
    protected void initData() {

    }

    static class ViewHolder{

        @BindView(R.id.tv_not_login)
        TextView tv_not_login;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
