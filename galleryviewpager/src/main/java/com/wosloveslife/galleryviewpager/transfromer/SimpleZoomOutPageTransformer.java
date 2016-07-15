package com.wosloveslife.galleryviewpager.transfromer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 官方案例中给出的ZoomOutPageTransformer在这里不太适用, 因此这这里自己写了一个
 * Created by WosLovesLife on 2016/7/15.
 */
public class SimpleZoomOutPageTransformer implements ViewPager.PageTransformer {

    public static final float SCALE = 0.75f;

    @Override
    public void transformPage(View page, float position) {

        /* A页的position变化是[ 0, -1] B页的position变化是[ 1 , 0 ] */
        if (position >= -1 && position <= 1) {
            float scale = Math.max(SCALE, 1 - Math.abs(position));
            page.setScaleX(scale);
            page.setScaleY(scale);
        }
        /* 对两边的view缩放到默认大小, 这样在图片一显现的时候就是缩放后的大小. */
        else if (page.getScaleX() != SCALE) {
            page.setScaleX(SCALE);
            page.setScaleY(SCALE);
        }
    }
}
