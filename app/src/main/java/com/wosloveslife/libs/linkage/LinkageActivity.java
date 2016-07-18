package com.wosloveslife.libs.linkage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.wosloveslife.libs.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 展示各种联动效果
 * Created by WosLovesLife on 2016/7/18.
 */
public class LinkageActivity extends AppCompatActivity {

    @InjectView(R.id.id_toolbar)
    Toolbar mIdToolbar;
    @InjectView(R.id.id_btn_effect_enter_always)
    Button mIdBtnEffectEnterAlways;
    @InjectView(R.id.id_btn_effect_pull_to_blur)
    Button mIdBtnEffectPullToBlur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkage);
        ButterKnife.inject(this);

    }

    @OnClick({R.id.id_btn_effect_enter_always, R.id.id_btn_effect_pull_to_blur})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btn_effect_enter_always:
                enter(EnterAlwaysLinkageActivity.class);
                break;
            case R.id.id_btn_effect_pull_to_blur:
                enter(Pull2BlurLinkageActivity.class);
                break;
        }
    }

    private void enter(Class targetClass) {
        Intent i = new Intent(this, targetClass);
        startActivity(i);
    }
}
