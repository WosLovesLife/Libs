package com.wosloveslife.libs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wosloveslife.loopviewpager.adapter.LoopViewPagerAdapter;
import com.wosloveslife.loopviewpager.view.LoopViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图LoopViewPager的实例类
 * Created by WosLovesLife on 2016/7/13.
 */
public class LoopViewExampleActivity extends AppCompatActivity{
    private LoopViewPager mLoopViewPager;
    private LoopViewPager mLoopViewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_view_example);

        init();
    }

    private void init() {
        /* 模拟数据 */
        List<Bitmap> data = new ArrayList<>();
        data.add(BitmapFactory.decodeResource(getResources(), R.drawable.icon1));
        data.add(BitmapFactory.decodeResource(getResources(), R.drawable.icon2));
        data.add(BitmapFactory.decodeResource(getResources(), R.drawable.icon3));
        data.add(BitmapFactory.decodeResource(getResources(), R.drawable.icon4));
        data.add(BitmapFactory.decodeResource(getResources(), R.drawable.icon5));

        mLoopViewPager = (LoopViewPager) findViewById(R.id.id_vp_loop_view_pager);
        mLoopViewPager.setAdapter(new MyLoopAdapter(data));
        mLoopViewPager.startLoop();

        mLoopViewPager2 = (LoopViewPager) findViewById(R.id.id_vp_loop_view_pager2);
        mLoopViewPager2.setAdapter(new MyLoopAdapter(data));
        mLoopViewPager2.setDuration(2500);
        mLoopViewPager2.startLoop();
    }

    class MyLoopAdapter extends LoopViewPagerAdapter<Bitmap> {

        public MyLoopAdapter(List<Bitmap> data) {
            super(data);
        }

        @Override
        protected View setView(ViewGroup container, int position) {
            final ImageView imageView = new ImageView(container.getContext());
            imageView.setImageBitmap(mData.get(position));
            return imageView;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        mLoopViewPager.stopLoop();
        mLoopViewPager2.stopLoop();
    }
}
