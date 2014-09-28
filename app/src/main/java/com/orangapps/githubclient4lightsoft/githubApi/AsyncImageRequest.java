package com.orangapps.githubclient4lightsoft.githubApi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by rurik on 28.09.14.
 */
public class AsyncImageRequest extends AsyncTask<String, String, Bitmap> {
    public static final int IMAGE_SIZE = 100;

    protected Bitmap doInBackground(String... args) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            bitmap = Bitmap.createScaledBitmap(bitmap, IMAGE_SIZE, IMAGE_SIZE, false);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}