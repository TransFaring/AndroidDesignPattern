package com.my.myapplication;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by bjzhanxiang on 2016/11/24.
 */
public class ImageLoader {
    private static final ImageLoader instance=new ImageLoader();

    private ImageLoader(){

    }
    public static ImageLoader getInstance(){
        return instance;
    }


    ImageCache mImageCache = new MemoryCache();

    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void setImageCache(ImageCache cache){
        mImageCache=cache;
    }

    public void displayImage(final String url, final ImageView imageView) {
        Bitmap bitmap =mImageCache.get(url);

        if (bitmap != null) {

            imageView.setImageBitmap(bitmap);
            return;
        }
        imageView.setTag(url);
        submitLoadRequest(url,imageView);

    }

    private void submitLoadRequest(final String imageUrl,final ImageView imageView){
        imageView.setTag(imageUrl);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downloadImage(imageUrl);
                if (bitmap == null) {
                    return;
                }
                if (imageView.getTag().equals(imageUrl)) {
                    imageView.setImageBitmap(bitmap);
                }
                mImageCache.put(imageUrl, bitmap);
            }
        });


    }

    public Bitmap downloadImage(String imageurl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageurl);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
