package com.wosloveslife.galleryviewpager.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.wosloveslife.galleryviewpager.R;
import com.wosloveslife.galleryviewpager.transfromer.ZoomOutPageTransformer;

/**
 * 实现了基本轮播墙的功能
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

        // 1.设置幕后item的缓存数目
        mGallery_vp.setOffscreenPageLimit(3);
        // 2.设置页与页之间的间距
        mGallery_vp.setPageMargin(10);

        mGallery_vp.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    /** 设置数据Adapter */
    public void setAdapter(PagerAdapter pagerAdapter) {
        mGallery_vp.setAdapter(pagerAdapter);
    }

    /** 设置页面切换的形态 */
    public void setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        mGallery_vp.setPageTransformer(reverseDrawingOrder, transformer);
    }

    /** 设置主要区域的尺寸 */
    public void setMainPageSize(int width, int height) {
        int selfWidth = getMeasuredWidth();
        int selfHeight = getMeasuredHeight();

        Log.w(TAG, "setMainPageSize: selfWidth = " + selfWidth + "; selfHeight = " + selfHeight);

        if (width > selfWidth) width = selfWidth;
        if (height > selfHeight) height = selfHeight;

        int left = selfWidth - width;
        int right = selfHeight - height;

        FrameLayout.LayoutParams params = (LayoutParams) getLayoutParams();
        params.setMargins(left, 0, right, 0);
        mGallery_vp.setLayoutParams(params);
    }

    // 3.将父类的touch事件分发至viewPgaer，否则只能滑动中间的一个view对象
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGallery_vp.dispatchTouchEvent(event);
    }
}
