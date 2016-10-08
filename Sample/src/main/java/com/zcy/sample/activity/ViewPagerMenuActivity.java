package com.zcy.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.zcy.sample.R;
import com.zcy.sample.adapter.MenuPagerAdapter;
import com.zcy.sample.base.BaseActivity;
import com.zcy.sample.fragment.MenuFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zcy on 2016/10/5.
 */

public class ViewPagerMenuActivity extends BaseActivity {

    private ViewPager viewPager;
    private MenuPagerAdapter menuPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("侧滑菜单嵌套ViewPager");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.btn_one).setOnClickListener(onClickListener);
        findViewById(R.id.btn_two).setOnClickListener(onClickListener);
        findViewById(R.id.btn_three).setOnClickListener(onClickListener);
        viewPager = (ViewPager) findViewById(R.id.view_pager_menu);
        viewPager.addOnPageChangeListener(simpleOnPageChangeListener);
        viewPager.setOffscreenPageLimit(2);

        List<Fragment> fragments = new ArrayList<>(3);
        fragments.add(MenuFragment.newInstance());
        fragments.add(MenuFragment.newInstance());
        fragments.add(MenuFragment.newInstance());
        menuPagerAdapter = new MenuPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(menuPagerAdapter);
        simpleOnPageChangeListener.onPageSelected(0);
    }

    /**
     * Btn点击监听。
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_one) {
                viewPager.setCurrentItem(0, true);
            } else if (v.getId() == R.id.btn_two) {
                viewPager.setCurrentItem(1, true);
            } else if (v.getId() == R.id.btn_three) {
                viewPager.setCurrentItem(2, true);
            }
        }
    };

    private ViewPager.SimpleOnPageChangeListener simpleOnPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            getSupportActionBar().setSubtitle("第" + (position + 1) + "个");
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
