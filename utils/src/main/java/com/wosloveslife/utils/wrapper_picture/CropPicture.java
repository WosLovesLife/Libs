package com.wosloveslife.utils.wrapper_picture;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.Display;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 裁剪压缩图片
 * Created by WosLovesLife on 2016/7/20.
 */
public class CropPicture {
    public static BitmapDrawable getScaledDrawable(Activity a, String path, int targetWidth, int targetHeight) {
        /* 获取屏幕的宽高 */
        Display display = a.getWindowManager().getDefaultDisplay();

        float destWidth = targetWidth;
        if (targetWidth <= 0) {
            destWidth = display.getWidth();   //系统宽
        }
        float destHeight = targetHeight;
        if (targetHeight <= 0) {
            destHeight = display.getHeight(); //系统高
        }

        /* 创建一个位图工厂的配置器对象 */
        BitmapFactory.Options options = getBoundOption();

        /* 用位图工厂析出一个只有边框大小的数据的Option对象 */
        BitmapFactory.decodeFile(path, options);
        /* 通过配置器获取到图片的宽高 */
        int inSampleSize = getSampleSize(destWidth, destHeight, options);

        /* 重新给option赋值, 这次是为了创建一个真实的位图对象 */
        options = getScaledOptions(inSampleSize);

        /* 按照改变了缩放比的option获取位图对象 */
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return new BitmapDrawable(a.getResources(), bitmap);
    }

    public static BitmapDrawable getScaledDrawable(Activity a, String path) {
        /* 获取屏幕的宽高 */
        Display display = a.getWindowManager().getDefaultDisplay();
        float destWidth = display.getWidth();   //系统宽
        float destHeight = display.getHeight(); //系统高

        /* 创建一个位图工厂的配置器对象 */
        BitmapFactory.Options options = getBoundOption();

        /* 用位图工厂析出一个只有边框大小的数据的Option对象 */
        BitmapFactory.decodeFile(path, options);

        /* 通过配置器获取到图片的宽高 */
        int inSampleSize = getSampleSize(destWidth, destHeight, options);

        /* 重新给option赋值, 这次是为了创建一个真实的位图对象 */
        options = getScaledOptions(inSampleSize);

        /* 按照改变了缩放比的option获取位图对象 */
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return new BitmapDrawable(a.getResources(), bitmap);
    }

    public static BitmapDrawable getScaledDrawable(Activity a, @DrawableRes int resId) {
        /* 获取屏幕的宽高 */
        Display display = a.getWindowManager().getDefaultDisplay();
        float destWidth = display.getWidth();   //系统宽
        float destHeight = display.getHeight(); //系统高

        return getScaledDrawable(a, resId, destWidth, destHeight, Bitmap.Config.ARGB_8888);
    }

    public static BitmapDrawable getScaledDrawable(Activity a, @DrawableRes int resId, Bitmap.Config Mode) {
        /* 获取屏幕的宽高 */
        Display display = a.getWindowManager().getDefaultDisplay();
        float destWidth = display.getWidth();   //系统宽
        float destHeight = display.getHeight(); //系统高

        return getScaledDrawable(a, resId, destWidth, destHeight, Mode);
    }

    public static BitmapDrawable getScaledDrawable(Context context, @DrawableRes int resId, float destWidth, float destHeight) {
        return getScaledDrawable(context, resId, destWidth, destHeight, Bitmap.Config.ARGB_8888);
    }

