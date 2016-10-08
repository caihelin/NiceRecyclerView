package com.zcy.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zcy.nicerecyclerview.swipe.SwipeSwitch;
import com.zcy.sample.R;
import com.zcy.sample.base.BaseActivity;

/**
 * Created by zcy on 2016/10/5.
 */

public class DefineActivity extends BaseActivity {


    private TextView mTvContent, mBtnLeft, mBtnRight;

    private SwipeSwitch mSwipeSwitch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("自定义侧滑菜单");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSwipeSwitch = (SwipeSwitch) findViewById(R.id.swipe_layout);
        mTvContent = (TextView) findViewById(R.id.content_view);
        mBtnLeft = (TextView) findViewById(R.id.left_view);
        mBtnRight = (TextView) findViewById(R.id.right_view);

        mBtnLeft.setOnClickListener(按钮监听器);
        mBtnRight.setOnClickListener(按钮监听器);
    }

    private View.OnClickListener 按钮监听器 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.left_view) {
                mSwipeSwitch.smoothCloseMenu();// 关闭菜单。
                Toast.makeText(context, "我是左面的", Toast.LENGTH_SHORT).show();
            } else if (v.getId() == R.id.right_view) {
                mSwipeSwitch.smoothCloseMenu();// 关闭菜单。
                Toast.makeText(context, "我是右面的", Toast.LENGTH_SHORT).show();
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
