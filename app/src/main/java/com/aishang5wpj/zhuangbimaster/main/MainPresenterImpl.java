package com.aishang5wpj.zhuangbimaster.main;

import com.aishang5wpj.zhuangbimaster.app.BasePresenter;
import com.aishang5wpj.zhuangbimaster.app.OnLoadListener;
import com.aishang5wpj.zhuangbimaster.bean.WeatherBean;

/**
 * Created by wpj on 16/6/8下午1:51.
 */
public class MainPresenterImpl extends BasePresenter<IMainView> implements IMainPresenter {

    private IMainModel mMainModel;

    public MainPresenterImpl(IMainView iMainView) {
        super(iMainView);
        mMainModel = new MainModelImpl();
    }

    @Override
    public void loadWeather(String city) {

        mView.showLoading();
        mMainModel.loadWeather(city, new OnLoadListener<WeatherBean>() {
            @Override
            public void onLoadCompleted(WeatherBean weatherBean) {

                mView.setWeather(weatherBean);
            }

            @Override
            public void onLoadFailed(String errMsg) {

                mView.hideLoading();
                mView.showToast(errMsg);
            }
        });
    }

    @Override
    public void onDestory() {
        mMainModel.onDestroy();
        super.onDestory();
    }
}
