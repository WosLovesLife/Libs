package com.wosloveslife.libs;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wosloveslife.galleryviewpager.adapter.SimplePagerAdapter;
import com.wosloveslife.galleryviewpager.view.GalleryViewPager;

import java.util.List;

/**
 * GalleryViewPager示例
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

        List<Bitmap> data = DataUtils.getBitmaps(getApplicationContext());
        mGalleryViewPager.setAdapter(new SimplePagerAdapter<Bitmap>(data) {
            @Override
            public View onCreateView(ViewGroup container, int position) {
                ImageView imageView = new ImageView(container.getContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY  );
                imageView.setImageBitmap(mData.get(position));
                return imageView;
            }
        });
    }
}
