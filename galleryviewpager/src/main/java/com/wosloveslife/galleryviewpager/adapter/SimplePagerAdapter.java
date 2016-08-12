package com.wosloveslife.galleryviewpager.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public abstract class SimplePagerAdapter<T> extends PagerAdapter {
    protected List<T> mData;

    public SimplePagerAdapter(List<T> data) {
        mData = data;

        if (mData == null)
            mData = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        FrameLayout view = new FrameLayout(container.getContext());
        view.addView(onCreateView(container, position));
        container.addView(view);

        return view;
    }

    protected abstract View onCreateView(ViewGroup container, int position);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}