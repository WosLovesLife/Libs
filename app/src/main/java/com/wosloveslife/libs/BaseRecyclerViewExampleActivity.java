package com.wosloveslife.libs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.wosloveslife.baserecyclerview.adapter.BaseRecyclerViewAdapter;
import com.wosloveslife.libs.adapter.MyRecyclerViewAdapter;
import com.wosloveslife.loopviewpager.adapter.LoopViewPagerAdapter;
import com.wosloveslife.loopviewpager.view.LoopViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 带Header功能的RecyclerView.Adapter及ViewHolder的使用
 * 这里结合了LoopViewPager展示了一个顶端是轮播图下面是普通条目的一个列表
 * Created by WosLovesLife on 2016/7/13.
 */
public class BaseRecyclerViewExampleActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LoopViewPager mLoopViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_recycler_view_example);

        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.id_rv_header_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add("这是模拟的数据: 第 " + (i + 1) + "条");
        }

        BaseRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(data);
        mRecyclerView.setAdapter(adapter);

        View view = getLayoutInflater().inflate(R.layout.view_item_recycler_view_header, null, false);
        mLoopViewPager = (LoopViewPager) view.findViewById(R.id.id_vp_loop_view_pager);

        List<Bitmap> simulatedData = getSimulatedData();
        mLoopViewPager.setAdapter(new LoopViewPagerAdapter<Bitmap>(simulatedData) {
            @Override
            protected View onCreateView(ViewGroup container, int position) {
                ImageView imageView = new ImageView(container.getContext());
                imageView.setImageBitmap(mData.get(position));
                return imageView;
            }
        });
        mLoopViewPager.setDuration(1000);
        mLoopViewPager.startLoop();
        adapter.addHeader(view);

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.icon5);
        adapter.addHeader(imageView);


        mRecyclerView.setItemAnimator(new DefaultItemAnimator() {
            @Override
            public boolean animateAdd(RecyclerView.ViewHolder holder) {
                AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1.0f);
                alphaAnimation.setDuration(2000);
                holder.itemView.startAnimation(alphaAnimation);
                return super.animateAdd(holder);
            }
        });

    }

    /** 模拟数据 */
    private List<Bitmap> getSimulatedData(){
        List<Bitmap> data = new ArrayList<>();
        data.add(BitmapFactory.decodeResource(getResources(), R.drawable.icon1));
        data.add(BitmapFactory.decodeResource(getResources(), R.drawable.icon2));
        data.add(BitmapFactory.decodeResource(getResources(), R.drawable.icon3));
        data.add(BitmapFactory.decodeResource(getResources(), R.drawable.icon4));
        data.add(BitmapFactory.decodeResource(getResources(), R.drawable.icon5));
        return data;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoopViewPager.stopLoop();
    }
}
