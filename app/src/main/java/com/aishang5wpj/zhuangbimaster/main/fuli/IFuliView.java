package com.aishang5wpj.zhuangbimaster.main.fuli;

import com.aishang5wpj.zhuangbimaster.app.BaseView;
import com.aishang5wpj.zhuangbimaster.bean.ImageBean;

import java.util.List;

/**
 * Created by wpj on 16/6/8下午5:59.
 */
public interface IFuliView extends BaseView {

    void onFuliLoad(List<ImageBean> imageList);

    void onLoadFailed(String errMsg);
}
