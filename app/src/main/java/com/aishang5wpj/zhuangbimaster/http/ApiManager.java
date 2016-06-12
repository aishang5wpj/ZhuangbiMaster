package com.aishang5wpj.zhuangbimaster.http;

import com.aishang5wpj.zhuangbimaster.bean.FuliBean;
import com.aishang5wpj.zhuangbimaster.bean.WeatherBean;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wpj on 16/6/8下午2:17.
 */
public interface ApiManager {

    @GET("weatherservice/cityname")
    @Headers({"apikey:40ffbe65699a9fea09022c2cdbcc7f8d"})
    Observable<WeatherBean> getWeather(@Query("cityname") String cityname);

    @GET("data/%E7%A6%8F%E5%88%A9/{pageSize}/{pageNum}")
    Observable<FuliBean> getFuli(@Path("pageSize") int pageSize, @Path("pageNum") int pageNum);
}
