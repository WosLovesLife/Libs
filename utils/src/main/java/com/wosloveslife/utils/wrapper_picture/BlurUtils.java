package com.wosloveslife.utils.wrapper_picture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;

/**
 * Created by WosLovesLife on 2016/6/1.
 */
public class BlurUtils {

    private static final String TAG = "BlurUtils";

    public static BitmapDrawable makePictureBlur(Context context, Bitmap bkg, View view, float radius) {
        long startMs = System.currentTimeMillis();

        int scaleFactor = 1;
        if (radius < 1) {
            radius = 1;
        }

        Bitmap overlay = Bitmap.createBitmap((int) (bkg.getWidth() / scaleFactor),
                (int) (bkg.getHeight() / scaleFactor), Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop() / scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);

        overlay = FastBlur.doBlur(overlay, (int) radius, true);

        Log.w(TAG, "blur: cost time = " + (System.currentTimeMillis() - startMs));
        return new BitmapDrawable(context.getResources(), overlay);
    }

    public static Bitmap makePictureBlur(Context context, Bitmap bkg, View view , float scaleFactor, float radius) {
//        long startMs = System.currentTimeMillis();

        if (scaleFactor <=0){
            scaleFactor = 1;
        }
        if (radius <= 1){
            radius = 1;
        }

        Bitmap overlay = Bitmap.createBitmap((int) (bkg.getWidth() / scaleFactor),
                (int) (bkg.getHeight() / scaleFactor), Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop() / scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);

        overlay = FastBlur.doBlur(overlay, (int) radius, true);

//        Log.w(TAG, " makePictureBlur: blur cost time = " + (System.currentTimeMillis() - startMs));
        return overlay;
    }
}
