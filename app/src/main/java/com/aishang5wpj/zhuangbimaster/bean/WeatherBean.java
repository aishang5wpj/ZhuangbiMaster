package com.aishang5wpj.zhuangbimaster.bean;

/**
 * Created by wpj on 16/6/8下午2:02.
 */
public class WeatherBean {

    public String errNum;
    public String errMsg;
    public Weather retData;

    public class Weather {
        public String city;//城市
        public String pinyin; //城市拼音
        public String citycode; //城市编码
        public String date;//日期
        public String time;//发布时间
        public String postCode;//邮编
        public String longitude;//经度
        public String latitude;//维度
        public String altitude;//海拔
        public String weather;//天气情况
        public String temp; //气温
        public String l_tmp;//最低气温
        public String h_tmp; //最高气温
        public String WD; //风向
        public String WS;//风力
        public String sunrise; //日出时间
        public String sunset; //日落时间
    }
}
