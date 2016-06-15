package com.aishang5wpj.zhuangbimaster.imageloader;

import android.widget.ImageView;

/**
 * Created by wpj on 16/6/14下午1:30.
 */
public interface ImageLoaderInterface {

    void display(ImageView imageView, String url);

    void display(ImageView imageView, String url, int loadingImg, int errorImg);

    void displayCircleImg(ImageView imageView, int resId);

    void displayCircleImg(ImageView imageView, String url);
}
