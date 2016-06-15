package com.aishang5wpj.zhuangbimaster.main.fuli;

import com.aishang5wpj.zhuangbimaster.app.OnLoadListener;
import com.aishang5wpj.zhuangbimaster.bean.FuliBean;
import com.aishang5wpj.zhuangbimaster.bean.ImageBean;
import com.aishang5wpj.zhuangbimaster.cache.CacheManager;
import com.aishang5wpj.zhuangbimaster.cache.NetworkCache;
import com.aishang5wpj.zhuangbimaster.http.ApiManager;
import com.aishang5wpj.zhuangbimaster.http.HttpHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wpj on 16/6/12下午3:51.
 * http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1
 */
public class FuliModelImpl implements IFuliModel {

    private static final int PAGE_SIZE = 15;
    /**
     * 含有%，不能直接String.format，会报错
     */
    private static final String API = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/%s/%s";
    private ApiManager mApiManager;
    private Subscription mSubscription;

    public FuliModelImpl() {

        Retrofit retrofit = HttpHelper.getInstance().getRetrofit("http://gank.io/api/");
        mApiManager = retrofit.create(ApiManager.class);
    }

    @Override
    public void load(final int pageNum, final OnLoadListener<List<ImageBean>> onLoadListener) {

        final String url = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/" + PAGE_SIZE + "/" + pageNum;

        NetworkCache<FuliBean> networkCache = new NetworkCache<FuliBean>() {
            @Override
            public Observable<FuliBean> get(String key, Class<FuliBean> cls) {

                return mApiManager
                        .getFuli(PAGE_SIZE, pageNum)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io());
            }
        };
        Observable<FuliBean> observable = CacheManager.getInstance().load(url, FuliBean.class, networkCache);
        mSubscription = observable.map(new Func1<FuliBean, List<ImageBean>>() {

            @Override
            public List<ImageBean> call(FuliBean fuliBean) {

                List<FuliBean.Fuli> fuliList = fuliBean.results;
                List<ImageBean> imageList = new ArrayList<>(fuliList.size());
                for (FuliBean.Fuli fuli : fuliList) {
                    ImageBean imageBean = new ImageBean();
                    imageBean.desc = fuli.desc;
                    imageBean.url = fuli.url;
                    imageList.add(imageBean);
                }
                return imageList;
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<ImageBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        if (null != onLoadListener) {
                            onLoadListener.onLoadFailed(e.toString());
                        }
                    }

                    @Override
                    public void onNext(List<ImageBean> imageList) {

                        if (null != onLoadListener) {

                            onLoadListener.onLoadCompleted(imageList);
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        if (null != mSubscription && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
