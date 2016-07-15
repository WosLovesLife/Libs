package com.wosloveslife.libs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wosloveslife.galleryviewpager.adapter.SimplePagerAdapter;
import com.wosloveslife.galleryviewpager.view.GalleryViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WosLovesLife on 2016/7/15.
 */
public class GalleryViewPagerActivity extends AppCompatActivity {

    private GalleryViewPager mGalleryViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_view_pager);

        initView();
    }

    private void initView() {
        mGalleryViewPager = (GalleryViewPager) findViewById(R.id.id_gvp_gallery);

        List<Bitmap> data = getData();
        mGalleryViewPager.setAdapter(new SimplePagerAdapter<Bitmap>(data) {
            @Override
            public View onCreateView(ViewGroup container, int position) {
                ImageView imageView = new ImageView(container.getContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageBitmap(mData.get(position));
                return imageView;
            }
        });
//        mGalleryViewPager.setMainPageSize(80,80);
    }

    private List<Bitmap> getData() {
        List<Bitmap> data = new ArrayList<>();
        data.add(BitmapFactory.decodeResource(getResources(), R.drawable.icon2));
        data.add(BitmapFactory.decodeResource(getResources(), R.drawable.icon3));
        data.add(BitmapFactory.decodeResource(getResources(), R.drawable.icon4));
        data.add(BitmapFactory.decodeResource(getResources(), R.drawable.icon5));
        data.add(BitmapFactory.decodeResource(getResources(), R.drawable.icon2));
        data.add(BitmapFactory.decodeResource(getResources(), R.drawable.icon3));
        data.add(BitmapFactory.decodeResource(getResources(), R.drawable.icon4));
        data.add(BitmapFactory.decodeResource(getResources(), R.drawable.icon5));
        return data;
    }
}
