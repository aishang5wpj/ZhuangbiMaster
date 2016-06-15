MVP + Retrofit + RxJava + RxAndroid实战项目
--

#项目介绍
MVP + Retrofit + RxJava + RxAndroid结合的实战项目，实现三级缓存，判断缓存过期等。

下面是截图，打开app之前已经把进程杀掉了，而且手机的流量和wifi都关了（注意屏幕顶部的状态栏，可以看到是没有数据交互的）。

可以看到，离线时候的表现依然抢眼。

![image](https://github.com/aishang5wpj/ZhuangbiMaster/raw/master/screenshot.gif)

#依赖库
```
    compile 'com.squareup.retrofit2:retrofit:2.0.2'
    compile 'com.squareup.retrofit2:adapter-rxjava:2.0.2'
    compile 'com.squareup.retrofit2:converter-gson:2.0.2'
    compile 'io.reactivex:rxjava:1.1.5'
    compile 'io.reactivex:rxandroid:1.2.0'
    compile 'com.jakewharton:butterknife:8.0.1'
    apt 'com.jakewharton:butterknife-compiler:8.0.1'
    compile 'com.github.bumptech.glide:glide:3.7.0'
    compile 'com.github.chrisbanes.photoview:library:1.2.4'
    compile 'com.android.support:design:23.4.0'
    compile 'com.android.support:cardview-v7:23.4.0'
```

#项目结构

项目采用MVP的架构，大致如下：

![image](https://github.com/aishang5wpj/ZhuangbiMaster/raw/master/images/mvp.png)



#推荐阅读
- [浅析如何高效的使用MVP](http://mp.weixin.qq.com/s?__biz=MzAxMTI4MTkwNQ==&mid=2650820273&idx=1&sn=bb390bd5b1b678435ee4ae5014c651d2&scene=0#wechat_redirect)
- [RxImageloader](https://github.com/Chaoba/RxImageloader)
- [RxJavaSamples](https://github.com/rengwuxian/RxJavaSamples)