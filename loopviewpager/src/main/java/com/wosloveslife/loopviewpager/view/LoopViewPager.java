package com.wosloveslife.loopviewpager.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * 轮播图
 * Created by YesingBeijing on 2016/7/12.
 */
public class LoopViewPager extends ViewPager {
    private static final String TAG = "LoopViewPager";

    private static final int LOOP_STATE_IDLE = 0;
    private static final int LOOP_STATE_PREPARE = 1;
    private static final int LOOP_STATE_LOOPING = 2;

    private int mLooping = LOOP_STATE_IDLE;
    private int mDuration = 5000;

    Handler mHandler;
    PagerAdapter mAdapter;
    private PagerObserver mPagerObserver;

    /** 对于轮播的处理, 跳转到下一页 */
    private void nextPager() {
        Log.w(TAG, "nextPager : " + (getCurrentItem() + 1));
        setCurrentItem(getCurrentItem() + 1, true);

        if (mLooping == LOOP_STATE_LOOPING) {
            loop();
        }
    }

    ////////// 构造-start //////////
    public LoopViewPager(Context context) {
        this(context, null);
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    ////////// 构造-end //////////

    @Override
    public void setAdapter(PagerAdapter adapter) {
        if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(mPagerObserver);
        }

        mAdapter = adapter;

        super.setAdapter(adapter);

        updateItem();

        if (mAdapter != null) {
            if (mPagerObserver == null) {
                mPagerObserver = new PagerObserver();
            }
            mAdapter.registerDataSetObserver(mPagerObserver);
        }
    }

    /** 开始轮播/或准备轮播 */
    public void startLoop() {
        mLooping = LOOP_STATE_PREPARE;

        if (mAdapter == null || mAdapter.getCount() <= 1) return;

        if (mLooping == LOOP_STATE_PREPARE) {
            Log.w("LoopViewPager", "startLoop: mheander == null" + (mHandler == null));
            if (mHandler == null) {
                mHandler = new Handler(Looper.getMainLooper()) {
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
            }

            updateItem();

            loop();

            mLooping = LOOP_STATE_LOOPING;
        }
    }

    /** 停止轮播, 注意!!! 一定要在Activity销毁前调用该方法, 不然会导致内存泄露 */
    public void stopLoop() {
        pause();
        mHandler = null;

        mLooping = LOOP_STATE_IDLE;
    }

    /** 调用该方法轮播 */
    private void loop() {
        if (mHandler != null)
            mHandler.sendEmptyMessageDelayed(0, mDuration);
    }

    private void pause() {
        if (mHandler != null)
            mHandler.removeCallbacksAndMessages(null);
    }

    /** 设置轮播图的间隔时间 */
    public void setDuration(int duration) {
        mDuration = duration;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mLooping == LOOP_STATE_LOOPING) {
            pause();
        }
        return super.onInterceptTouchEvent(ev);
    }

    /** 当手指触摸到该组件时,停止轮播,当手指离开继续轮播 */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mLooping == LOOP_STATE_LOOPING) {
                    loop();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }


    private class PagerObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            Log.w(TAG, "onChanged: ");
            checkLoop();
        }

        @Override
        public void onInvalidated() {
            Log.w(TAG, "onInvalidated: ");
            checkLoop();
        }
    }

    private void checkLoop() {
        if (mLooping != LOOP_STATE_IDLE) {
            if (mAdapter != null && mAdapter.getCount() > 1 && mLooping == LOOP_STATE_PREPARE) {
                updateItem();
                startLoop();
            }

            if (mAdapter == null || mAdapter.getCount() <= 1) {
                stopLoop();
            }
        }
    }

    private void updateItem() {
        setCurrentItem(mAdapter.getCount() / 2 + getCurrentItem());
    }
}
