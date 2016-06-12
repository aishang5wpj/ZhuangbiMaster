package com.aishang5wpj.zhuangbimaster.main.fuli;

import com.aishang5wpj.zhuangbimaster.app.BasePresenter;
import com.aishang5wpj.zhuangbimaster.app.OnLoadListener;
import com.aishang5wpj.zhuangbimaster.bean.ImageBean;

import java.util.List;

/**
 * Created by wpj on 16/6/8下午6:00.
 */
public class FuliPresenterImpl extends BasePresenter<IFuliView> implements IFuliPresenter {

    private IFuliModel mFuliModel;

    public FuliPresenterImpl(IFuliView iFuliView) {
        super(iFuliView);
        mFuliModel = new FuliModelImpl();
    }

    @Override
    public int getStartIndex() {
        return 1;
    }

    @Override
    public void load(int pageNum) {

        mView.showLoading();
        mFuliModel.load(pageNum, new OnLoadListener<List<ImageBean>>() {
            @Override
            public void onLoadCompleted(List<ImageBean> imageList) {

                mView.hideLoading();
                mView.onFuliLoad(imageList);
            }

            @Override
            public void onLoadFailed(String errMsg) {

                mView.hideLoading();
                mView.onLoadFailed(errMsg);
            }
        });
    }

    @Override
    public void onDestory() {
        mFuliModel.onDestroy();
        super.onDestory();
    }
}