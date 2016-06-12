package com.aishang5wpj.zhuangbimaster.main;

import com.aishang5wpj.zhuangbimaster.app.OnLoadListener;
import com.aishang5wpj.zhuangbimaster.bean.WeatherBean;
import com.aishang5wpj.zhuangbimaster.http.ApiManager;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wpj on 16/6/8下午2:02.
 */
public class MainModelImpl implements IMainModel {

    private ApiManager mApiManager;
    private Subscription mSubscription;

    public MainModelImpl() {
        //在Retrofit 2.0中，Converter 不再包含在package 中了。
        // 你需要自己插入一个Converter 不然的话Retrofit 只能接收字符串结果。
        // 同样的，Retrofit 2.0也不再依赖于Gson 。
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://apis.baidu.com/apistore/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) //Service接口现在可以作为Observable返回了！
                .addConverterFactory(GsonConverterFactory.create())//序列化的工厂
                .build();
        mApiManager = retrofit.create(ApiManager.class);
    }

    @Override
    public void loadWeather(String city, final OnLoadListener<WeatherBean> listener) {

        Observable<WeatherBean> observable = mApiManager.getWeather(city);

        Subscriber<WeatherBean> subscriber = new Subscriber<WeatherBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                if (null != listener) {
                    listener.onLoadFailed(e.toString());
                }
            }

            @Override
            public void onNext(WeatherBean weatherBean) {

                if (null != listener) {
                    listener.onLoadCompleted(weatherBean);
                }
            }
        };

        mSubscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void onDestroy() {

        if (null != mSubscription && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
