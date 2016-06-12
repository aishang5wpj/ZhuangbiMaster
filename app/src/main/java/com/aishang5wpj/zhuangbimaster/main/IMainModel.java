package com.aishang5wpj.zhuangbimaster.main;

import com.aishang5wpj.zhuangbimaster.app.BaseModel;
import com.aishang5wpj.zhuangbimaster.app.OnLoadListener;
import com.aishang5wpj.zhuangbimaster.bean.WeatherBean;

/**
 * Created by wpj on 16/6/8下午2:00.
 */
public interface IMainModel extends BaseModel {

    void loadWeather(String city, OnLoadListener<WeatherBean> listener);
}
