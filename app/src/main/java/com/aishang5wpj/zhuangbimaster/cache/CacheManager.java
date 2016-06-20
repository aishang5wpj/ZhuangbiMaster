package com.aishang5wpj.zhuangbimaster.cache;

import android.util.Log;

import com.aishang5wpj.zhuangbimaster.bean.TextBean;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by wpj on 16/6/13下午5:24.
 */
public class CacheManager {

    private ICache mMemoryCache, mDiskCache;

    private CacheManager() {

        mMemoryCache = new MemoryCache();
        mDiskCache = new DiskCache();
    }

    public static final CacheManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    public <T extends TextBean> Observable<T> load(String key, Class<T> cls, NetworkCache<T> networkCache) {

        Observable observable = Observable.concat(
                loadFromMemory(key, cls),
                loadFromDisk(key, cls),
                loadFromNetwork(key, cls, networkCache))
                .first(new Func1<T, Boolean>() {
                    @Override
                    public Boolean call(T t) {

                        String result = t == null ? "not exist" :
                                t.isExpire() ? "exist but expired" : "exist and not expired";
                        Log.v("cache", "result: " + result);

                        return t != null && !t.isExpire();
                    }
                });
        return observable;
    }

    private <T extends TextBean> Observable<T> loadFromMemory(String key, Class<T> cls) {

        Observable.Transformer<T, T> transformer = log("load from memory: " + key);

        return mMemoryCache
                .get(key, cls)
                .compose(transformer);
    }

    private <T extends TextBean> Observable<T> loadFromDisk(final String key, Class<T> cls) {

        Observable.Transformer<T, T> transformer = log("load from disk: " + key);

        return mDiskCache.get(key, cls)
                .compose(transformer)
                .doOnNext(new Action1<T>() {
                    @Override
                    public void call(T t) {

                        if (null != t) {

                            mMemoryCache.put(key, t);
                        }
                    }
                });
    }

    private <T extends TextBean> Observable<T> loadFromNetwork(final String key, Class<T> cls
            , NetworkCache<T> networkCache) {

        Observable.Transformer<T, T> transformer = log("load from network: " + key);

        return networkCache.get(key, cls)
                .compose(transformer)
                .doOnNext(new Action1<T>() {
                    @Override
                    public void call(T t) {

                        if (null != t) {
                            mDiskCache.put(key, t);
                            mMemoryCache.put(key, t);
                        }
                    }
                });
    }

    private <T extends TextBean> Observable.Transformer<T, T> log(final String msg) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.doOnNext(new Action1<T>() {
                    @Override
                    public void call(T t) {

                        //MemoryCache、DiskCache中已经打印过log了，这里只是为了演示transformer、和compose的使用
                        Log.v("cache", msg);
                    }
                });
            }
        };
    }

    private static final class LazyHolder {
        public static final CacheManager INSTANCE = new CacheManager();
    }
}