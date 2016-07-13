package com.wosloveslife.baserecyclerview.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);

        onCreateView(itemView);
    }

    public void onCreateView(View view){
    }

    public abstract void onBind(T data);
}