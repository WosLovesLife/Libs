package com.wosloveslife.libs.viewHolder;

import android.view.View;
import android.widget.TextView;

import com.wosloveslife.baserecyclerview.viewHolder.BaseRecyclerViewHolder;

/**
 * 测试用的普通条目的ViewHolder 继承了BaseRecyclerViewHolder, 填充普通条目时调用相关方法
 * Created by WosLovesLife on 2016/7/13.
 */
public class HeaderRecyclerViewHolder extends BaseRecyclerViewHolder<String> {
        protected TextView mTextView;

        public HeaderRecyclerViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }

        public void onBind(String s) {
            mTextView.setText(s);
        }
    }