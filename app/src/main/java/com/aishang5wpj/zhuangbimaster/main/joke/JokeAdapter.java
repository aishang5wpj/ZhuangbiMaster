package com.aishang5wpj.zhuangbimaster.main.joke;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aishang5wpj.zhuangbimaster.R;
import com.aishang5wpj.zhuangbimaster.bean.JokeBean;
import com.aishang5wpj.zhuangbimaster.imageloader.ImageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wpj on 16/5/24上午11:25.
 */
public class JokeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemClickListener mOnItemClickListener;
    private List<JokeBean.Joke> mJokeList;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.joke_item, parent, false);
        return new JokeHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final JokeBean.Joke joke = mJokeList.get(position);
        final JokeHolder jokeHolder = (JokeHolder) holder;
        jokeHolder.authorTv.setText(joke.author);
        jokeHolder.contentTv.setText(joke.content);
        if (TextUtils.isEmpty(joke.picUrl)) {

            jokeHolder.pictureIv.setVisibility(View.GONE);
        } else {

            jokeHolder.pictureIv.setVisibility(View.VISIBLE);
            ImageUtils.getInstance().display(jokeHolder.pictureIv, joke.picUrl);
        }
        jokeHolder.pictureIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(jokeHolder.rootView, joke);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mJokeList == null ? 0 : mJokeList.size();
    }

    public void setData(List<JokeBean.Joke> jokeList) {
        if (null == jokeList) {
            return;
        }
        mJokeList = jokeList;
        notifyDataSetChanged();
    }

    public void addData(List<JokeBean.Joke> jokeList) {
        if (null == jokeList) {
            return;
        }
        mJokeList.addAll(jokeList);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View rootView, JokeBean.Joke joke);
    }

    protected class JokeHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.joke_pic_iv)
        protected ImageView pictureIv;
        @BindView(R.id.joke_author_tv)
        protected TextView authorTv;
        @BindView(R.id.joke_content_tv)
        protected TextView contentTv;
        protected View rootView;

        public JokeHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
