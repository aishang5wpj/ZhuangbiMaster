package com.aishang5wpj.zhuangbimaster.main.joke;

/**
 * Created by wpj on 16/6/14下午2:32.
 */
public interface IJokePresenter {

    void loadJokes(int pageNum);

    int getStartIndex();
}
