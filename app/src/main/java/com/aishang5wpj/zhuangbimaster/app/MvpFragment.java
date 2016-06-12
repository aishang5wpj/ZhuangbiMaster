package com.aishang5wpj.zhuangbimaster.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by wpj on 16/6/7下午5:57.
 */
public abstract class MvpFragment<V extends BaseView, P extends BasePresenter<V>> extends BaseFragment implements
        BaseView {

    protected P mPresenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPresenter = createPresenter(this);
    }

    protected abstract P createPresenter(BaseView view);

    @Override
    public void onDestroy() {
        mPresenter.onDestory();
        super.onDestroy();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToast(String msg) {

        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
