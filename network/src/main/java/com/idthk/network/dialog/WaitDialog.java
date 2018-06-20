package com.idthk.network.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.idthk.network.R;


public class WaitDialog extends Dialog {

    public WaitDialog(Context context) {
        super(context);
    }

    public WaitDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context; // 上下文对象
        private String message;

        public Builder(Context context, String message) {
            this.context = context;
            this.message = message;
        }

        public WaitDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final WaitDialog dialog = new WaitDialog(context,
                    R.style.progress_dialog);
            View layout = inflater.inflate(R.layout.wait_dialog_layout, null);
            TextView tv_ms = (TextView) layout
                    .findViewById(R.id.id_tv_loadingmsg);
            tv_ms.setText(message);
            dialog.addContentView(layout, new LayoutParams((int)
                    context.getResources().getDisplayMetrics().density * 80, (int)
                    context.getResources().getDisplayMetrics().density * 80));
            return dialog;
        }

    }

}
