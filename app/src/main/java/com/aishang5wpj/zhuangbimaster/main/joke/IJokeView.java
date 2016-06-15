package com.aishang5wpj.zhuangbimaster.main.joke;

import com.aishang5wpj.zhuangbimaster.app.BaseView;
import com.aishang5wpj.zhuangbimaster.bean.JokeBean;

/**
 * Created by wpj on 16/6/14下午2:28.
 */
public interface IJokeView extends BaseView {

    void onJokeLoad(JokeBean jokeBean);
}
