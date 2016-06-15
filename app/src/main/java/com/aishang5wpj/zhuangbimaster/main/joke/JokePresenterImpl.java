package com.aishang5wpj.zhuangbimaster.main.joke;

import com.aishang5wpj.zhuangbimaster.app.BasePresenter;
import com.aishang5wpj.zhuangbimaster.app.OnLoadListener;
import com.aishang5wpj.zhuangbimaster.bean.JokeBean;

/**
 * Created by wpj on 16/6/14下午2:33.
 */
public class JokePresenterImpl extends BasePresenter<IJokeView> implements IJokePresenter {

    private IJokeModel mJokeModel;

    public JokePresenterImpl(IJokeView iJokeView) {
        super(iJokeView);
        mJokeModel = new JokeModelImpl();
    }

    @Override
    public void loadJokes(int pageNum) {

        mView.showLoading();
        mJokeModel.loadJokes(pageNum, new OnLoadListener<JokeBean>() {
            @Override
            public void onLoadCompleted(JokeBean jokeBean) {

                mView.hideLoading();
                mView.onJokeLoad(jokeBean);
            }

            @Override
            public void onLoadFailed(String errMsg) {

                mView.hideLoading();
                mView.showToast(errMsg);
            }
        });
    }

    @Override
    public int getStartIndex() {
        return mJokeModel.getStartIndex();
    }
}
