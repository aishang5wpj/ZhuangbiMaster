package com.aishang5wpj.zhuangbimaster.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by wpj on 16/6/7下午5:34.
 *
 * @see http://mp.weixin.qq.com/s?__biz=MzAxMTI4MTkwNQ==&mid=2650820273&idx=1&sn=bb390bd5b1b678435ee4ae5014c651d2
 * &scene=0#wechat_redirect
 */
public abstract class MvpActivity<V extends BaseView, P extends BasePresenter<V>> extends BaseActivity implements
        BaseView {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = createPresenter(this);
    }

    protected abstract P createPresenter(BaseView view);

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToast(String msg) {

        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {

        if (null != mPresenter) {
            mPresenter.onDestory();
        }
        super.onDestroy();
    }
}
