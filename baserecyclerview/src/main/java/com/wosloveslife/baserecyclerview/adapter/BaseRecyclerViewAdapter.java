package com.wosloveslife.baserecyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.wosloveslife.baserecyclerview.viewHolder.BaseRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础Adapter,继承该Adapter, 实现相关方法
 * Created by WosLovesLife on 2016/7/13.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder<T>> {
    private static final String TAG = "BaseRecyclerViewAdapter";
    private static final int TYPE_HEADER = 1;

    protected List<T> mData;
    protected List<BaseRecyclerViewHolder<T>> mHeaderViewHolders;
    private int mTypeCount;

    public BaseRecyclerViewAdapter() {
    }

    public BaseRecyclerViewAdapter(List<T> data) {
        mData = data;

        mHeaderViewHolders = new ArrayList<>();
    }

    @Override
    public BaseRecyclerViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mTypeCount > 0 && viewType >= TYPE_HEADER) {
            return mHeaderViewHolders.get(viewType - 1);
        } else {
            return onCreateItemViewHolder(parent);
        }
    }

    /** 重写此方法 创建一般条目的ViewHolder时调用 */
    protected abstract BaseRecyclerViewHolder<T> onCreateItemViewHolder(ViewGroup parent);

    /** 在该方法中对不同的条目类型进行区分, 并算出每种类型对应的数据数据position */
    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder<T> holder, int position) {
        /* 说明这是一个Header条目 不需要绑定数据 */
        if (position < mTypeCount) return;

        holder.onBind(mData.get(getDataPosition(position)));
    }

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
    public void _addHeader(BaseRecyclerViewHolder<T> headerViewHolder) {
        mHeaderViewHolders.add(0, headerViewHolder);

        notifyItemChanged(0);
    }

    /** 添加头部的View. 后添加的出现在最上面. */
    public void addHeader(View headerView) {
        BaseRecyclerViewHolder<T> viewHolder = new BaseRecyclerViewHolder<T>(headerView) {
            @Override
            public void onBind(T data) {
            }
        };
        mHeaderViewHolders.add(0, viewHolder);

        notifyItemChanged(0);
    }

    public int getDataPosition(int oldPosition) {
        int newPosition;
        /* 有header则position要减去header的个数 */
        if (mTypeCount > 0) {
            newPosition = oldPosition - mTypeCount;
        } else {
            newPosition = oldPosition;
        }
        return newPosition;
    }

    public T getData(int position) {
        if (position >= mData.size()) return null;

        return mData.get(position);
    }
}