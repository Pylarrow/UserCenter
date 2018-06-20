package com.idthk.usercenter.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dgrlucky.log.LogX;
import com.idthk.usercenter.R;
import com.idthk.usercenter.bean.response.LoginRspBean;
import com.idthk.usercenter.bean.response.SendSmsRspBean;
import com.idthk.usercenter.bll.UserBll;
import com.idthk.usercenter.utils.StringUtils;
import com.idthk.usercenter.utils.tools.UtilTools;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.functions.Action2;

/**
 * Created by pengyu.
 * 2018/2/6
 */

public class RegisterDialog extends Dialog implements View.OnClickListener {
    private ViewHolder viewHolder;
    private Context context;
    private UserBll bll = new UserBll();

    private String type="tel";
    private String serial_number="111";
    private String device_number="222";

    private String sendVericode="";
    private int selectedIndex;


    public RegisterDialog(Context context) {
        super(context, R.style.CommonDialog);

        setContentView(R.layout.dialog_register_layout);
        this.context = context;
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = UtilTools.getScreenWidth(context) - 300;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        show();
        initLayout();
        initData();
    }

    private void initData() {
        Resources res = context.getResources();
        String[] ages = res.getStringArray(R.array.age);
        List<String> dataset = new LinkedList<>(Arrays.asList(ages));
        viewHolder.nice_spinner.attachDataSource(dataset);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_send://发送验证码
                sendSms();
                break;
            case R.id.bt_register://注册
                register();
                break;
        }
    }

    /**
     * 发送验证码
     */
    private void sendSms() {
        String phone_email = viewHolder.et_phone_email.getText().toString().trim();
        if (TextUtils.isEmpty(phone_email)) {
            //ToastUtils.showMessage(context, "手机号或邮箱不能为空");
            Toast.makeText(context, R.string.register_username_desc,Toast.LENGTH_SHORT).show();
            return;
        }

        bll.sendSms(context, phone_email, new Action1<SendSmsRspBean>() {
            @Override
            public void call(SendSmsRspBean sendSmsRspBean) {
                LogX.e(sendSmsRspBean);
                sendVericode =sendSmsRspBean.getRandNum();
                //验证码发送成功后
                /** 倒计时180秒，一次1秒 */
                CountDownTimer timer = new CountDownTimer(60 * 1000 * 3, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub
                        viewHolder.bt_send.setText(millisUntilFinished / 1000 + "秒");
                        viewHolder.bt_send.setEnabled(false);
                    }

                    @Override
                    public void onFinish() {
                        viewHolder.bt_send.setText("发送验证码");
                        viewHolder.bt_send.setEnabled(true);
                    }
                }.start();

            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {
                LogX.e(s);
            }
        });

    }


    /**
     * 注册
     */
    private void register() {

        final String phone_email = viewHolder.et_phone_email.getText().toString().trim();
        if (TextUtils.isEmpty(phone_email)) {
            Toast.makeText(context, R.string.register_username_desc,Toast.LENGTH_SHORT).show();
            return;
        }
        String vericode = viewHolder.et_confirm.getText().toString().trim();
        if(TextUtils.isEmpty(vericode)){
            Toast.makeText(context, R.string.register_vericode_notnull_desc,Toast.LENGTH_SHORT).show();
            return;
        }
        //验证验证码是否正确
        if(!vericode.equals(sendVericode)){
            Toast.makeText(context, R.string.register_vericode_desc,Toast.LENGTH_SHORT).show();
            return;
        }
        final String password=viewHolder.et_password.getText().toString().trim();
        if(TextUtils.isEmpty(password)){
            Toast.makeText(context, R.string.register_password_desc,Toast.LENGTH_SHORT).show();
            return;
        }
        String age=viewHolder.nice_spinner.getText().toString().trim();
        if(TextUtils.isEmpty(age)){
            Toast.makeText(context, R.string.register_age_desc,Toast.LENGTH_SHORT).show();
            return;
        }

        String currentTime=System.currentTimeMillis()+"";

        bll.register(context, phone_email, type, password, currentTime,selectedIndex, new Action1() {
            @Override
            public void call(Object o) {
                //注册成功,调登录接口
                bll.login(context, phone_email, type, password, serial_number, device_number, new Action1<LoginRspBean>() {
                    @Override
                    public void call(LoginRspBean loginRspBean) {
                        //登录成功
                    }
                }, new Action2<Integer, String>() {
                    @Override
                    public void call(Integer integer, String s) {

                    }
                });
            }
        }, new Action2<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {
                LogX.e(s);
            }
        });
    }

    private void initLayout() {
        viewHolder = new ViewHolder(this);
        viewHolder.bt_send.setOnClickListener(this);
        viewHolder.bt_register.setOnClickListener(this);
        selectedIndex = viewHolder.nice_spinner.getSelectedIndex()+1;
        viewHolder.nice_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedIndex=position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        setCanceledOnTouchOutside(false);
        setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dismiss();
            }
        });
    }

    static class ViewHolder {

        @BindView(R.id.nice_spinner)
        NiceSpinner nice_spinner;
        @BindView(R.id.et_phone_email)
        EditText et_phone_email;
        @BindView(R.id.et_confirm)
        EditText et_confirm;
        @BindView(R.id.et_password)
        EditText et_password;
        @BindView(R.id.bt_send)
        Button bt_send;
        @BindView(R.id.bt_register)
        Button bt_register;


        ViewHolder(Dialog view) {
            ButterKnife.bind(this, view);
        }
    }
}
