package com.idthk.usercenter.utils.tools;

import android.app.Activity;
import android.os.Environment;

import java.io.File;


public class FileStorage {
    private File cropIconDir;
    private File iconDir;

    public FileStorage() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File external = Environment.getExternalStorageDirectory();
            String rootDir = "/" + "globe";
            cropIconDir = new File(external, rootDir + "/crop");
            if (!cropIconDir.exists()) {
                cropIconDir.mkdirs();

            }
            iconDir = new File(external, rootDir + "/icon");
            if (!iconDir.exists()) {
                iconDir.mkdirs();
            }
        }
    }

    public FileStorage(Activity activity) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            cropIconDir = new File(activity.getExternalCacheDir(), "/crop");
            if (!cropIconDir.exists()) {
                cropIconDir.mkdirs();
            }
            iconDir = new File(activity.getExternalCacheDir(), "/icon");
            if (!iconDir.exists()) {
                iconDir.mkdirs();
            }
        }
    }

    public File createCropFile() {
        String fileName = "";
        if (cropIconDir != null) {
            fileName = System.currentTimeMillis() + ".png";
        }
        return new File(cropIconDir, fileName);
    }

    public File createIconFile() {
        String fileName = "";
        if (iconDir != null) {
            fileName = System.currentTimeMillis() + ".png";
        }
        return new File(iconDir, fileName);
    }


}
