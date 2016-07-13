package com.wosloveslife.loopviewpager.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 轮播图
 * Created by YesingBeijing on 2016/7/12.
 */
public class LoopViewPager extends ViewPager {

    private boolean mLooping;
    private int mDuration = 5000;

    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    nextPager();
                    break;
            }
        }
    };

    /** 对于轮播的处理, 跳转到下一页 */
    private void nextPager() {
        setCurrentItem(getCurrentItem() + 1, true);

        if (mLooping) {
            loop();
        }
    }

    ////////// 构造-start //////////
    public LoopViewPager(Context context) {
        this(context, null);
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }
    ////////// 构造-end //////////

    /** 初始化默认设置 */
    private void init() {
    }

    /** 开始轮播 */
    public void startLoop() {
        if (!mLooping) {
            loop();
            mLooping = true;
        }
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);

        setCurrentItem(getCurrentItem() + getAdapter().getCount() / 2);
    }

    /** 调用该方法轮播 */
    private void loop() {
        mHandler.sendEmptyMessageDelayed(0, mDuration);
    }

    /** 停止轮播 */
    public void stopLoop() {
        mHandler.removeCallbacksAndMessages(null);
    }

    /** 设置轮播图的间隔时间 */
    public void setDuration(int duration) {
        mDuration = duration;

        mLooping = false;
    }

    /** 当手指触摸到该组件时,停止轮播,当手指离开继续轮播 */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }
}
