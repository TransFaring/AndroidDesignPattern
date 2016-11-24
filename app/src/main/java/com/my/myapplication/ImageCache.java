package com.my.myapplication;

import android.graphics.Bitmap;

/**
 * Created by bjzhanxiang on 2016/11/24.
 */
public interface ImageCache {
    public Bitmap get(String url);
    public void put(String url,Bitmap bmp);
}
