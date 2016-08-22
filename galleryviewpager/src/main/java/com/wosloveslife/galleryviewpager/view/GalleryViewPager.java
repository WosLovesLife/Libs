package com.wosloveslife.galleryviewpager.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.wosloveslife.galleryviewpager.transfromer.SimpleZoomOutPageTransformer;

/**
 * 使用ViewPager实现3D画廊效果. 左右平移 并动态缩放
 * Created by WosLovesLife on 2016/7/15.
 */
public class GalleryViewPager extends FrameLayout {
    private static final float DEFAULT_RATIO_WIDTH = 0.2f;
    private static final float DEFAULT_RATIO_HEIGHT = 0.0f;

    private ViewPager mGallery_vp;
    private SimpleZoomOutPageTransformer mTransformer;

    private int mWidth;
    private int mHeight;

    private float mRatioWidth = DEFAULT_RATIO_WIDTH;
    private float mRatioHeight = DEFAULT_RATIO_HEIGHT;


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
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mGallery_vp = new ViewPager(getContext());
        addView(mGallery_vp);

        /* 设置幕后page的缓存数目 */
        mGallery_vp.setOffscreenPageLimit(3);
        /* 不裁剪幕后控件 */
        mGallery_vp.setClipChildren(false);
        /* 禁用滑动到边缘时的边界阴影效果 */
        mGallery_vp.setOverScrollMode(OVER_SCROLL_NEVER);
        /* 设置page切换时的动画 */
        mTransformer = new SimpleZoomOutPageTransformer();
        mGallery_vp.setPageTransformer(true, mTransformer);
    }

    /** 设置数据Adapter */
    public void setAdapter(PagerAdapter pagerAdapter) {
        mGallery_vp.setAdapter(pagerAdapter);
    }

    /** 设置page之间的间距 */
    public void setPageDistance(int distance) {
        mGallery_vp.setPageMargin(distance - 20);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;

        setPageSizeRatio();
    }

    private void setPageSizeRatio() {
        int sideViewWidth = (int) (mRatioWidth * mWidth);
        int SideViewHeight = (int) (mRatioHeight * mHeight);

        LayoutParams params = (LayoutParams) mGallery_vp.getLayoutParams();
        params.width = LayoutParams.MATCH_PARENT;
        params.height = LayoutParams.MATCH_PARENT;
        params.setMargins(sideViewWidth, SideViewHeight, sideViewWidth, SideViewHeight);
        mGallery_vp.setLayoutParams(params);
    }

    /** 将touchEvent分发至viewPager，否则只能滑动中间的一个view对象 */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGallery_vp.dispatchTouchEvent(event);
    }

    /** 设置缩放的比例, 默认是{@link SimpleZoomOutPageTransformer#SCALE} */
    public void setScaleValue(float scaleValue) {
        if (scaleValue > 1f) scaleValue = 1f;
        if (scaleValue < 0f) scaleValue = 0f;

        mTransformer.setScaleValue(scaleValue);
    }

    /**
     * 设置两边的页面的大小(固定值),单位px,
     * 也可以通过{@link GalleryViewPager#setSideViewRatio(float)} 按照比例来设置
     */
    public void setSideViewWidth(int width) {
        mRatioWidth = (width+0.0f) / mWidth;
        setPageSizeRatio();
    }

    /**
     * 设置两边的页面的大小按照比例,范围在0~1之间的float值,默认为{@link GalleryViewPager#DEFAULT_RATIO_WIDTH}
     * 也可以通过{@link GalleryViewPager#setSideViewWidth(int)} 按照固定尺寸来设置
     */
    public void setSideViewRatio(float ratio) {
        mRatioWidth = ratio;
        setPageSizeRatio();
    }

    /**
     * 设置控件顶部和底部的内边距(固定值),单位px,
     * 也可以通过{@link GalleryViewPager#setPaddingTopRatio(float)} 按照比例来设置
     */
    public void setPaddingHeight(int height) {
        mRatioHeight = (height+0.0f) / mHeight;
        setPageSizeRatio();
    }

    /**
     * 设置控件顶部和底部的内边距比例, 默认为{@link GalleryViewPager#DEFAULT_RATIO_HEIGHT}
     * 也可以通过{@link GalleryViewPager#setPaddingHeight(int)} 按照固定尺寸来设置
     */
    public void setPaddingTopRatio(float ratio) {
        mRatioHeight = ratio;
        setPageSizeRatio();
    }
}
