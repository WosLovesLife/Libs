package com.wosloveslife.libs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.wosloveslife.libs.linkage.LinkageActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 实例首页
 * Created by WosLovesLife on 2016/7/13.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.id_btn_loop_view)
    Button mIdBtnLoopView;
    @InjectView(R.id.id_btn_header_recycler_view)
    Button mIdBtnHeaderRecyclerView;
    @InjectView(R.id.id_btn_clip_photo)
    Button mIdBtnClipPhoto;
    @InjectView(R.id.id_btn_gallery)
    Button mIdBtnGallery;
    @InjectView(R.id.id_btn_linkage)
    Button mIdBtnLinkage;
    @InjectView(R.id.id_btn_drag_unfold)
    Button mIdBtnDrag2DoubleUnfold;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.id_btn_loop_view, R.id.id_btn_header_recycler_view,
            R.id.id_btn_clip_photo, R.id.id_btn_gallery, R.id.id_btn_linkage,
            R.id.id_btn_drag_unfold})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btn_loop_view:
                enter(LoopViewExampleActivity.class);
                break;
            case R.id.id_btn_header_recycler_view:
                enter(BaseRecyclerViewExampleActivity.class);
                break;
            case R.id.id_btn_clip_photo:
                enter(GetAndClipPhotoActivity.class);
                break;
            case R.id.id_btn_gallery:
                enter(GalleryViewPagerActivity.class);
                break;
            case R.id.id_btn_linkage:
                enter(LinkageActivity.class);
                break;
            case R.id.id_btn_drag_unfold:
                enter(Drag2DoubleUnfoldActivity.class);
                break;
        }
    }

    private void enter(Class targetClass) {
        Intent i = new Intent(this, targetClass);
        startActivity(i);
    }
}