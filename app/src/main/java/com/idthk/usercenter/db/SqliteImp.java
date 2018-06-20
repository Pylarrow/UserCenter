package com.idthk.usercenter.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.idthk.usercenter.utils.Constant;
import com.idthk.usercenter.utils.SharedPreferenceUtils;

public class SqliteImp extends SQLiteOpenHelper {
    private String tableName = "user";
    private String column0 = "have_data";
    private String column1 = "name";
    private String column2 = "number";
    private String column3 = "is_regis";
    private String column4 = "grade";
    private String createString = "create table " + tableName + "(id integer primary key autoincrement,"
            + column0 + " integer,"
            + column1 + " text,"
            + column2 + " text,"
            + column3 + " integer,"
            + column4 + " text)" +
            "";
//    private String createString = "create table " + tableName + "(id integer primary key autoincrement,"
//            + column0 + " integer,"
//            + column1 + " text,"
//            + column2 + " text,"
//            + column3 + " integer)" +
//            "";
    private String createColumn0Index = "create index  user_have_data on user(have_data)";
    private String createColumn1Index = "create index  user_name on user(name)";
    private String createColumn2Index = "create index  user_number on user(number)";
    private String createColumn3Index = "create index  user_isregis on user(is_regis)";
    private String createColumn4Index = "create index  user_grade on user(grade)";

    private Context context;
    private int isRegis = 0;//默认未注册

    public SqliteImp(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // TODO Auto-generated method stub
        arg0.execSQL(createString);
        arg0.execSQL(createColumn0Index);
        arg0.execSQL(createColumn1Index);
        arg0.execSQL(createColumn2Index);
        arg0.execSQL(createColumn3Index);
        arg0.execSQL(createColumn4Index);


        ContentValues contentValues = new ContentValues();
        contentValues.put(column0, 0);
        contentValues.put(column1, "yin");
        contentValues.put(column2, "");
        contentValues.put(column3, 0);
        contentValues.put(column4, "");
        arg0.insert(tableName, null, contentValues);
    }


    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }
}
