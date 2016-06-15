package com.aishang5wpj.zhuangbimaster.main.joke;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aishang5wpj.zhuangbimaster.R;
import com.aishang5wpj.zhuangbimaster.app.BaseView;
import com.aishang5wpj.zhuangbimaster.app.ImageDetailActivity;
import com.aishang5wpj.zhuangbimaster.app.MvpFragment;
import com.aishang5wpj.zhuangbimaster.bean.JokeBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wpj on 16/6/14下午2:26.
 */
public class JokeFragment extends MvpFragment<IJokeView, JokePresenterImpl> implements IJokeView {

    @BindView(R.id.recyclerview)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.swiperefresh)
    protected SwipeRefreshLayout mRefreshLayout;
    protected int mPageIndex;
    private JokeAdapter mJokeAdapter;

    @Override
    protected JokePresenterImpl createPresenter(BaseView view) {
        return new JokePresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_joke, null);
        ButterKnife.bind(this, view);

        initViews();

        mPresenter.loadJokes(mPresenter.getStartIndex());

        return view;
    }

    private void initViews() {

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex = mPresenter.getStartIndex();
                mPresenter.loadJokes(mPageIndex);
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == recyclerView.getAdapter().getItemCount()) {

                    //加载更多
                    mRefreshLayout.setRefreshing(true);
                    mPresenter.loadJokes(mPageIndex);
                }
            }
        });
        mJokeAdapter = new JokeAdapter();
        mJokeAdapter.setOnItemClickListener(new JokeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View rootView, JokeBean.Joke joke) {
                if (TextUtils.isEmpty(joke.picUrl)) {
                    return;
                }
                Intent intent = new Intent(getActivity(), ImageDetailActivity.class);
                intent.putExtra(ImageDetailActivity.URL, joke.picUrl);
                View transitionView = rootView.findViewById(R.id.joke_pic_iv);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(), transitionView, "transitionName");

                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
            }
        });
        //设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //设置adapter
        mRecyclerView.setAdapter(mJokeAdapter);
        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

        mPageIndex = mPresenter.getStartIndex();
    }

    @Override
    public void onJokeLoad(JokeBean jokeBean) {

        if (mPageIndex == mPresenter.getStartIndex()) {

            mJokeAdapter.setData(jokeBean.detail);
        } else {

            mJokeAdapter.addData(jokeBean.detail);
        }
        mPageIndex++;
    }

    @Override
    public void showLoading() {
        super.showLoading();
        if (!mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.setRefreshing(false);
    }
}
