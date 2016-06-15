package com.aishang5wpj.zhuangbimaster.bean;

import com.google.gson.Gson;

/**
 * Created by wpj on 16/6/13下午5:13.
 */
public abstract class TextBean {

    /**
     * 默认有效期限是1分钟：24 * 60 * 60 * 1000
     */
    private static final long EXPIRE_LIMIT = 60 * 1000;
    private long mCreateTime;

    public TextBean() {
        mCreateTime = System.currentTimeMillis();
    }

    public String toString() {

        return new Gson().toJson(this);
    }

    /**
     * 在{@link #EXPIRE_LIMIT}时间之内有效，过期作废
     *
     * @return true 表示过期
     */
    public boolean isExpire() {

        //当前时间-保存时间如果超过1天，则认为过期
        return System.currentTimeMillis() - mCreateTime > EXPIRE_LIMIT;
    }
}
