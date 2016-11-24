package com.my.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by bjzhanxiang on 2016/11/24.
 */
public class DiskCache implements ImageCache{
    static String cacheDir = "/storage/emulated/0/DCIM/";

    @Override
    public Bitmap get(String url) {
        String s=Uri.parse(url).getPathSegments().get(Uri.parse(url).getPathSegments().size()-1);
        if(new File(cacheDir + s).exists())
            return BitmapFactory.decodeFile(cacheDir + s);
        return null;
    }

    @Override
    public void put(String url, Bitmap bmp) {
        String s=Uri.parse(url).getPathSegments().get(Uri.parse(url).getPathSegments().size()-1);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream=new FileOutputStream(cacheDir+s);
            bmp.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
