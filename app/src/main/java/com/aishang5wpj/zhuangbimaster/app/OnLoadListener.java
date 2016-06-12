package com.aishang5wpj.zhuangbimaster.app;

/**
 * Created by wpj on 16/6/8下午2:06.
 */
public interface OnLoadListener<T> {

    void onLoadCompleted(T t);

    void onLoadFailed(String errMsg);
}
