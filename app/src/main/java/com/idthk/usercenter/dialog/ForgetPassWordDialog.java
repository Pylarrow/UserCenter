package com.idthk.usercenter.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.idthk.usercenter.R;
import com.idthk.usercenter.utils.tools.UtilTools;

import butterknife.ButterKnife;

/**
 * Created by pengyu.
 * 2018/2/6
 */

public class ForgetPassWordDialog extends Dialog implements View.OnClickListener{
    private ViewHolder viewHolder;
    private Context context;


    public ForgetPassWordDialog(Context context) {
        super(context, R.style.CommonDialog);

        setContentView(R.layout.dialog_forget_password_layout);
        this.context = context;
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = UtilTools.getScreenWidth(context) - 300;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        show();
        initLayout();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    private void initLayout() {
        viewHolder = new ViewHolder(this);
        setCanceledOnTouchOutside(false);
        setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dismiss();
            }
        });
    }

    static class ViewHolder {


        ViewHolder(Dialog view) {
            ButterKnife.bind(this, view);
        }
    }
}
