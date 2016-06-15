package com.aishang5wpj.zhuangbimaster.imageloader.glide;

import android.widget.ImageView;

import com.aishang5wpj.zhuangbimaster.R;
import com.aishang5wpj.zhuangbimaster.imageloader.ImageLoaderInterface;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by wpj on 16/5/24上午9:31.
 * <p/>
 * * http://blog.csdn.net/shangmingchao/article/details/51125554
 * http://www.jianshu.com/p/31c82862ef19
 */
public class GlideImageLoader implements ImageLoaderInterface {

    @Override
    public void display(ImageView imageView, String url) {

        display(imageView, url, R.mipmap.ic_photo_size_select_actual_white_24dp
                , R.mipmap.ic_photo_size_select_actual_white_24dp);
    }

    /**
     * 这里不得不强调下Glide的一个强大的功能，当你在With后面的传Activity或者Fragment，
     * Glide就可以根据当前Activity或者Fragment的生命周期维护图片的生命周期，
     * 比如但activity销毁的时候，就会自动取消需要加载的图片
     *
     * @param imageView
     * @param url
     * @param loadingImg
     * @param errorImg
     */
    @Override
    public void display(ImageView imageView, String url, int loadingImg, int errorImg) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        DrawableTypeRequest request = Glide.with(imageView.getContext()).load(url);
        if (0 != loadingImg) {
            request.placeholder(loadingImg);
        }
        if (0 != errorImg) {
            request.error(errorImg);
        }
        request
                //缓存策略，默认不缓存原始图，只缓存结果图
                //现改为缓存所有图，否则断网时，类似于瀑布流效果（imageView长款不定时，glide本该去重新加载原始图，然后
                // 根据ImageView的目标尺寸进行相应调整）将无法正常显示；
                //若缓存了原始图，则glide可以从缓存加载原始图，并且进行相应尺寸调整了
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //用原图的1/10作为缩略图，如果缩略图先被加载出来则先显示缩略图
                .thumbnail(0.1f)
//                .fitCenter()
//                .centerCrop()
                .crossFade()
                //解决加载出来的瞬间闪一下的问题
                .dontAnimate()
                .into(imageView);
    }

    /*
    *
    * http://www.apkbus.com/forum.php?mod=viewthread&tid=245257
    * */
    @Override
    public void displayCircleImg(ImageView imageView, int resId) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(imageView.getContext())
                .load(resId)
                .transform(new GlideCircleTransform(imageView.getContext()))
                .into(imageView);
    }

    /*
    *
    * http://www.apkbus.com/forum.php?mod=viewthread&tid=245257
    * */
    @Override
    public void displayCircleImg(ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(imageView.getContext())
                .load(url)
                .transform(new GlideCircleTransform(imageView.getContext()))
                .into(imageView);
    }
}
