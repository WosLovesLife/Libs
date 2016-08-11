package com.wosloveslife.libs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YesingBeijing on 2016/8/11.
 */
public class DataUtils {
    /** 模拟数据 */
    public static List<Bitmap> getBitmaps(Context c) {
        List<Bitmap> data = new ArrayList<>();
        data.add(BitmapFactory.decodeResource(c.getResources(), R.drawable.image1));
        data.add(BitmapFactory.decodeResource(c.getResources(), R.drawable.image2));
        data.add(BitmapFactory.decodeResource(c.getResources(), R.drawable.image3));
        data.add(BitmapFactory.decodeResource(c.getResources(), R.drawable.image4));
        data.add(BitmapFactory.decodeResource(c.getResources(), R.drawable.image5));
        data.add(BitmapFactory.decodeResource(c.getResources(), R.drawable.image6));
        data.add(BitmapFactory.decodeResource(c.getResources(), R.drawable.image7));
        return data;
    }
}
