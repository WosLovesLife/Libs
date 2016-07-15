package com.wosloveslife.loopviewpager.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * LoopViewPager对应的Adapter, 已经对部分基础操作进行了封装,只需要实现简单的方法即可
 * Created by YesingBeijing on 2016/7/12.
 */
public abstract class LoopViewPagerAdapter<T> extends PagerAdapter {

    /** 基础的轮播数 */
    private static final int BASE_COUNT = 10_000_000;

    /** 数据集合 */
    protected List<T> mData;
    /** 轮播总数 = BASE_COUNT * mData.size() */
    protected int mCount;

    public LoopViewPagerAdapter(List<T> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        if (mData == null)
            return 0;

        mCount = BASE_COUNT * mData.size();
        return mCount;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = onCreateView(container, position % mData.size());

        container.addView(view);
        return view;
    }

    /** 实现该方法,返回View对象,即可显示在轮播图中 */
    protected abstract View onCreateView(ViewGroup container, int position);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /** 使用该方法变更轮播图内容 */
    public void setData(List<T> data) {
        mData = data;

        notifyDataSetChanged();
    }
}