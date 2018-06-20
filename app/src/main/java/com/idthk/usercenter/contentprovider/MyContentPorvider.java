package com.idthk.usercenter.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.idthk.usercenter.db.SqliteImp;

/**
 * Created by williamyin on 2017/12/15.
 */

public class MyContentPorvider extends ContentProvider {
    public static final int DIR = 1;
    public static final int ITEM = 2;

    public static String authorityString = "com.idthk.usercenter.contentporvider.provider";
    public static String tableString = "user";
    public SqliteImp sqliteImp;
    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(authorityString, tableString, DIR);
        uriMatcher.addURI(authorityString, tableString + "/#", ITEM);
    }


    public MyContentPorvider() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean onCreate() {
        // TODO Auto-generated method stub
        sqliteImp = new SqliteImp(getContext(), tableString, null, 1);
        return true;
    }

    @Override
    public Cursor query(Uri paramUri, String[] paramArrayOfString1,
                        String paramString1, String[] paramArrayOfString2,
                        String paramString2) {
        // TODO Auto-generated method stub
        Cursor cursor = null;
        switch (uriMatcher.match(paramUri)) {
            case DIR:
                cursor = sqliteImp.getWritableDatabase().query(tableString, paramArrayOfString1, paramString1, paramArrayOfString2, null, null, paramString2);
                break;
            case ITEM:
                long id = ContentUris.parseId(paramUri);
                String whereString = "id=" + id + " and " + paramString1;
                cursor = sqliteImp.getWritableDatabase().query(tableString, paramArrayOfString1, whereString, paramArrayOfString2, null, null, paramString2);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public String getType(Uri paramUri) {
        // TODO Auto-generated method stub
        String typeString = "";
        switch (uriMatcher.match(paramUri)) {
            case DIR:
                typeString = "vnd.android.cursor.dir/vnd.com.idthk.usercenter.contentporvider.provider";
                break;
            case ITEM:
                typeString = "vnd.android.cursor.item/vnd.com.idthk.usercenter.contentporvider.provider";
                break;
            default:
                break;
        }
        return typeString;
    }

    @Override
    public Uri insert(Uri paramUri, ContentValues paramContentValues) {
        // TODO Auto-generated method stub
        Uri uri = null;
        long rowid = sqliteImp.getWritableDatabase().insert(tableString, null, paramContentValues);
        switch (uriMatcher.match(paramUri)) {
            case DIR:
                uri = ContentUris.withAppendedId(paramUri, rowid);
                break;
            case ITEM:
                uri = paramUri;
                break;
            default:
                break;
        }
        return uri;
    }

    @Override
    public int delete(Uri paramUri, String paramString,
                      String[] paramArrayOfString) {
        // TODO Auto-generated method stub
        int result = 0;
        switch (uriMatcher.match(paramUri)) {
            case DIR:
                result = sqliteImp.getWritableDatabase().delete(tableString, paramString, paramArrayOfString);
                break;
            case ITEM:
                long id = ContentUris.parseId(paramUri);
                paramString = "id=" + id + " and " + paramString;
                result = sqliteImp.getWritableDatabase().delete(tableString, paramString, paramArrayOfString);
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    public int update(Uri paramUri, ContentValues paramContentValues,
                      String paramString, String[] paramArrayOfString) {
        // TODO Auto-generated method stub
        int rowid = 0;
        switch (uriMatcher.match(paramUri)) {
            case DIR:
                rowid = sqliteImp.getWritableDatabase().update(tableString, paramContentValues, paramString, paramArrayOfString);
                break;
            case ITEM:
                long id = ContentUris.parseId(paramUri);
                paramString = "id=" + id + " and " + paramString;
                rowid = sqliteImp.getWritableDatabase().update(tableString, paramContentValues, paramString, paramArrayOfString);
                break;
            default:
                break;
        }
        return rowid;
    }
}
