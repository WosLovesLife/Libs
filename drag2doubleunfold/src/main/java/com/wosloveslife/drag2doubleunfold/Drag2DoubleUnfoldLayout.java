package com.wosloveslife.drag2doubleunfold;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * 两段式抽屉效果, 默认形态为部分显示, 上拉可完全展开, 下拉则收起.
 * Created by WosLovesLife on 2016/7/26.
 */
public class Drag2DoubleUnfoldLayout extends FrameLayout {
    private static final String TAG = "SlideDialogLayout";

    /** 滑动速度的临界值, 决定是否展开或收起滑动控件 */
    private static final int SLIDE_SENSITIVITY = 200;

    /** 展开形态 部分展开 */
    public static final int FORM_PART = 0;
    /** 展开形态 全部展开 */
    public static final int FORM_COMP = 1;
    /** 展开形态 全部收起 */
    public static final int FORM_FOLD = 2;

    // view
    /** 滑动控件 */
    private ViewGroup mChildLayout;

    // controller
    /** 控件拖动控制器 */
    private ViewDragHelper mDragHelper;
    /** 手势识别控制器 */
    private GestureDetector mGestureDetector;
    /** 对于形态变化的监听器集合 */
    ArrayList<OnFormChangeListener> mOnFormChangeListeners = new ArrayList<>();

    // judgment
    /** 部分展开的临界值 */
    private int mStep1;
    /** 为true时,Form==FORM_FOLD则自动隐藏此控件 */
    private boolean mAutoDismiss;
    /** 为true时,可以从底边边缘滑出窗体 */
    private boolean mEdgeTrackingEnabled;
    /** 判断是否为第一次初始化控件 */
    private boolean mFirstInit = true;


    // variable
    /** 滑动速度 */
    float mVelocityY;
    /** 滑动控件距离本控件Top的距离 */
    int mTop;
    /** 本控件的高度 */
    private int mHeight;
    /** 记录部分展开的位置 */
    private Point mPartPoint = new Point();
    /** 记录全部展开的位置 */
    private Point mCompPoint = new Point();
    /** 记录收起的位置 */
    private Point mFoldPoint = new Point();
    /** 当前的展开形态 */
    private int mCurrentForm = FORM_FOLD;
    /** 中间半透明层, 随着展开幅度透明度加深 */
    private Drawable mBackground;

    public Drag2DoubleUnfoldLayout(Context context) {
        this(context, null);
    }

    public Drag2DoubleUnfoldLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Drag2DoubleUnfoldLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();

        initGesture();

        initDragHelper();
    }

    private void init() {

        /* 设置中间层背景半透明层 让背景颜色随着滑动控件的展开而透明度加深 */
        setBackgroundResource(android.R.color.transparent);
        mBackground = getBackground();
    }

    private void initGesture() {
        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                mVelocityY = velocityY;
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    /** 该控件的子节点必须有且只有一个ViewGroup类型的控件,拿到第一个控件, 如果控件超过一个 就抛出异常 */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mChildLayout = (ViewGroup) getChildAt(0);
    }

    private void initDragHelper() {
        mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

            /* 如果第child是第一个子layout才触发滑动 */
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mChildLayout;
            }

            /** 禁止x轴滑动 */
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return 0;
            }

            /** 规定y轴滑动区域 */
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - topBound;
                return Math.min(Math.max(top, topBound), bottomBound);
            }

            /** 当手指释放时,根据滑动速度和滑动距离进行展开或收起形态 */
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);

                if (releasedChild == mChildLayout) {
                    slide2Spread();
                }
            }

            /** 记录Top值 */
            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
