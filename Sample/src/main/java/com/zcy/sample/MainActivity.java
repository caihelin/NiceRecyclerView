package com.zcy.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.zcy.nicerecyclerview.NiceRecyclerView;
import com.zcy.nicerecyclerview.adapter.CommonAdapter;
import com.zcy.nicerecyclerview.adapter.MultiItemTypeAdapter;
import com.zcy.nicerecyclerview.adapter.ViewHolder;
import com.zcy.sample.activity.AllMenuActivity;
import com.zcy.sample.activity.AllViewsActivity;
import com.zcy.sample.activity.DefineActivity;
import com.zcy.sample.activity.DragSwipeFlagsActivity;
import com.zcy.sample.activity.GridDragMenuActivity;
import com.zcy.sample.activity.HeaderAndFooterViewActivity;
import com.zcy.sample.activity.ListDragMenuActivity;
import com.zcy.sample.activity.ListDragSwipeActivity;
import com.zcy.sample.activity.LoadMoreActivity;
import com.zcy.sample.activity.NoDataActivity;
import com.zcy.sample.activity.SwipeRefreshLayoutActivity;
import com.zcy.sample.activity.VerticalMenuActivity;
import com.zcy.sample.activity.ViewPagerMenuActivity;
import com.zcy.sample.activity.ViewTypeMenuActivity;
import com.zcy.sample.base.BaseActivity;
import com.zcy.sample.entity.ViewTypeBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity implements MultiItemTypeAdapter.OnItemClickListener {


    private NiceRecyclerView niceRecyclerView;
    private List<String> titles;
    private List<String> descriptions;
    private CommonAdapter<ViewTypeBean> adapter;
    private List<ViewTypeBean> listData;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setAdapter();
        setListener();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("功能选择");
        setSupportActionBar(toolbar);
        niceRecyclerView = (NiceRecyclerView) findViewById(R.id.recycler_view);
        niceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        niceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // TODO: 2016/10/5 需要添加分割线添加如下代码，本示例中使用了CardView 不需要添加分割线
//        recyclerView.addItemDecoration(new DividerDecoration(R.color.green_pressed, 2));
    }

    private void initData() {
        titles = Arrays.asList(getResources().getStringArray(R.array.main_item));
        descriptions = Arrays.asList(getResources().getStringArray(R.array.main_item_des));
        listData = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            ViewTypeBean bean = new ViewTypeBean();
            bean.setContent(descriptions.get(i));
            bean.setTitle(titles.get(i));
            listData.add(bean);
        }
    }

    private void setAdapter() {
        adapter = new CommonAdapter<ViewTypeBean>(context, R.layout.item_main, listData) {
            @Override
            protected void convert(ViewHolder holder, ViewTypeBean viewTypeBean, int position) {
                holder.setText(R.id.tv_title, viewTypeBean.getTitle());
                holder.setText(R.id.tv_des, viewTypeBean.getContent());
            }
        };
        niceRecyclerView.setAdapter(adapter);
    }

    private void setListener() {
        adapter.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(context, AllMenuActivity.class);
                break;
            case 1:
                intent = new Intent(context, ViewTypeMenuActivity.class);
                break;
            case 2:
                intent = new Intent(context, ViewPagerMenuActivity.class);
                break;
            case 3:
                intent = new Intent(context, ListDragMenuActivity.class);
                break;
            case 4:
                intent = new Intent(context, GridDragMenuActivity.class);
                break;
            case 5:
                intent = new Intent(context, ListDragSwipeActivity.class);
                break;
            case 6:
                intent = new Intent(context, DragSwipeFlagsActivity.class);
                break;
            case 7:
                intent = new Intent(context, VerticalMenuActivity.class);
                break;
            case 8:
                intent = new Intent(context, SwipeRefreshLayoutActivity.class);
                break;
            case 9:
                intent = new Intent(context, DefineActivity.class);
                break;
            case 10:
                intent = new Intent(context, HeaderAndFooterViewActivity.class);
                break;
            case 11:
                intent = new Intent(context, LoadMoreActivity.class);
                break;
            case 12:
                intent = new Intent(context, AllViewsActivity.class);
                break;
            case 13:
                intent = new Intent(context, NoDataActivity.class);
                break;
        }
        if (null != intent) {
            startActivity(intent);
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        Toast.makeText(context, "玩王者吗?带我一个,我负责躺O(∩_∩)O~", Toast.LENGTH_SHORT).show();
        // TODO: 2016/10/8 为了不干扰单击事件，让长按的事件返回true
        return true;
    }
}
