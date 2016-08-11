package com.wosloveslife.libs.linkage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wosloveslife.baserecyclerview.adapter.BaseRecyclerViewAdapter;
import com.wosloveslife.libs.R;
import com.wosloveslife.libs.adapter.MyRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 联动效果: 向上滑动时 Actionbar随之移动, 向下滑动时 随之显现.
 * 类似CoordinatorLayout的flag = scroll| enter_always
 * Created by WosLovesLife on 2016/7/18.
 */
public class EnterAlwaysLinkageActivity extends AppCompatActivity {
    private static final String TAG = "EnterAlwaysLinkage";
    private int mActionBarHeight;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkage_enter_always);
        initView();
    }

    private void initView() {
        initActionbar();
    }

    private void initActionbar() {
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        mToolbar.setTitle("自定义联动");
        setSupportActionBar(mToolbar);

        /* 获取Toolbar的高度 */
        mToolbar.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mToolbar.getViewTreeObserver().removeOnPreDrawListener(this);

                mActionBarHeight = mToolbar.getHeight();

                initRecyclerView();
                return true;
            }
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.id_rv_header_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add("这是模拟的数据: 第 " + (i + 1) + "条");
        }

        BaseRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(data);

        final ImageView imageView = new ImageView(this);
        /* 设置第一个处于顶端的Header的MarinTop为Toolbar的高度,避免被遮挡
         * 因为条目可能获取不到LayoutParams,所以手动设置一个 */
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, mActionBarHeight, 0, 0);
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
        imageView.setLayoutParams(params);
        imageView.setImageResource(R.drawable.image4);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        adapter.addHeader(imageView);

        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                /* 这句话是因为, 默认dy的值是 向上滑动为正数, 向下滑动是负数 */
                dy = -dy;

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mToolbar.getLayoutParams();

                /* 这一重判断是为了增加效率,避免频繁的调用setMargins() */
                if (params.topMargin >= 0 && dy > 0) return;
                if (params.topMargin <= -mActionBarHeight && dy < 0) return;

                int marginTop = params.topMargin + dy;
                Log.w(TAG, "onTouch: marginTop = " + marginTop);

                /* 这一重判断是为了快速滑动时move的值比较大时将值设为默认大小 */
                if (marginTop > 0) {
                    marginTop = 0;
                } else if (marginTop < -mActionBarHeight) {
                    marginTop = -mActionBarHeight;
                }

                int height = mToolbar.getHeight();
                Log.w(TAG, "onScrolled: mToolbar.getHeight() = " + height);
                params.setMargins(0, marginTop, 0, 0);
                mToolbar.setLayoutParams(params);
            }
        });
    }
}
