package com.wosloveslife.galleryviewpager.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.wosloveslife.galleryviewpager.R;
import com.wosloveslife.galleryviewpager.transfromer.SimpleZoomOutPageTransformer;

/**
 * 使用ViewPager实现3D画廊效果. 左右平移 并动态缩放
 * Created by WosLovesLife on 2016/7/15.
 */
public class GalleryViewPager extends FrameLayout {
    private static final String TAG = "GalleryViewPager";

    private ViewPager mGallery_vp;

    public GalleryViewPager(Context context) {
        this(context, null);
    }

    public GalleryViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GalleryViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_gallery_layout, this);
        mGallery_vp = (ViewPager) view.findViewById(R.id.id_vp_gallery);

        /* 设置幕后page的缓存数目 */
        mGallery_vp.setOffscreenPageLimit(3);
        /* 设置page之间的间距 */
        mGallery_vp.setPageMargin(60);
        /* 设置page切换时的动画 */
        mGallery_vp.setPageTransformer(true, new SimpleZoomOutPageTransformer());
    }

    /** 设置数据Adapter */
    public void setAdapter(PagerAdapter pagerAdapter) {
        mGallery_vp.setAdapter(pagerAdapter);
    }

    /** 设置页面切换的形态 */
    public void setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        mGallery_vp.setPageTransformer(reverseDrawingOrder, transformer);
    }

    /** 将touchEvent分发至viewPager，否则只能滑动中间的一个view对象 */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGallery_vp.dispatchTouchEvent(event);
    }
}
