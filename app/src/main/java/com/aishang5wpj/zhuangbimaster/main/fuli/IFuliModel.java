package com.aishang5wpj.zhuangbimaster.main.fuli;

import com.aishang5wpj.zhuangbimaster.app.BaseModel;
import com.aishang5wpj.zhuangbimaster.app.OnLoadListener;
import com.aishang5wpj.zhuangbimaster.bean.ImageBean;

import java.util.List;

/**
 * Created by wpj on 16/6/12下午3:51.
 */
public interface IFuliModel extends BaseModel {

    void load(int pageNum, OnLoadListener<List<ImageBean>> onLoadListener);
}
