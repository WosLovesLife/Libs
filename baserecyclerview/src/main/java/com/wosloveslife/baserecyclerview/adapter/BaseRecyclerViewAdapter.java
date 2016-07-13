package com.wosloveslife.baserecyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.wosloveslife.baserecyclerview.viewHolder.BaseRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    private static final String TAG = "BaseRecyclerViewAdapter";
    private static final int TYPE_HEADER = 1;

    protected List<String> mData;
    protected List<BaseRecyclerViewHolder> mHeaderViewHolders;
    private int mTypeCount;

    public BaseRecyclerViewAdapter(){
    }

    public BaseRecyclerViewAdapter(List<String> data) {
        mData = data;

        mHeaderViewHolders = new ArrayList<>();
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mTypeCount > 0 && viewType >= TYPE_HEADER) {
            return mHeaderViewHolders.get(viewType - 1);
        } else {
            return onCreateItemViewHolder(parent);
        }
    }

    /** 重新此方法 创建一般条目的ViewHolder时调用 */
    protected abstract BaseRecyclerViewHolder onCreateItemViewHolder(ViewGroup parent);

    /** 在该方法中对不同的条目类型进行区分, 并算出每种类型对应的数据数据position */
    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        if (mTypeCount > 0) {
            Log.w(TAG, "onBindViewHolder: position = " + position);
            if (position < mTypeCount) {
                onBindHeaderViewHolder(holder, position);
            } else {
                onBindItemViewHolder(holder, position - mTypeCount);
            }
        } else {
            onBindItemViewHolder(holder, position);
        }
    }

    /** 实现该方法, 绑定Header条目的ViewHolder时调用 */
    protected abstract void onBindHeaderViewHolder(BaseRecyclerViewHolder holder, int position);

    /** 实现该方法, 绑定普通条目的ViewHolder时调用 */
    protected abstract void onBindItemViewHolder(BaseRecyclerViewHolder holder, int position);

//    预留方案, 不区分普通条目或是Header条目
//    protected abstract void bind(BaseRecyclerViewHolder holder, int type, int position);

    @Override
    public int getItemCount() {
        mTypeCount = 0;

        int count = 0;
        /* 如果有Header count就多Header的个数 类型多加Header个数 */
        if (mHeaderViewHolders != null) {
            count += mHeaderViewHolders.size();

            mTypeCount += mHeaderViewHolders.size();
        }

        /* 如果数据不为null count多+ count.size() 类型多加1 */
        if (mData != null) {
            count += mData.size();
        }
        return count;
    }

    /** 如果有Header 则当position==0时返回代表Header的type */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderViewHolders.size() > 0 && position < mHeaderViewHolders.size()) {
            return position + 1;
        }

        return super.getItemViewType(position);
    }

    /** 实现BaseRecyclerViewHolder 添加头部的View. 后添加的出现在最上面. */
    public void addHeader(BaseRecyclerViewHolder headerViewHolder) {
        mHeaderViewHolders.add(0, headerViewHolder);

        notifyDataSetChanged();
    }
}