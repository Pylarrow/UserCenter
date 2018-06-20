/*
package com.idthk.usercenter.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.idthk.usercenter.R;
import com.idthk.usercenter.db.DbManager;
import com.idthk.usercenter.permission.acp.Acp;
import com.idthk.usercenter.permission.acp.AcpListener;
import com.idthk.usercenter.permission.acp.AcpOptions;
import com.idthk.usercenter.utils.tools.UtilTools;

import java.util.List;

public class MainActivity extends FragmentActivity {

    private DbManager dbManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = new DbManager(MainActivity.this);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbManager.update(0, "x", "18925286395", 1,"");
            }
        });
        findViewById(R.id.user_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到用户中心
                Intent i = new Intent(MainActivity.this, UserCenterActivity.class);
                startActivity(i);
            }
        });
        Acp.getInstance(this).request(new AcpOptions.Builder()
                        .setPermissions(UtilTools.PERMISSIONS).build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                    }
                });
    }
}
*/
