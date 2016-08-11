package com.wosloveslife.libs;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.wosloveslife.simplefloatinglayout.SimpleFloatingLayout;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by YesingBeijing on 2016/8/11.
 */
public class SimpleFloatingLayoutActivity extends AppCompatActivity {

    @BindView(R.id.btn_add)
    Button mBtnAdd;
    @BindView(R.id.et_new_item_width)
    EditText mEtNewItemWidth;
    @BindView(R.id.et_new_item_height)
    EditText mEtNewItemHeight;
    @BindView(R.id.simple_floating_layout)
    SimpleFloatingLayout mSimpleFloatingLayout;
    @BindView(R.id.et_new_item_margin)
    EditText mEtNewItemMargin;

    private int width;
    private int height;
    private int margin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_floating_layout);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        mEtNewItemWidth.addTextChangedListener(new Watcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                width = Integer.parseInt(s.toString().trim());
            }
        });

        mEtNewItemHeight.addTextChangedListener(new Watcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                height = Integer.parseInt(s.toString().trim());
            }
        });

        mEtNewItemMargin.addTextChangedListener(new Watcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                margin = Integer.parseInt(s.toString().trim());
            }
        });
    }

    class Watcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    @OnClick(R.id.btn_add)
    public void onClick(View view) {
        if (width < 0 || height < 0 || margin < 0) {
            Toast.makeText(SimpleFloatingLayoutActivity.this, "请输入尺寸", Toast.LENGTH_SHORT).show();
            return;
        }

        View v = new View(this);
        v.setBackgroundColor(Color.rgb(new Random().nextInt(255)+1,new Random().nextInt(255)+1,new Random().nextInt(255)+1));
        mSimpleFloatingLayout.addView(v);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) v.getLayoutParams();
        params.width = width;
        params.height = height;
        params.setMargins(margin, margin, margin, margin);
        v.setLayoutParams(params);
    }
}
