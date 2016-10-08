package com.zcy.nicerecyclerview.adapter;

/**
 * Created by zcy on 16/10/2.
 */

public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);

}
