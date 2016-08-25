package com.wosloveslife.libs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.wosloveslife.galleryviewpager.transfromer.SimpleZoomOutPageTransformer;
import com.wosloveslife.multiviewpager.view.MultiViewPager;

public class MultiViewPagerActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_view_pager);

        MultiViewPager bannerLayout = (MultiViewPager) findViewById(R.id.banner_layout);
        if (bannerLayout == null) return;   // 防止黄线..

        final Adapter adapter = new Adapter(getSupportFragmentManager());
        /* 通过adapter添加Fragment */
        bannerLayout.setAdapter(adapter);
        SimpleZoomOutPageTransformer transformer = new SimpleZoomOutPageTransformer();
        bannerLayout.setTransformer(transformer);
        transformer.setScaleValue(0.85f);
        bannerLayout.setPageDistance(10);

        Button addData = (Button) findViewById(R.id.btn_add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addData();
            }
        });
    }

    class Adapter extends FragmentPagerAdapter{
        final int[] drawableRes = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5};
        private int mCount = 15;

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MultiViewPagerSampleFragment.newInstance(drawableRes[position % drawableRes.length]);
        }

        @Override
        public int getCount() {
            return mCount;
        }

        public void addData(){
            mCount ++;
            notifyDataSetChanged();
        }
    };
}
