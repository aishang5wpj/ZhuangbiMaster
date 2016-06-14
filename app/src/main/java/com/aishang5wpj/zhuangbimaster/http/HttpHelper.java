package com.aishang5wpj.zhuangbimaster.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wpj on 16/6/14下午6:06.
 */
public class HttpHelper {

    private static final int TIME_OUT_LIMIT = 30;
    private OkHttpClient mOkHttpClient;
    private RxJavaCallAdapterFactory mAdapterFactory;
    private GsonConverterFactory mGsonConverterFactory;

    private HttpHelper() {

        mGsonConverterFactory = GsonConverterFactory.create();
        mAdapterFactory = RxJavaCallAdapterFactory.create();
        //设置超时时间
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT_LIMIT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT_LIMIT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_LIMIT, TimeUnit.SECONDS)
                .build();
    }

    public static final HttpHelper getInstance() {
        return LazyHolder.INSTANCE;
    }

    public Retrofit getRetrofit(String url) {

        return new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(mAdapterFactory)
                .addConverterFactory(mGsonConverterFactory)
                .client(mOkHttpClient)
                .build();
    }

    private static final class LazyHolder {
        public static final HttpHelper INSTANCE = new HttpHelper();
    }
}
