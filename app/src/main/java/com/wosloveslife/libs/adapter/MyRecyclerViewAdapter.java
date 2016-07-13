package com.wosloveslife.libs.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ViewGroup;
import android.widget.Button;

import com.wosloveslife.baserecyclerview.adapter.BaseRecyclerViewAdapter;
import com.wosloveslife.baserecyclerview.viewHolder.BaseRecyclerViewHolder;
import com.wosloveslife.libs.R;
import com.wosloveslife.libs.viewHolder.HeaderRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试用的Adapter 继承了BaseRecyclerViewAdapter, 通过实现几个方法 实现了带Header的列表
 * Created by WosLovesLife on 2016/7/13.
 */
public class MyRecyclerViewAdapter extends BaseRecyclerViewAdapter {

    public MyRecyclerViewAdapter(List<String> data) {
        super(data);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateItemViewHolder(ViewGroup parent) {
        Button textView = new Button(parent.getContext());
        textView.setWidth(parent.getWidth());
        textView.setHeight(100);
        return new HeaderRecyclerViewHolder(textView);
    }

    @Override
    protected void onBindHeaderViewHolder(BaseRecyclerViewHolder holder, int headerIndex) {

        Context context = holder.itemView.getContext();
        switch (headerIndex) {
            case 0:
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cartton_tom_and_jerry);
                holder.onBind(bitmap);
                break;
            case 1:
                /* 模拟数据 */
                List<Bitmap> data = new ArrayList<>();
                data.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon1));
                data.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon2));
                data.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon3));
                data.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon4));
                data.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon5));

                holder.onBind(data);
                break;
        }
    }

    @Override
    protected void onBindItemViewHolder(BaseRecyclerViewHolder holder, int position) {
        holder.onBind(mData.get(position));
    }
}
