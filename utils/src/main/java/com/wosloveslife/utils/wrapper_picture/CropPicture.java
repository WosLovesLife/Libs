package com.wosloveslife.utils.wrapper_picture;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;

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
        BitmapFactory.Options options = new BitmapFactory.Options();
        /* 配置器设置为只解析图片的边框大小 */
        options.inJustDecodeBounds = true;
        /* 用位图工厂析出一个只有边框大小的数据的Option对象 */
        BitmapFactory.decodeFile(path, options);
        /* 通过配置器获取到图片的宽高 */
        float srcWidth = options.outWidth;  //图片宽
        float srcheight = options.outHeight;//图片高
        int inSampleSize = 1;   //默认的缩放比例
        /* 如果资源位图的高或者宽大于屏幕 */
        if (srcheight > destHeight || srcWidth > destHeight) {
        /* 根据判断宽高值哪个更大决定将用哪个作为缩放比计算的参照 */
            if (srcWidth > srcheight) {
                inSampleSize = Math.round(srcheight / destHeight);
            } else {
                inSampleSize = Math.round(srcWidth / destWidth);
            }
        }
        /* 重新给option赋值, 这次是为了创建一个真实的位图对象 */
        options = new BitmapFactory.Options();
        /* 根据之前的计算结果,设置图片的缩放比 */
        options.inSampleSize = inSampleSize;
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
        BitmapFactory.Options options = new BitmapFactory.Options();
        /* 配置器设置为只解析图片的边框大小 */
        options.inJustDecodeBounds = true;
        /* 用位图工厂析出一个只有边框大小的数据的Option对象 */
        BitmapFactory.decodeFile(path, options);
        /* 通过配置器获取到图片的宽高 */
        float srcWidth = options.outWidth;  //图片宽
        float srcHeight = options.outHeight;//图片高
        int inSampleSize = 1;   //默认的缩放比例
        /* 如果资源位图的高或者宽大于屏幕 */
        if (srcHeight > destHeight || srcWidth > destHeight) {
        /* 根据判断宽高值哪个更大决定将用哪个作为缩放比计算的参照 */
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / destHeight);
            } else {
                inSampleSize = Math.round(srcWidth / destWidth);
            }
        }
        /* 重新给option赋值, 这次是为了创建一个真实的位图对象 */
        options = new BitmapFactory.Options();
        /* 根据之前的计算结果,设置图片的缩放比 */
        options.inSampleSize = inSampleSize;
        /* 按照改变了缩放比的option获取位图对象 */
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return new BitmapDrawable(a.getResources(), bitmap);
    }
}
