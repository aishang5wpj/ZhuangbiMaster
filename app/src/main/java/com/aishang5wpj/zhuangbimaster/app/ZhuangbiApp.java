package com.aishang5wpj.zhuangbimaster.app;

import android.app.Application;

import com.aishang5wpj.zhuangbimaster.imageloader.ImageUtils;
import com.aishang5wpj.zhuangbimaster.imageloader.glide.GlideImageLoader;

/**
 * Created by wpj on 16/6/14上午11:10.
 */
public class ZhuangbiApp extends Application {

    private static ZhuangbiApp INSTANCE;
    private String ROOT_PATH;

    public static ZhuangbiApp getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        ROOT_PATH = getDir("zhuangbi", MODE_PRIVATE).getAbsolutePath();
        if (!ROOT_PATH.endsWith("/")) {
            ROOT_PATH += "/";
        }

        ImageUtils.getInstance().init(new GlideImageLoader());
    }

    public String getRootPath() {
        return ROOT_PATH;
    }
}
