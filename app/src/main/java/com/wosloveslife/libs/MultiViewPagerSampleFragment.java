package com.wosloveslife.libs;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by YesingBeijing on 2016/8/16.
 */
public class MultiViewPagerSampleFragment extends Fragment {
    private static final String TAG = "MultiViewFragment";
    private static final String ARGS_DRAWABLE_RES = "drawable_bg";

    public static MultiViewPagerSampleFragment newInstance(@DrawableRes int drawableRes) {

        Bundle args = new Bundle();
        args.putInt(ARGS_DRAWABLE_RES,drawableRes);

        MultiViewPagerSampleFragment fragment = new MultiViewPagerSampleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.w(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_multi_view_pager_sample_layout, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
        int drawableRes = getArguments().getInt(ARGS_DRAWABLE_RES);
        imageView.setImageResource(drawableRes);
        return view;
    }
}
