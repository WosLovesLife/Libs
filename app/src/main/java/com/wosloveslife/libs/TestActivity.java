package com.wosloveslife.libs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wosloveslife.galleryviewpager.adapter.SimplePagerAdapter;
import com.wosloveslife.galleryviewpager.view.GalleryViewPager;

import java.util.ArrayList;

/**
 * Created by YesingBeijing on 2016/8/12.
 */
public class TestActivity extends AppCompatActivity {

    private GalleryViewPager mVp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_layout);
        init();
    }

    void init(){

        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            strings.add("heheh"+i);
        }
        mVp = (GalleryViewPager) findViewById(R.id.id_gvp_gallery);
        mVp.setAdapter(new SimplePagerAdapter<String>(strings) {

            private ImageView mWordBackGound;
            private ImageView mStudyClick;
            private TextView mCouseNumber;
            private ImageView mCommentIcon;
            private TextView mWordCommentNum;
            private TextView mWordPagerNum;
            @Override
            protected View onCreateView(ViewGroup container, int position) {

                View view = View.inflate(getApplicationContext(),R.layout.test_layout,null);
                LinearLayout ll_backgound = (LinearLayout) view.findViewById(R.id.ll_backgound);
//                ll_backgound.getBackground().setAlpha(255);
                mWordBackGound = (ImageView) view.findViewById(R.id.backgound_word);
                mStudyClick = (ImageView) view.findViewById(R.id.study_click);
                mCouseNumber = (TextView) view.findViewById(R.id.course_number);
                mCommentIcon = (ImageView) view.findViewById(R.id.word_comment_icon);
                mWordCommentNum = (TextView) view.findViewById(R.id.word_comment_num);

                mWordPagerNum = (TextView) view.findViewById(R.id.word_pager_num);
                mWordPagerNum.setText(""+position);
                mCouseNumber.setText("第"+position+"课");
                mWordCommentNum.setText(position+"");
                return view;
            }
        });

    }
}