//                Log.w(TAG, "onViewPositionChanged: left = " + left + "; top = " + top + "; dx = " + dx + "; dy = " + dy);
                if (changedView == mChildLayout) {
                    mTop = top;
                    if (mTop != 0) {
                        int alpha = (int) (255 - 255 * ((mTop + 0.0f) / mHeight));
                        Log.w(TAG, "onViewPositionChanged: top = " + top + "; alpha = " + alpha);
                        mBackground.setAlpha(alpha);
                    }
                }

                /* 如果设置收起形态时移出该View,则在满足条件时收起时设置Visible为GONE */
                if (mAutoDismiss && top >= mHeight) {
                    clearAnimation();
                    mChildLayout.clearAnimation();
                    setVisibility(GONE);
                }
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);

                if (mEdgeTrackingEnabled) {
                    mDragHelper.captureChildView(mChildLayout, pointerId);
                }
                Log.w(TAG, "onEdgeDragStarted: ");
            }
        });
    }

    /** 处理滑动相关事件，根据速度和位置展开或收起滑动控件 */
    private void slide2Spread() {
        /* 移动范围在高度一半以上，则可能的形态为完全展开或部分展开 */
        if (mTop < mStep1) {
            if (mVelocityY < -SLIDE_SENSITIVITY) {
                mCurrentForm = FORM_COMP;
            } else if (mVelocityY > SLIDE_SENSITIVITY) {
                mCurrentForm = FORM_PART;
            } else if (mTop < mHeight / 2) {
                mCurrentForm = FORM_COMP;
            } else {
                mCurrentForm = FORM_PART;
            }
        }
        /* 移动范围在高度一半以下，则可能的形态为部分展开或完全收起 */
        else {
            if (mVelocityY < -SLIDE_SENSITIVITY) {
                mCurrentForm = FORM_PART;
            } else if (mVelocityY > SLIDE_SENSITIVITY) {
                mCurrentForm = FORM_FOLD;
            } else if (mTop < (mHeight - mStep1) / 2) {
                mCurrentForm = FORM_PART;
            } else {
                mCurrentForm = FORM_FOLD;
            }
        }

        /* 平滑展开或收起 */
        if (mCurrentForm == FORM_PART) {
            mDragHelper.settleCapturedViewAt(mPartPoint.x, mPartPoint.y);
        } else if (mCurrentForm == FORM_COMP) {
            mDragHelper.settleCapturedViewAt(mCompPoint.x, mCompPoint.y);
        } else {
            mDragHelper.settleCapturedViewAt(mFoldPoint.x, mFoldPoint.y);
        }

        /* 通知监听器 */
        for (OnFormChangeListener l : mOnFormChangeListeners) {
            l.onSlide(mCurrentForm);
        }

        /* 重绘控件位置 平滑移动至指定形态 */
        invalidate();
    }

    /** 将拦截判断交给ViewDragHelper处理 */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    /** 将触摸事件传递给ViewDragHelper以及GestureDetector */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        mDragHelper.processTouchEvent(event);
        return true;
    }

    /** 设置每种形态的默认值 */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.w(TAG, "onSizeChanged: ");

        mHeight = h;
        mStep1 = mHeight - mHeight / 3;

        int left = getLeft();

        mPartPoint.x = left;
        mPartPoint.y = mStep1;

        mCompPoint.x = left;
        mCompPoint.y = getTop();

        mFoldPoint.x = left;
        mFoldPoint.y = mHeight;

        mChildLayout.setVisibility(INVISIBLE);
        mBackground.setAlpha(0);
        scroll(FORM_FOLD);
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        Log.w(TAG, "computeScroll: " );

        if (mDragHelper.continueSettling(true)) {

            invalidate();
        }else if (mDragHelper.getViewDragState() == ViewDragHelper.STATE_IDLE && mFirstInit){
            Log.w(TAG, "computeScroll: mFirstInit = false" );
            mFirstInit = false;
            setBackgroundColor(Color.parseColor("#28292b2b"));
            mBackground.setAlpha(0);
            mChildLayout.setVisibility(VISIBLE);
            controlForm(mCurrentForm);
        }
    }

    // 对外提供控制方法

    /**
     * @param form 改变滑动控件的形态
     * @see #FORM_PART 展开部分
     * @see #FORM_COMP 完全展开
     * @see #FORM_FOLD 完全收起
     */
    public void controlForm(int form) {
        setVisibility(VISIBLE);
        Log.w(TAG, "controlSpread: form = " + form + "; mTop = " + mTop);
        switch (form) {
            case FORM_PART:
                mCurrentForm = FORM_PART;
                break;
            case FORM_COMP:
                mCurrentForm = FORM_COMP;
                break;
            case FORM_FOLD:
                mCurrentForm = FORM_FOLD;
                break;
        }
        if (!mFirstInit) {
            scroll(form);
        }
    }

    private void scroll(int form) {
        switch (form) {
            case FORM_PART:
                mDragHelper.smoothSlideViewTo(mChildLayout, mPartPoint.x, mPartPoint.y);
                break;
            case FORM_COMP:
                mDragHelper.smoothSlideViewTo(mChildLayout, mCompPoint.x, mCompPoint.y);
                break;
            case FORM_FOLD:
                mDragHelper.smoothSlideViewTo(mChildLayout, mFoldPoint.x, mFoldPoint.y);
                break;
        }
        invalidate();
    }

    /** 获取当前形态 */
    public int getForm() {
        return mCurrentForm;
    }

    /** 设置是否启用底边边缘触摸滑出功能 */
    public void setEdgeTrackingEnabled(boolean fromBottom) {
        mEdgeTrackingEnabled = fromBottom;
        if (fromBottom) {
            mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_BOTTOM);
        }
    }

    /** 设置 */
    public boolean getEdgeTrackingEnabled() {
        return mEdgeTrackingEnabled;
    }

    public void setAutoDismissEnable(boolean autoDismiss) {
        mAutoDismiss = autoDismiss;
    }

    public boolean getAutoDismissEnable() {
        return mAutoDismiss;
    }

    // 对外提供事件监听
    public interface OnFormChangeListener {
        void onSlide(int form);
    }

    public void addOnFormChangeListener(OnFormChangeListener onFormChangeListener) {
        if (onFormChangeListener != null)
            mOnFormChangeListeners.add(onFormChangeListener);
    }

    public void removeOnFormChangeListener(OnFormChangeListener onFormChangeListener) {
        mOnFormChangeListeners.remove(onFormChangeListener);
    }
}
