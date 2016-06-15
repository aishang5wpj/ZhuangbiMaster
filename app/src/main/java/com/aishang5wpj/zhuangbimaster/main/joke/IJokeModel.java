package com.aishang5wpj.zhuangbimaster.main.joke;

import com.aishang5wpj.zhuangbimaster.app.OnLoadListener;
import com.aishang5wpj.zhuangbimaster.bean.JokeBean;

/**
 * Created by wpj on 16/6/14下午2:32.
 */
public interface IJokeModel {

    void loadJokes(int pageNum, OnLoadListener<JokeBean> listener);

    int getStartIndex();
}
