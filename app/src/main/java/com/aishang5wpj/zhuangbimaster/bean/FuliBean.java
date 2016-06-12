package com.aishang5wpj.zhuangbimaster.bean;

import java.util.List;

/**
 * Created by wpj on 16/6/12下午4:20.
 */
public class FuliBean {

    public boolean error;
    public List<Fuli> results;

    public class Fuli {
        public String _id;
        public String createdAt;
        public String desc;
        public String publishedAt;
        public String source;
        public String type;
        public String url;
        public String used;
        public String who;
    }
}
