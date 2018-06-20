package com.idthk.usercenter.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.idthk.usercenter.R;
import com.idthk.usercenter.utils.tools.UtilTools;


/**
 * Created by williamyin on 2017/12/21.
 */

public class ActionSheet {
    public interface OnActionSheetSelected {
        void onClick(int index);
    }

    public static Dialog showSheetTake(final Context context, final OnActionSheetSelected actionSheetSelected,
                                       DialogInterface.OnCancelListener cancelListener, int layout) {
        final Dialog dlg = new Dialog(context, R.style.ActionSheet);
        final Window window = dlg.getWindow();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(layout, null);
        RelativeLayout update_photo = (RelativeLayout) view.findViewById(R.id.update_photo);
        RelativeLayout from_photo = (RelativeLayout) view.findViewById(R.id.from_photo);
        RelativeLayout cancel = (RelativeLayout) view.findViewById(R.id.cancel);
        update_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionSheetSelected.onClick(0);
                dlg.dismiss();
            }
        });
        from_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionSheetSelected.onClick(1);
                dlg.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionSheetSelected.onClick(5);
                dlg.dismiss();
            }
        });
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        final int cMakeBottom = -1000;
        lp.y = cMakeBottom;
        lp.gravity = Gravity.BOTTOM;
        dlg.onWindowAttributesChanged(lp);
        dlg.setCanceledOnTouchOutside(true);
        if (cancelListener != null)
            dlg.setOnCancelListener(cancelListener);
        dlg.setContentView(view);
        dlg.show();
        lp.width = UtilTools.getScreenWidth(context) - 30;
        window.setAttributes(lp);
//        window.setWindowAnimations(R.style.dialog_Animation);
        return dlg;
    }
}