    public static BitmapDrawable getScaledDrawable(Context context, @DrawableRes int resId, float destWidth, float destHeight, Bitmap.Config mode) {
        if (destWidth < 1) destWidth = 1;
        if (destHeight < 1) destHeight = 1;

        /* 获取只截取边缘的Option */
        BitmapFactory.Options options = getBoundOption();

        /* 用位图工厂析出一个只有边框大小的数据的Option对象 */
        BitmapFactory.decodeResource(context.getResources(), resId, options);

        /*获取压缩比例 */
        int inSampleSize = getSampleSize(destWidth, destHeight, options);

        options = getScaledOptions(inSampleSize);
        options.inPreferredConfig = mode;

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId, options);
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public static Bitmap getScaledBitmap(Bitmap origin, int destWidth, int destHeight) {
        if (destWidth < 1) destWidth = 1;
        if (destHeight < 1) destHeight = 1;

        int inSampleSize = getSampleSize(destWidth, destHeight, origin.getWidth(), origin.getHeight());

        Matrix matrix = new Matrix();
        matrix.postScale(inSampleSize, inSampleSize); //长和宽放大缩小的比例
        return Bitmap.createBitmap(origin, 0, 0, origin.getWidth(), origin.getHeight(), matrix, true);
    }

    public static Bitmap getScaledBitmap(Bitmap origin, int destWidth, int destHeight, Bitmap.Config mode) {
        if (destWidth < 1) destWidth = 1;
        if (destHeight < 1) destHeight = 1;

        /* 获取只截取边缘的Option */
        BitmapFactory.Options options = getBoundOption();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        origin.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayInputStream inputStream = new ByteArrayInputStream(b);
        /* 用位图工厂析出一个只有边框大小的数据的Option对象 */
        BitmapFactory.decodeStream(inputStream,null,options);
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*获取压缩比例 */
        int inSampleSize = getSampleSize(destWidth, destHeight, options);

        /* 获取比例更改后的Options, 将图片模式改为指定值 */
        options = getScaledOptions(inSampleSize);
        options.inPreferredConfig = mode;
        inputStream = new ByteArrayInputStream(b);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    @NonNull
    private static BitmapFactory.Options getBoundOption() {
        /* 创建一个位图工厂的配置器对象 */
        BitmapFactory.Options options = new BitmapFactory.Options();
        /* 配置器设置为只解析图片的边框大小 */
        options.inJustDecodeBounds = true;
        return options;
    }

    private static int getSampleSize(float destWidth, float destHeight, BitmapFactory.Options options) {
    /* 通过配置器获取到图片的宽高 */
        float srcWidth = options.outWidth;  //图片宽
        float srcHeight = options.outHeight;//图片高

        return getSampleSize(destWidth, destHeight, srcWidth, srcHeight);
    }

    private static int getSampleSize(float destWidth, float destHeight, float srcWidth, float srcHeight) {
        int inSampleSize = 1;   //默认的缩放比例
        /* 如果资源位图的高或者宽大于屏幕 */
        if (srcHeight > destHeight || srcWidth > destWidth) {
        /* 根据判断宽高值哪个更大决定将用哪个作为缩放比计算的参照 */
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / destHeight);
            } else {
                inSampleSize = Math.round(srcWidth / destWidth);
            }
        }
        return inSampleSize;
    }

    @NonNull
    private static BitmapFactory.Options getScaledOptions(int inSampleSize) {
        /* 重新给option赋值, 这次是为了创建一个真实的位图对象 */
        BitmapFactory.Options options = new BitmapFactory.Options();
        /* 根据之前的计算结果,设置图片的缩放比 */
        options.inSampleSize = inSampleSize;
        /* 按照改变了缩放比的option获取位图对象 */
        return options;
    }

    public static Bitmap ImageCrop(Bitmap bitmap, int destWith, int destHeight) {
        int width = bitmap.getWidth(); // 得到图片的宽，高
        int height = bitmap.getHeight();

        if (width == destWith && height == destHeight) return bitmap;

        int marginLeft = (width - destWith) / 2;
        int marginTop = (height - destHeight) / 2;

        int x;
        int w;
        if (marginLeft > 0) {
            x = marginLeft;
            w = destWith;
        } else {
            x = 0;
            w = width;
        }
        int y;
        int h;
        if (marginTop >0){
            y =marginTop;
            h = destHeight;
        }else {
            y = 0;
            h = height;
        }

        //下面这句是关键
        return Bitmap.createBitmap(bitmap, x, y, w, h, null, false);
    }
}
