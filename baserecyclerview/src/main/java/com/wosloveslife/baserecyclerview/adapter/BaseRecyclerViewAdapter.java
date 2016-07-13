package com.wosloveslife.baserecyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.wosloveslife.baserecyclerview.viewHolder.BaseRecyclerViewHolder;

import java.util.List;

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    private static final int TYPE_HEADER = 1;

    protected List<String> mData;
    protected BaseRecyclerViewHolder mHeaderViewHolder;

    public BaseRecyclerViewAdapter(List<String> data) {
        mData = data;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER && mHeaderViewHolder != null) {

            return mHeaderViewHolder;
        } else {
            return onCreateItemViewHolder(parent);
        }
    }

    /** 重新此方法 创建一般条目的ViewHolder时调用 */
    protected abstract BaseRecyclerViewHolder onCreateItemViewHolder(ViewGroup parent);

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        if (mHeaderViewHolder != null) {
            if (position == 0) {
                onBindHeaderViewHolder(holder, position);
            } else {
                onBindItemViewHolder(holder, position - 1);
            }
        } else {
            onBindItemViewHolder(holder, position);
        }
    }

    /** 实现该方法, 绑定Header条目的ViewHolder时调用 */
    protected abstract void onBindHeaderViewHolder(BaseRecyclerViewHolder holder, int position);

    /** 实现该方法, 绑定普通条目的ViewHolder时调用 */
    protected abstract void onBindItemViewHolder(BaseRecyclerViewHolder holder, int position);

    @Override
    public int getItemCount() {
        int count = 0;
        /* 如果有Header count就多+1 */
        if (mHeaderViewHolder != null) {
            count++;
        }

        /* 如果数据不为null count多+ count.size() */
        if (mData != null) {
            count += mData.size();
        }
        return count;
    }

    /** 如果有Header 则当position==0时返回代表Header的type */
    @Override
    public int getItemViewType(int position) {
        if (position == 0 && mHeaderViewHolder != null) {
            return TYPE_HEADER;
        }

        return super.getItemViewType(position);
    }

    /** 实现BaseRecyclerViewHolder 设置头部的View */
    public void setHeader(BaseRecyclerViewHolder headerViewHolder) {
        mHeaderViewHolder = headerViewHolder;
        notifyDataSetChanged();
    }
}