package com.zcy.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zcy.nicerecyclerview.NiceRecyclerView;
import com.zcy.nicerecyclerview.adapter.CommonAdapter;
import com.zcy.nicerecyclerview.adapter.MultiItemTypeAdapter;
import com.zcy.nicerecyclerview.adapter.ViewHolder;
import com.zcy.nicerecyclerview.swipe.touch.OnItemMoveListener;
import com.zcy.nicerecyclerview.swipe.touch.OnItemStateChangedListener;
import com.zcy.sample.R;
import com.zcy.sample.base.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zcy on 2016/10/5.
 */

public class ListDragSwipeActivity extends BaseActivity {

    private NiceRecyclerView niceRecyclerView;

    private List<String> listData;

    private CommonAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipemenu);
        context = this;
        initView();
        initData();
        setAdapter();
        setListener();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("可拖拽并能够侧滑删除的List列表");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        niceRecyclerView = (NiceRecyclerView) findViewById(R.id.recycler_view);
        niceRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        niceRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        niceRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        niceRecyclerView.setLongPressDragEnabled(true);// 开启拖拽，就这么简单一句话。
        niceRecyclerView.setItemViewSwipeEnabled(true);// 开启滑动删除。
        niceRecyclerView.setOnItemMoveListener(onItemMoveListener);// 监听拖拽，更新UI。
        niceRecyclerView.setOnItemStateChangedListener(mOnItemStateChangedListener);
    }

    /**
     * 当Item移动的时候。
     */
    private OnItemMoveListener onItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(int fromPosition, int toPosition) {
            Collections.swap(listData, fromPosition, toPosition);
            niceRecyclerView.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onItemDismiss(int position) {
            listData.remove(position);
            niceRecyclerView.notifyItemRemoved(position);
            Toast.makeText(context, "当前列表的第" + (position + 1) + "条被删除。", Toast.LENGTH_SHORT).show();
        }

    };

    /**
     * Item的滑动状态发生变化监听。
     */
    private OnItemStateChangedListener mOnItemStateChangedListener = new OnItemStateChangedListener() {
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState == ACTION_STATE_DRAG) {
                getSupportActionBar().setSubtitle("状态：拖拽");
            } else if (actionState == ACTION_STATE_SWIPE) {
                getSupportActionBar().setSubtitle("状态：滑动删除");
            } else if (actionState == ACTION_STATE_IDLE) {
                getSupportActionBar().setSubtitle("状态：手指松开");
            }
        }
    };

    private void initData() {
        listData = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            listData.add("我是第" + (i + 1) + "个栏目");
        }
    }

    private void setAdapter() {
        adapter = new CommonAdapter<String>(context, R.layout.layout_item_allmenu, listData) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_title, s);
                ImageView imageView = holder.getView(R.id.iv_icon);
                Glide.with(context)
                        .load(R.mipmap.icon_moving)
                        .placeholder(R.mipmap.icon_moving)
                        .error(R.mipmap.icon_moving)
                        .crossFade(500)
                        .into(imageView);
            }
        };
        niceRecyclerView.setAdapter(adapter);
    }

    private void setListener() {
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(context, "我当前列表的第" + (position + 1) + "条。", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(context, "别摸我，疼O(∩_∩)O~", Toast.LENGTH_SHORT).show();
                // TODO: 2016/10/8 为了不干扰单击事件，让长按的事件返回true
                return true;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }


}
