package com.zcy.sample.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zcy.nicerecyclerview.NiceRecyclerView;
import com.zcy.nicerecyclerview.adapter.CommonAdapter;
import com.zcy.nicerecyclerview.adapter.MultiItemTypeAdapter;
import com.zcy.nicerecyclerview.adapter.ViewHolder;
import com.zcy.nicerecyclerview.swipe.Closeable;
import com.zcy.nicerecyclerview.swipe.OnSwipeMenuItemClickListener;
import com.zcy.nicerecyclerview.swipe.SwipeMenu;
import com.zcy.nicerecyclerview.swipe.SwipeMenuCreator;
import com.zcy.nicerecyclerview.swipe.SwipeMenuItem;
import com.zcy.nicerecyclerview.swipe.SwipeMenuRecyclerView;
import com.zcy.sample.R;
import com.zcy.sample.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zcy on 2016/10/5.
 */

public class HeaderAndFooterViewActivity extends BaseActivity {


    private NiceRecyclerView superRecyclerView;
    private CommonAdapter<String> adapter;
    private List<String> listData;


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
        toolbar.setTitle("添加了HeaderView和FooterView的列表");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        superRecyclerView = (NiceRecyclerView) findViewById(R.id.recycler_view);
        superRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
//        superRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        superRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
//        superRecyclerView.addItemDecoration(new DividerDecoration(
//                mContext.getResources().getColor(R.color.purple_pressed), 2));// 添加分割线。
        // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
        // 设置菜单创建器。
        superRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        superRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
    }

    private void initData() {
        listData = new ArrayList<>();
        for (int i = 0; i < 44; i++) {
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
        // TODO: 2016/10/5  添加头部或者尾部View使用以下方式 
        // 添加头部view1
        View header1 = LayoutInflater.from(context).inflate(R.layout.view_header, null);
        superRecyclerView.addHeaderView(header1);
        // 添加头部view2
        View header2 = LayoutInflater.from(context).inflate(R.layout.view_largeheader, null);
        superRecyclerView.addHeaderView(header2);
        // 添加尾部view
        View footer = LayoutInflater.from(context).inflate(R.layout.view_footer, null);
        superRecyclerView.addLoadMoreView(footer);
        // TODO: 2016/10/5  注意此时添加的adapter为wrapper
        superRecyclerView.setAdapter(adapter);
    }

    private void setListener() {
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(context, "您点击了当前列表的第" + (position + 1) + "个栏目", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(context, "您长按了当前列表的第" + (position + 1) + "个栏目", Toast.LENGTH_SHORT).show();
                // TODO: 2016/10/8 为了不干扰单击事件，让长按的事件返回true
                return true;
            }
        });
    }

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.item_height);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 添加左侧的，如果不添加，则左侧不会出现菜单。
            {
                SwipeMenuItem addItem = new SwipeMenuItem(context)
                        .setBackgroundDrawable(R.drawable.selector_green)// 点击的背景。
                        .setImage(R.mipmap.ic_action_add) // 图标。
                        .setWidth(width) // 宽度。
                        .setHeight(height); // 高度。
                swipeLeftMenu.addMenuItem(addItem); // 添加一个按钮到左侧菜单。

                SwipeMenuItem closeItem = new SwipeMenuItem(context)
                        .setBackgroundDrawable(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_close)
                        .setWidth(width)
                        .setHeight(height);

                swipeLeftMenu.addMenuItem(closeItem); // 添加一个按钮到左侧菜单。
            }

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(context)
                        .setBackgroundDrawable(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_delete)
                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。

                SwipeMenuItem closeItem = new SwipeMenuItem(context)
                        .setBackgroundDrawable(R.drawable.selector_purple)
                        .setImage(R.mipmap.ic_action_close)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(closeItem); // 添加一个按钮到右侧菜单。

                SwipeMenuItem addItem = new SwipeMenuItem(context)
                        .setBackgroundDrawable(R.drawable.selector_green)
                        .setText("添加")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加一个按钮到右侧菜单。
            }
        }
    };

    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        /**
         * Item的菜单被点击的时候调用。
         * @param closeable       closeable. 用来关闭菜单。
         * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
         * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
         * @param direction       如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView#RIGHT_DIRECTION.
         */
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                Toast.makeText(context, "当前列表的第" + (adapterPosition + 1) + "项; " +
                        "右侧第" + (menuPosition + 1) + "个菜单", Toast.LENGTH_SHORT).show();
                if (menuPosition == 0) {// 删除按钮被点击。
                    // 移除数据时需要注意此时的position，由于添加过header，下标应该减去头部view的个数
                    listData.remove(adapterPosition - superRecyclerView.getHeadersCount());
                    // TODO: 2016/10/6 此时移除Item的操作交给SuperRecyclerView来处理
                    superRecyclerView.notifyItemRemoved(adapterPosition);
                }
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
