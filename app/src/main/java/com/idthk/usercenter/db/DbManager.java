package com.idthk.usercenter.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by williamyin on 2017/12/15.
 */

public class DbManager {

    private SqliteImp helper;
    private String column0 = "have_data";
    private String tableString = "user";
    private String column1 = "name";
    private String column2 = "number";
    private String column3 = "is_regis";
    private String column4 = "grade";

    public DbManager(Context context) {
        helper = new SqliteImp(context, tableString, null, 1);
    }

    public void update(int hava_data, String name, String number, Integer is_regis, String grade) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(column1, name);
        contentValues.put(column2, number);
        contentValues.put(column3, is_regis);
        contentValues.put(column4, grade);
        db.update(tableString, contentValues, column0 + "=?", new String[]{hava_data + ""});


//        ContentValues values = new ContentValues();
//        values.put("g_name", group.getName());
//        values.put("g_order", group.getOrder());
//        values.put("g_color", group.getColor());
//        values.put("g_encrypt", group.getIsEncrypt());
//        values.put("update_time", UtilTool.date2string(new Date()));
//        db.update("db_group", values, "g_id=?", new String[]{group.getId() + ""});

    }
}
