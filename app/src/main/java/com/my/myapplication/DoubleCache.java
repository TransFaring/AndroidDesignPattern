package com.my.myapplication;

import android.graphics.Bitmap;

/**
 * Created by bjzhanxiang on 2016/11/24.
 */
public class DoubleCache implements ImageCache {
    ImageCache mMemoryCache=new MemoryCache();
    ImageCache mDiskCache=new DiskCache();
    public Bitmap get(String url){
        Bitmap bitmap=mMemoryCache.get(url);
        if (bitmap == null) {
            bitmap=mDiskCache.get(url);
        }
        return bitmap;
    }
    public void put(String url,Bitmap bmp){
        mMemoryCache.put(url,bmp);
        mDiskCache.put(url,bmp);
    }
}
