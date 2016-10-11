package com.zcy.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zcy.nicerecyclerview.NiceRecyclerView;
import com.zcy.nicerecyclerview.adapter.CommonAdapter;
import com.zcy.nicerecyclerview.adapter.ItemViewDelegate;
import com.zcy.nicerecyclerview.adapter.MultiItemTypeAdapter;
import com.zcy.nicerecyclerview.adapter.SwipeMenuAdapter;
import com.zcy.nicerecyclerview.adapter.ViewHolder;
import com.zcy.nicerecyclerview.swipe.Closeable;
import com.zcy.nicerecyclerview.swipe.OnSwipeMenuItemClickListener;
import com.zcy.nicerecyclerview.swipe.SwipeMenu;
import com.zcy.nicerecyclerview.swipe.SwipeMenuCreator;
import com.zcy.nicerecyclerview.swipe.SwipeMenuItem;
import com.zcy.nicerecyclerview.swipe.SwipeMenuRecyclerView;
import com.zcy.sample.R;
import com.zcy.sample.base.BaseActivity;
import com.zcy.sample.entity.ViewTypeBean;

import java.util.ArrayList;
import java.util.List;

import static com.zcy.sample.R.layout.layout_item_allmenu;

/**
 * Created by zcy on 2016/10/5.
 */

public class ViewTypeMenuActivity extends BaseActivity {

