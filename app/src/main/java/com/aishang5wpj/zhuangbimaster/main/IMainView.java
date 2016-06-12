package com.aishang5wpj.zhuangbimaster.main;

import com.aishang5wpj.zhuangbimaster.app.BaseView;
import com.aishang5wpj.zhuangbimaster.bean.WeatherBean;

/**
 * Created by wpj on 16/6/8下午1:51.
 */
public interface IMainView extends BaseView {

    void setWeather(WeatherBean weather);
}
