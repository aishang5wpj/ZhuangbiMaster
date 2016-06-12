package com.aishang5wpj.zhuangbimaster.main.fuli;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aishang5wpj.zhuangbimaster.R;
import com.aishang5wpj.zhuangbimaster.bean.ImageBean;
import com.aishang5wpj.zhuangbimaster.glide.ImageUtils;
import com.aishang5wpj.zhuangbimaster.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wpj on 16/6/12下午4:04.
 */
public class FuliAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ImageBean> mImageList;

    public FuliAdapter() {
        mImageList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fuli_item, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        ImageBean imageBean = mImageList.get(position);

        Context context = ((MyHolder) holder).mImageView.getContext();
        myHolder.mImageView.getLayoutParams().width = Utils.getWindowWidth(context) / 3;
        myHolder.mImageView.getLayoutParams().height = (int) (Math.random() * Utils.dp2px(context, 150)
                + Utils.dp2px(context, 100));

        ImageUtils.getInstance().display(myHolder.mImageView, imageBean.url);
    }

    @Override
    public int getItemCount() {
        return mImageList == null ? 0 : mImageList.size();
    }

    public void addData(List<ImageBean> imageList) {

        int start = mImageList.size();
        int count = imageList.size();

        mImageList.addAll(imageList);
        //避免每次加载更多时整个页面都刷新的问题
        notifyItemRangeChanged(start, count);
    }

    public void setData(List<ImageBean> imageList) {
        mImageList.clear();
        mImageList.addAll(imageList);
        notifyDataSetChanged();
    }

    protected class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageview)
        public ImageView mImageView;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
