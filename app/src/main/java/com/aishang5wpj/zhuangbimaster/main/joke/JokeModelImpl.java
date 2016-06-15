package com.aishang5wpj.zhuangbimaster.main.joke;

import com.aishang5wpj.zhuangbimaster.app.OnLoadListener;
import com.aishang5wpj.zhuangbimaster.bean.JokeBean;
import com.aishang5wpj.zhuangbimaster.cache.CacheManager;
import com.aishang5wpj.zhuangbimaster.cache.NetworkCache;
import com.aishang5wpj.zhuangbimaster.http.ApiManager;
import com.aishang5wpj.zhuangbimaster.http.HttpHelper;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wpj on 16/6/14下午2:36.
 */
public class JokeModelImpl implements IJokeModel {

    private static final int PAGE_SIZE = 10;
    /**
     * 请求参数：
     * 方式一：    maxXhid:已有的最大笑话ID；minXhid:已有的最小笑话ID；size:要获取的笑话的条数
     * 方式二：    size:要获取的笑话的条数；page:分页请求的页数，从0开始
     */
    private static final String API = "http://api.1-blog.com/biz/bizserver/xiaohua/list.do?page=%s&size=%s";

    @Override
    public void loadJokes(final int pageNum, final OnLoadListener<JokeBean> listener) {

        String url = String.format(API, pageNum, PAGE_SIZE);
        NetworkCache<JokeBean> networkCache = new NetworkCache<JokeBean>() {
            @Override
            public Observable<JokeBean> get(String key, Class<JokeBean> cls) {

                Retrofit retrofit = HttpHelper.getInstance().getRetrofit("http://api.1-blog.com/biz/bizserver/");
                ApiManager apiManager = retrofit.create(ApiManager.class);
                Observable<JokeBean> observable = apiManager.getJoke(pageNum, PAGE_SIZE)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io());
                return observable;
            }
        };
        Observable<JokeBean> observable = CacheManager.getInstance().load(url, JokeBean.class, networkCache);
        observable.subscribe(new Observer<JokeBean>() {
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
            public void onNext(JokeBean jokeBean) {

                if (null != listener) {
                    listener.onLoadCompleted(jokeBean);
                }
            }
        });
    }

    @Override
    public int getStartIndex() {
        return 0;
    }
}
