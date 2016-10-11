package com.zcy.sample.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.zcy.nicerecyclerview.NiceRecyclerView;
import com.zcy.sample.R;
import com.zcy.sample.base.BaseActivity;

/**
 * Created by zcy on 2016/10/8.
 */

public class NoDataActivity extends BaseActivity {

    private NiceRecyclerView niceRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipemenu);
        initView();
        addView();
        setListener();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("只有一个TextView的布局");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        niceRecyclerView = (NiceRecyclerView) findViewById(R.id.recycler_view);
    }


    private void addView() {
        View view = LayoutInflater.from(context).inflate(R.layout.view_nomore, null);
        niceRecyclerView.setEmptyView(view);
        niceRecyclerView.showEmpty();
    }

    private void setListener() {
        niceRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        niceRecyclerView.setRefreshing(false);
                    }
                }, 3000);
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
