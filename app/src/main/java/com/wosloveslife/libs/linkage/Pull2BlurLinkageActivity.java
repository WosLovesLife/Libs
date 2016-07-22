package com.wosloveslife.libs.linkage;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wosloveslife.baserecyclerview.adapter.BaseRecyclerViewAdapter;
import com.wosloveslife.libs.R;
import com.wosloveslife.libs.adapter.MyRecyclerViewAdapter;
import com.wosloveslife.utils.Dp2Px;
import com.wosloveslife.utils.wrapper_picture.BlurUtils;
import com.wosloveslife.utils.wrapper_picture.CropPicture;

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
    private static final float SCALE_TARGET = 1.4f;
    private static final float SCALE_GROWTH_RATE = 0.001f;

    // Variables
    /** 手指触摸到的Y坐标 */
    float mTouchedY = DEFAULT_VALUE;
    /** HeaderVIew的高度, 联动效果根据该值来作为比例变量的依据 */
    private int mHeaderSize;
    /** 预计缩放比 */
    private float mTargetScale;
    /** 记录预计模糊程度 */
    private float mBlurRadius;
    /** 该变量记录手指移动的区间阶段, 每增加或减少指定的像素位数以上则变更HeaderView的模糊程度 */
    float mTY;
    /** HeaderView的原始图片 */
    private Bitmap mOriginBitmap;
    /** 该图片用于作为裁剪后的模糊模板,是原始图片的压缩版,所有模糊效果根据该图做处理 */
    private Bitmap mForBlur;
    
    // Widgets
    private AppBarLayout mActionbar;
    private ImageView mImageView;
    private RecyclerView mRecyclerView;

    // Controllers
    private BaseRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

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
        mActionbar = (AppBarLayout) findViewById(R.id.id_app_bar_layout);
        mActionbar.setAlpha(0f);
    }

    private void initHeaderView() {
        // header
        mImageView = new ImageView(this);
        mHeaderSize = Dp2Px.toPX(getApplicationContext(), 200);
        /* 设置第一个处于顶端的Header的MarinTop为Toolbar的高度,避免被遮挡
         * 因为条目可能获取不到LayoutParams,所以手动设置一个 */
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mHeaderSize);
        mImageView.setLayoutParams(params);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        setHeaderImage(R.drawable.header_bg1);
    }

    private void setHeaderImage(@DrawableRes int resDrawable) {
        /* 以压缩的方式读入内存 */
        mOriginBitmap = CropPicture.getScaledDrawable(this, resDrawable, mHeaderSize, mHeaderSize).getBitmap();
        /* 将作为模糊参照的图片按原图的一半大小进行处理 */
        mForBlur = CropPicture.getScaledDrawable(this, resDrawable, (int) (mHeaderSize * 0.5), (int) (mHeaderSize * 0.5), Bitmap.Config.RGB_565).getBitmap();
        Log.w(TAG, "setHeaderImage: mOriginBitmap.getByteCount() = " + mOriginBitmap.getByteCount());
        mImageView.setImageBitmap(mOriginBitmap);
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
//        Log.w(TAG, "disposeActionbarLinkage: mHeaderSize" + mHeaderSize);
        if (mHeaderSize == 0) return;

        float rate = (dy + 0f) / (mHeaderSize + 0f);

        if (mLinearLayoutManager.findFirstVisibleItemPosition() > 0f && rate < 0f) return;

        float currentAlpha = mActionbar.getAlpha();

        /* 这一重判断是为了增加效率,避免频繁的调用setAlpha() */
        if (currentAlpha >= 1f && rate > 0f
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
        long millis = System.currentTimeMillis();

        if (mTouchedY == DEFAULT_VALUE) {
            mTouchedY = event.getY();
            return false;
        }

        float y = event.getY();
        float dy = y - mTouchedY;
        mTouchedY = y;

        float currentScale = mImageView.getScaleX();

        /* 这一重判断是为了增加效率,避免频繁的调用setScaleX/Y() */
        if (mLinearLayoutManager.findFirstCompletelyVisibleItemPosition() > 0f && dy > 0f
                || currentScale <= SCALE_DEFAULT && dy < 0f) {
            return false;
        }

        /* 这里也是为了增加效率避免重复处理，但是它需要返回true 不然会出卡顿的现象 */
        if (currentScale >= SCALE_TARGET && dy > 0f) return true;

        /* 处理模糊操作 */
        disposeHeaderBlur(dy);

        mTargetScale = currentScale + (dy * SCALE_GROWTH_RATE);

        /* 这一重判断是为了快速滑动时move的值比较大时将值设为默认大小 */
        if (mTargetScale > SCALE_TARGET) {
            mTargetScale = SCALE_TARGET;
        } else if (mTargetScale < SCALE_DEFAULT) {
            mTargetScale = SCALE_DEFAULT;
        }

        mImageView.setScaleX(mTargetScale);
        mImageView.setScaleY(mTargetScale);

        Log.w(TAG, "disposeHeaderView: disposeHeaderView cost time = " + (System.currentTimeMillis() - millis));
        return true;
    }

    /**
     * 对HeaderView进行模糊处理
     *
     * @param dy y轴移动的距离
     */
    private void disposeHeaderBlur(float dy) {
        if (dy == 0) return;

        mTY += dy;
        /* mBlurRadius同时起到记录手指未抬起前移动的Y轴距离. 当它的值大于0时表示HeaderView完全可见
         * 这里用这个值作为是否要将图片置为原始图片的判断依据 */
        mBlurRadius += (dy * 0.02f);

        /* 为了避免反复的模糊渲染 */
        if (dy > 0 && mTY < 1) return;
        if (dy < 0 && mTY > -1) return;
        mTY = 0;

        Log.w(TAG, "disposeHeaderBlur: mBlurRadius = " + mBlurRadius);

        Bitmap bitmap;
        /* dy作为判断方向的依据(上拉还是下拉),如果上拉并且模糊值<1就设置成原图 */
        if (mBlurRadius < 1 && dy < 0) {
            bitmap = mOriginBitmap;
        }
        /* 表明HeaderView完全可见并且当前正在下拉,需要进行模糊处理 */
        else {
            /* 对原图片进行高斯模糊处理 */
            bitmap = BlurUtils.makePictureBlur(getApplicationContext(), mForBlur, mImageView, 1, mBlurRadius);
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
        mTouchedY = DEFAULT_VALUE;
        mBlurRadius = 0;
    }

    private void toBlurDefault() {
        // 模糊相关的内容恢复
        mImageView.setImageBitmap(mOriginBitmap);
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