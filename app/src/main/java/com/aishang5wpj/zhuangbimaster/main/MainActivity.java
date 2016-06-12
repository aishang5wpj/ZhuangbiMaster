package com.aishang5wpj.zhuangbimaster.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aishang5wpj.zhuangbimaster.R;
import com.aishang5wpj.zhuangbimaster.app.BaseFragment;
import com.aishang5wpj.zhuangbimaster.app.BaseView;
import com.aishang5wpj.zhuangbimaster.app.MvpActivity;
import com.aishang5wpj.zhuangbimaster.bean.WeatherBean;
import com.aishang5wpj.zhuangbimaster.main.fuli.FuliFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpActivity<IMainView, MainPresenterImpl> implements IMainView {

    @BindView(R.id.main_drawer_layout)
    protected DrawerLayout mDrawerLayout;
    @BindView(R.id.main_navigation)
    protected NavigationView mNavigationView;
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    //在NavigationView的header中，BindView似乎找不到
    protected TextView mCityTv, mTempTv, mWeatherTv;

    private String mLastFragment;
    private Map<String, BaseFragment> mFragmentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initViews();

        mFragmentMap = new HashMap<>();
        mFragmentMap.put(getString(R.string.menu_fuli), new FuliFragment());

        mPresenter.loadWeather("上海");
        switch2Fragment(getString(R.string.menu_fuli));
    }

    protected void initViews() {

        ViewGroup headerLayout = (ViewGroup) mNavigationView.getHeaderView(0);
        mCityTv = (TextView) headerLayout.findViewById(R.id.main_header_city_tv);
        mTempTv = (TextView) headerLayout.findViewById(R.id.main_header_temp_tv);
        mWeatherTv = (TextView) headerLayout.findViewById(R.id.main_header_weather_tv);

        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.drawer_open, R.string.drawer_close);
        toggle.syncState();
        mDrawerLayout.addDrawerListener(toggle);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch2Fragment(item.getTitle().toString());
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void switch2Fragment(String title) {

        mToolbar.setTitle(title);

        BaseFragment showFragment = mFragmentMap.get(title);
        if (null == showFragment || showFragment.isVisible()) {
            return;
        }
        String showTag = showFragment.toString();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (ft.isEmpty()) {

            ft.replace(R.id.main_container_layout, showFragment, showTag);

        } else {

            Fragment hideFragment = getSupportFragmentManager().findFragmentByTag(mLastFragment);
            ft
                    .hide(hideFragment)
                    .add(R.id.main_container_layout, showFragment, showTag)
                    .show(showFragment);
        }
        ft.commit();
        mLastFragment = showTag;
    }

    @Override
    protected MainPresenterImpl createPresenter(BaseView view) {
        return new MainPresenterImpl(this);
    }

    @Override
    public void setWeather(WeatherBean weather) {

        mCityTv.setText(weather.retData.city);
        mTempTv.setText(weather.retData.temp + "°");
        mWeatherTv.setText(String.format("%s %s", weather.retData.weather, weather.retData.WS));
    }
}