    private List<ViewTypeBean> listData;
    private CommonAdapter<ViewTypeBean> adapter;
    private NiceRecyclerView niceRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipemenu);
        initView();
        initData();
        setAdapter();
        setListener();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("具有不同样式的菜单的List列表");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        niceRecyclerView = (NiceRecyclerView) findViewById(R.id.recycler_view);
        niceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        superRecyclerView.setHasFixedSize(true);
        niceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        niceRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        niceRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
    }

    private void initData() {
        listData = new ArrayList<>();
        for (int i = 0, j = 0; i < 30; i++, j++) {
            ViewTypeBean viewTypeBean = new ViewTypeBean();
            if (j == 0) {
                viewTypeBean.setViewType(SwipeMenuAdapter.VIEW_TYPE_MENU_NONE);
                viewTypeBean.setContent("我没有菜单");
            } else if (j == 1) {
                viewTypeBean.setViewType(SwipeMenuAdapter.VIEW_TYPE_MENU_SINGLE);
                viewTypeBean.setContent("我有1个菜单");
            } else if (j == 2) {
                viewTypeBean.setViewType(SwipeMenuAdapter.VIEW_TYPE_MENU_MULTI);
                viewTypeBean.setContent("我有2个菜单");
            } else if (j == 3) {
                viewTypeBean.setViewType(SwipeMenuAdapter.VIEW_TYPE_MENU_LEFT);
                viewTypeBean.setContent("我的左边有菜单，右边没有");
                j = -1;
            }
            listData.add(viewTypeBean);
        }
    }

    private void setAdapter() {
        adapter = new CommonAdapter<ViewTypeBean>(context, R.layout.layout_item_allmenu, listData) {
            @Override
            protected void convert(ViewHolder holder, ViewTypeBean viewTypeBean, int position) {
                holder.setText(R.id.tv_title, viewTypeBean.getContent());
                ImageView imageView = holder.getView(R.id.iv_icon);
                Glide.with(context)
                        .load(R.mipmap.icon_moving)
                        .placeholder(R.mipmap.icon_moving)
                        .error(R.mipmap.icon_moving)
                        .crossFade(500)
                        .into(imageView);
            }
        };
        // TODO: 2016/10/5 多个ViewType建议下面这样写（如果你要使用CommonAdapter的话）
        adapter.addItemViewDelegate(SwipeMenuAdapter.VIEW_TYPE_MENU_LEFT, new ItemViewDelegate<ViewTypeBean>() {
            @Override
            public int getItemViewLayoutId() {
                return layout_item_allmenu;
            }

            @Override
            public boolean isForViewType(ViewTypeBean item, int position) {
                return item.getViewType() == SwipeMenuAdapter.VIEW_TYPE_MENU_LEFT;
            }

            @Override
            public void convert(ViewHolder holder, ViewTypeBean viewTypeBean, int position) {
                // TODO: 2016/10/11  如果界面不同请复写该方法
            }
        });
        adapter.addItemViewDelegate(SwipeMenuAdapter.VIEW_TYPE_MENU_MULTI, new ItemViewDelegate<ViewTypeBean>() {
            @Override
            public int getItemViewLayoutId() {
                return layout_item_allmenu;
            }

            @Override
            public boolean isForViewType(ViewTypeBean item, int position) {
                return item.getViewType() == SwipeMenuAdapter.VIEW_TYPE_MENU_MULTI;
            }

            @Override
            public void convert(ViewHolder holder, ViewTypeBean viewTypeBean, int position) {
                // TODO: 2016/10/11  如果界面不同请复写该方法
            }
        });
        adapter.addItemViewDelegate(SwipeMenuAdapter.VIEW_TYPE_MENU_NONE, new ItemViewDelegate<ViewTypeBean>() {
            @Override
            public int getItemViewLayoutId() {
                return layout_item_allmenu;
            }

            @Override
            public boolean isForViewType(ViewTypeBean item, int position) {
                return item.getViewType() == SwipeMenuAdapter.VIEW_TYPE_MENU_NONE;
            }

            @Override
            public void convert(ViewHolder holder, ViewTypeBean viewTypeBean, int position) {
                // TODO: 2016/10/11  如果界面不同请复写该方法
            }
        });
        adapter.addItemViewDelegate(SwipeMenuAdapter.VIEW_TYPE_MENU_SINGLE, new ItemViewDelegate<ViewTypeBean>() {
            @Override
            public int getItemViewLayoutId() {
                return layout_item_allmenu;
            }

            @Override
            public boolean isForViewType(ViewTypeBean item, int position) {
                return item.getViewType() == SwipeMenuAdapter.VIEW_TYPE_MENU_SINGLE;
            }

            @Override
            public void convert(ViewHolder holder, ViewTypeBean viewTypeBean, int position) {
                // TODO: 2016/10/11  如果界面不同请复写该方法
            }
        });
        niceRecyclerView.setAdapter(adapter);
    }

    private void setListener() {
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(context, "我是当前列表的第" + (position + 1) + "个栏目", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(context, "别摸我，疼O(∩_∩)O~", Toast.LENGTH_SHORT).show();
                // TODO: 2016/10/8 为了不干扰单击事件，让长按的事件返回true
                return true;
            }
        });
    }

    /**
     * 菜单创建器。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.item_height);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            if (viewType == SwipeMenuAdapter.VIEW_TYPE_MENU_NONE) {// 根据Adapter的ViewType来决定菜单的样式、颜色等属性、或者是否添加菜单。
                // Do nothing.
            } else if (viewType == SwipeMenuAdapter.VIEW_TYPE_MENU_SINGLE) {// 需要添加单个菜单的Item。
                SwipeMenuItem wechatItem = new SwipeMenuItem(context)
                        .setBackgroundDrawable(R.drawable.selector_purple)
                        .setImage(R.mipmap.ic_action_wechat)
                        .setText("微信")
                        .setWidth(width)
                        .setHeight(height);
                swipeLeftMenu.addMenuItem(wechatItem);
                swipeRightMenu.addMenuItem(wechatItem);

            } else if (viewType == SwipeMenuAdapter.VIEW_TYPE_MENU_MULTI) { // 是需要添加多个菜单的Item。
                SwipeMenuItem wechatItem = new SwipeMenuItem(context)
                        .setBackgroundDrawable(R.drawable.selector_purple)
                        .setImage(R.mipmap.ic_action_wechat)
                        .setText("微信")
                        .setWidth(width)
                        .setHeight(height);

                swipeLeftMenu.addMenuItem(wechatItem);
                swipeRightMenu.addMenuItem(wechatItem);

                SwipeMenuItem addItem = new SwipeMenuItem(context)
                        .setBackgroundDrawable(R.drawable.selector_green)
                        .setImage(R.mipmap.ic_action_add)
                        .setText("添加")
                        .setWidth(width)
                        .setHeight(height);

                swipeLeftMenu.addMenuItem(addItem);
                swipeRightMenu.addMenuItem(addItem);
            } else if (viewType == SwipeMenuAdapter.VIEW_TYPE_MENU_LEFT) {
                SwipeMenuItem wechatItem = new SwipeMenuItem(context)
                        .setBackgroundDrawable(R.drawable.selector_purple)
                        .setImage(R.mipmap.ic_action_wechat)
                        .setText("嘻嘻")
                        .setWidth(width)
                        .setHeight(height);
                swipeLeftMenu.addMenuItem(wechatItem);
            }
        }
    };

    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                Toast.makeText(context, "当前列表的第" + (adapterPosition + 1) + "项; " +
                        "右侧第" + (menuPosition + 1) + "个菜单", Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(context, "当前列表的第" + (adapterPosition + 1) + "项; " +
                        "左侧第" + (menuPosition + 1) + "个菜单", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

}
