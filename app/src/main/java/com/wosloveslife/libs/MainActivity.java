package com.wosloveslife.libs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * 实例首页
 * Created by WosLovesLife on 2016/7/13.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mLoopView_Btn;
    private Button mBaseRecyclerView_Btn;
    private Button mGetAndClipPhoto_Btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        mLoopView_Btn = (Button) findViewById(R.id.id_btn_loop_view);
        mLoopView_Btn.setOnClickListener(this);

        mBaseRecyclerView_Btn = (Button) findViewById(R.id.id_btn_header_recycler_view);
        mBaseRecyclerView_Btn.setOnClickListener(this);

        mGetAndClipPhoto_Btn = (Button) findViewById(R.id.id_btn_clip_photo);;
        mGetAndClipPhoto_Btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_loop_view:
                enter(LoopViewExampleActivity.class);
                break;
            case R.id.id_btn_header_recycler_view:
                enter(BaseRecyclerViewExampleActivity.class);
                break;
            case R.id.id_btn_clip_photo:
                enter(GetAndClipPhotoActivity.class);
                break;
        }
    }

    private void enter(Class targetClass) {
        Intent i = new Intent(this, targetClass);
        startActivity(i);
    }
}