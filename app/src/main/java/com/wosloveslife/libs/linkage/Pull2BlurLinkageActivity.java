package com.wosloveslife.libs.linkage;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wosloveslife.baserecyclerview.adapter.BaseRecyclerViewAdapter;
import com.wosloveslife.libs.R;
import com.wosloveslife.libs.adapter.MyRecyclerViewAdapter;
import com.wosloveslife.utils.Dp2Px;
import com.wosloveslife.utils.wrapper_picture.BlurUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 联动效果: 向上滑动时 Actionbar随之移动, 向下滑动时 随之显现.
 * Actionbar全部显现后,继续向下滑动,则头部视图放大并模糊
 * Created by WosLovesLife on 2016/7/18.
 */
public class Pull2BlurLinkageActivity extends AppCompatActivity {
    // Constance
    private static final String TAG = "MainActivity";
    private static final float DEFAULT_VALUE = -1008611.0f;
    private static final float SCALE_DEFAULT = 1.0f;
    private static final float SCALE_TARGET = 1.3f;
    private static final float SCALE_GROWTH_RATE = 0.001f;

    // Variables
    float mY = DEFAULT_VALUE;
    private int mHeaderHeight;
    private float mTargetScale;
    private float mBlurRadius;

    // Widgets
    private AppBarLayout mActionbar;
    private ImageView mImageView;
    private RecyclerView mRecyclerView;

