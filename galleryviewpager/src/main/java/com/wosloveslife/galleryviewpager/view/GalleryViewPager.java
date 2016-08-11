package com.wosloveslife.galleryviewpager.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.wosloveslife.galleryviewpager.transfromer.SimpleZoomOutPageTransformer;

/**
 * 使用ViewPager实现3D画廊效果. 左右平移 并动态缩放
 * Created by WosLovesLife on 2016/7/15.
 */
public class GalleryViewPager extends FrameLayout {
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
        /* 不裁剪幕后控件 */
        setClipChildren(false);

        mGallery_vp = new ViewPager(getContext());
        addView(mGallery_vp);

        /* 设置幕后page的缓存数目 */
        mGallery_vp.setOffscreenPageLimit(3);
        /* 不裁剪幕后控件 */
        mGallery_vp.setClipChildren(false);
        /* 禁用滑动到边缘时的边界阴影效果 */
        mGallery_vp.setOverScrollMode(OVER_SCROLL_NEVER);
        /* 设置page切换时的动画 */
        mGallery_vp.setPageTransformer(true, new SimpleZoomOutPageTransformer());
    }

    /** 设置数据Adapter */
    public void setAdapter(PagerAdapter pagerAdapter) {
        mGallery_vp.setAdapter(pagerAdapter);
    }

    /** 设置page之间的间距 */
    public void setPageDistance(int distance) {
        mGallery_vp.setPageMargin(distance);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        LayoutParams params = (LayoutParams) mGallery_vp.getLayoutParams();
        params.width = LayoutParams.MATCH_PARENT;
        params.height = LayoutParams.MATCH_PARENT;
        params.setMargins(w / 5, h / 16, w / 5, h / 16);
        mGallery_vp.setLayoutParams(params);
    }

    /** 将touchEvent分发至viewPager，否则只能滑动中间的一个view对象 */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGallery_vp.dispatchTouchEvent(event);
    }
}
