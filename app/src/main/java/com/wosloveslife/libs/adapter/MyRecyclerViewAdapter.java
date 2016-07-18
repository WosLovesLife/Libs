package com.wosloveslife.libs.adapter;

import android.view.ViewGroup;
import android.widget.Button;

import com.wosloveslife.baserecyclerview.adapter.BaseRecyclerViewAdapter;
import com.wosloveslife.baserecyclerview.viewHolder.BaseRecyclerViewHolder;
import com.wosloveslife.libs.viewHolder.HeaderRecyclerViewHolder;

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
    protected void onBindItemViewHolder(BaseRecyclerViewHolder holder, int position) {
        holder.onBind(mData.get(position));
    }
}
