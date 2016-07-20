package com.wosloveslife.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.util.Log;

/**
 * 对图片进行模糊处理
 * Created by WosLovesLife on 2016/7/20.
 */
public class Blur {
    private static final String TAG = "Blur";

    public static Bitmap blurBitmap(Context context, Bitmap bitmap, float radius) {
        float targetRadius = radius;
        if (radius > 25f) {
            targetRadius = 25f;
        }
        if (radius < 1f) {
            targetRadius = 1f;
        }

        long timeMillis = System.currentTimeMillis();

        // 用需要创建高斯模糊bitmap创建一个空的bitmap
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        // 初始化Renderscript，这个类提供了RenderScript context，在创建其他RS类之前必须要先创建这个类，他控制RenderScript的初始化，资源管理，释放
        RenderScript rs = RenderScript.create(context);
        // 创建高斯模糊对象
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        // 创建Allocations，此类是将数据传递给RenderScript内核的主要方法，并制定一个后备类型存储给定类型
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
        // 设定模糊度
        blurScript.setRadius(targetRadius);
        // Perform the Renderscript
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);
        // Copy the final bitmap created by the out Allocation to the outBitmap
        allOut.copyTo(outBitmap);
        // recycle the original bitmap
//        bitmap.recycle();
        // After finishing everything, we destroy the Renderscript.
        rs.destroy();

        Log.w(TAG, "blurBitmap: cost time = " + (System.currentTimeMillis() - timeMillis));
        return outBitmap;
    }
}