    // Controllers
    private BaseRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    // Data
    private ArrayList<Bitmap> mBlurBgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkage_pull_to_blur);

        initView();
    }

    private void initView() {
        initActionbar();

        initHeaderView();

        initRecyclerView();
    }

    private void initActionbar() {
        mActionbar = (AppBarLayout) findViewById(R.id.id_toolbar);
        mActionbar.setAlpha(0f);
    }

    private void initHeaderView() {
        // header
        mImageView = new ImageView(this);
        /* 设置第一个处于顶端的Header的MarinTop为Toolbar的高度,避免被遮挡
         * 因为条目可能获取不到LayoutParams,所以手动设置一个 */
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Dp2Px.toPX(getApplicationContext(), 180));
        mImageView.setLayoutParams(params);
        mImageView.setImageResource(R.drawable.header_bg);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        /* 获取Toolbar的高度 */
        mImageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mImageView.getViewTreeObserver().removeOnPreDrawListener(this);

                mHeaderHeight = mImageView.getHeight();

                return true;
            }
        });
    }

    /** 获取模拟数据 */
    @NonNull
    private List<String> getData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add("这是模拟的fffffffffddddddddddd数据: 第 " + (i * 100 * 100) + "条");
        }
        return data;
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.id_rv_header_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new MyRecyclerViewAdapter(getData());
        mAdapter.addHeader(mImageView);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                disposeActionbarLinkage(dy);
            }
        });

        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        return disposeHeaderView(event);
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        toDefault();

                }
                return false;
            }
        });
    }

    /** 处理Actionbar的联动效果 */
    private void disposeActionbarLinkage(int dy) {
        if (mHeaderHeight == 0) return;

        float rate = (dy + 0f) / (mHeaderHeight + 0f);

        float currentAlpha = mActionbar.getAlpha();

//        Log.w(TAG, "onTouch: currentAlpha = " + currentAlpha + "; dy = " + dy + "; mHeaderHeight =" + mHeaderHeight + "; rate = " + rate);

        /* 这一重判断是为了增加效率,避免频繁的调用setAlpha() */
        if (currentAlpha >= 1f && rate > 0f
                || mLinearLayoutManager.findFirstVisibleItemPosition() > 0f && rate < 0f
                || currentAlpha <= 0f && rate < 0f) {
            return;
        }

        float targetAlpha = currentAlpha + rate;

        /* 这一重判断是为了快速滑动时move的值比较大时将值设为默认大小 */
        if (targetAlpha > 1f) {
            targetAlpha = 1f;
        } else if (targetAlpha < 0f) {
            targetAlpha = 0f;
        }

        mActionbar.setAlpha(targetAlpha);
    }

    /** 处理HeaderView的联动效果 */
    private boolean disposeHeaderView(MotionEvent event) {
        if (mY == DEFAULT_VALUE) {
            mY = event.getY();
            return false;
        }

        float y = event.getY();
        float dy = y - mY;
        mY = y;

        float currentScale = mImageView.getScaleX();

        /* 这一重判断是为了增加效率,避免频繁的调用setScaleX/Y() */
        if (mLinearLayoutManager.findFirstCompletelyVisibleItemPosition() > 0f && dy > 0f
                || currentScale <= SCALE_DEFAULT && dy < 0f) {
            return false;
        }

        /* 这里也是为了增加效率避免重复处理，但是它需要返回true 不然会出卡顿的现象 */
        if (currentScale >= SCALE_TARGET && dy > 0f) return true;

        mTargetScale = currentScale + dy * SCALE_GROWTH_RATE;

        /* 这一重判断是为了快速滑动时move的值比较大时将值设为默认大小 */
        if (mTargetScale > SCALE_TARGET) {
            mTargetScale = SCALE_TARGET;
        } else if (mTargetScale < SCALE_DEFAULT) {
            mTargetScale = SCALE_DEFAULT;
        }

        mImageView.setScaleX(mTargetScale);
        mImageView.setScaleY(mTargetScale);

        disposeHeaderBlur(dy * SCALE_GROWTH_RATE);
        return true;
    }

    /**
     * 对HeaderView进行模糊处理
     *
     * @param dy
     */
    private void disposeHeaderBlur(float dy) {
        float radius = dy * 25f;
//        Log.w(TAG, "disposeHeaderView: radius = " + radius + "; dy = " + dy);

        /* 为了避免反复的模糊渲染 */
        if (mBlurRadius == radius || radius == 0) return;
        mBlurRadius = radius;

        /* 说明还没有进行过模糊处理, 将原图背景存起来 */
        if (mBlurBgs == null) {
            mBlurBgs = new ArrayList<>();
            Bitmap origin = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
            mBlurBgs.add(origin);
        }

        Bitmap bitmap;
        /* 正数表明当下是向下拉,需要进行模糊处理 */
        if (mBlurRadius > 0) {
            Bitmap origin = mBlurBgs.get(0);
            bitmap = ThumbnailUtils.extractThumbnail(origin, origin.getWidth() / 10, origin.getHeight() / 10);
//            bitmap = Blur.blurBitmap(getApplicationContext(), bitmap, Math.abs(mBlurRadius));
            bitmap = BlurUtils.makePictureBlur(getApplicationContext(), mBlurBgs.get(mBlurBgs.size() - 1), mImageView, mBlurRadius).getBitmap();
            mBlurBgs.add(bitmap);
        }
        /* 负数说明当前是向上拉,则应该逐渐清晰 */
        else {
            if (mBlurBgs.size() > 1) {
                int lastIndex = mBlurBgs.size() - 1;
                bitmap = mBlurBgs.get(lastIndex);
                mBlurBgs.remove(lastIndex);
            } else {
                bitmap = mBlurBgs.get(0);
            }
        }
        mImageView.setImageBitmap(bitmap);
    }

    /** 回到默认值 */
    private void toDefault() {
        // 缩放相关的内容恢复
        if (mTargetScale > SCALE_DEFAULT) {
            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(mImageView, "scaleX", mTargetScale, SCALE_DEFAULT);
            scaleXAnimator.setDuration(220);
            scaleXAnimator.start();
            ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(mImageView, "scaleY", mTargetScale, SCALE_DEFAULT);
            scaleYAnimator.setDuration(220);
            scaleYAnimator.start();
            scaleXAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    toBlurDefault();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    toBlurDefault();
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });

            mTargetScale = SCALE_DEFAULT;
        } else {
            toBlurDefault();
        }
        mY = DEFAULT_VALUE;
    }

    private void toBlurDefault() {
        // 模糊相关的内容恢复
        if (mBlurBgs != null) {
            mImageView.setImageBitmap(mBlurBgs.get(0));
            mBlurBgs.clear();
            mBlurBgs = null;
        }
    }

    /** 如果页面处于 */
    @Override
    public void onBackPressed() {
        if (mLinearLayoutManager.findFirstVisibleItemPosition() != 0) {
            mRecyclerView.smoothScrollToPosition(0);
            return;
        }
        super.onBackPressed();
    }
}