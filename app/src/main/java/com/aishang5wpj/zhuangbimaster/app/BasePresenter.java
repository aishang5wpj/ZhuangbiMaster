package com.aishang5wpj.zhuangbimaster.app;

/**
 * Created by wpj on 16/6/7下午5:32.
 */
public class BasePresenter<V> {

    protected V mView;

    public BasePresenter(V v) {
        mView = v;
    }

    public void onDestory() {
        mView = null;
    }
}