package com.wosloveslife.libs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.wosloveslife.galleryviewpager.transfromer.SimpleZoomOutPageTransformer;
import com.wosloveslife.multiviewpager.view.MultiViewPager;

public class MultiViewPagerActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_view_pager);

        MultiViewPager bannerLayout = (MultiViewPager) findViewById(R.id.banner_layout);
        if (bannerLayout == null) return;

        /* 添加Fragment集合 */
//        List<Fragment> fragments = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            fragments.add(new MultiViewPagerSampleFragment());
//        }
//        bannerLayout.addFragment(fragments, getSupportFragmentManager());

        /* 添加View集合 */
//        List<View> views = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            ImageView view = new ImageView(this);
//            view.setImageResource(R.drawable.image1);
//            views.add(view);
//        }
//        bannerLayout.addViews(views);


        /* 添加一个View */
//        ImageView view = new ImageView(this);
//        view.setImageResource(R.drawable.image1);
//        bannerLayout.addView(view);


        /* 通过adapter添加Fragment */
        bannerLayout.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            final int[] drawableRes = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5};

            @Override
            public Fragment getItem(int position) {
                return MultiViewPagerSampleFragment.newInstance(drawableRes[position % drawableRes.length]);
            }

            @Override
            public int getCount() {
                return 15;
            }
        });
        SimpleZoomOutPageTransformer transformer = new SimpleZoomOutPageTransformer();
        bannerLayout.setTransformer(transformer);
        transformer.setScaleValue(0.85f);
        bannerLayout.setPageDistance(10);

        /* 通过adapter添加View */
//        bannerLayout.setAdapter(new PagerAdapter() {
//
//            @Override
//            public int getCount() {
//                return 15;
//            }
//
//            @Override
//            public boolean isViewFromObject(View view, Object object) {
//                return view == object;
//            }
//
//            @Override
//            public Object instantiateItem(ViewGroup container, int position) {
//                ImageView view = new ImageView(container.getContext());
//                view.setImageResource(R.drawable.image1);
//                container.addView(view);
//                return view;
//            }
//        });

        ////////////////////////////VIEW_PAGER///////////////////////////////

//        TestViewPager pager = (TestViewPager) findViewById(R.id.test_view_pager);

//        pager.setAdapter(new PagerAdapter() {
//
//            @Override
//            public int getCount() {
//                return 15;
//            }
//
//            @Override
//            public boolean isViewFromObject(View view, Object object) {
//                boolean b = view == object;
//                Log.w(TAG, "isViewFromObject: view == object = " + (view == object));
//                return b;
//            }
//
//            @Override
//            public Object instantiateItem(ViewGroup container, int position) {
//                ImageView view = new ImageView(container.getContext());
//                view.setImageResource(R.drawable.image1);
//                container.addView(view);
//                return view;
//            }
//
//            @Override
//            public void destroyItem(ViewGroup container, int position, Object object) {
//                container.removeView((View) object);
//            }
//        });

//        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
//            final int[] drawableRes = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5};
//
//            @Override
//            public Fragment getItem(int position) {
//                return MultiViewPagerSampleFragment.newInstance(drawableRes[position % drawableRes.length]);
//            }
//
//            @Override
//            public int getCount() {
//                return 10;
//            }
//
//            @Override
//            public boolean isViewFromObject(View view, Object object) {
//                boolean viewFromObject = super.isViewFromObject(view, object);
//                Log.w(TAG, "isViewFromObject: view == object = " + viewFromObject);
//                return viewFromObject;
//            }
//
//            @Override
//            public void destroyItem(ViewGroup container, int position, Object object) {
////                super.destroyItem(container, position, object);
//            }
//
//            @Override
//            public void destroyItem(View container, int position, Object object) {
////                super.destroyItem(container, position, object);
//            }
//        });
    }
}
