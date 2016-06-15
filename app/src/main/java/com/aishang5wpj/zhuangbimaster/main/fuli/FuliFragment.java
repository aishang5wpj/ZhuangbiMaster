package com.aishang5wpj.zhuangbimaster.main.fuli;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aishang5wpj.zhuangbimaster.R;
import com.aishang5wpj.zhuangbimaster.app.BaseView;
import com.aishang5wpj.zhuangbimaster.app.MvpFragment;
import com.aishang5wpj.zhuangbimaster.bean.ImageBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wpj on 16/6/8下午5:59.
 */
public class FuliFragment extends MvpFragment<IFuliView, FuliPresenterImpl> implements IFuliView {

    @BindView(R.id.recyclerview)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.swiperefresh)
    protected SwipeRefreshLayout mRefreshLayout;

    protected int mPageNum;
    protected FuliAdapter mAdapter;
    /**
     * 上拉加载更多
     */
    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView,
                                         int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mRecyclerView.getAdapter().getItemCount()) {

                mPresenter.load(mPageNum);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(null);
            lastVisibleItem = getMaxPosition(lastPositions);
        }

        /**
         * 获得最大的位置
         *
         * @param positions
         * @return
         * @see http://www.cnblogs.com/xiaoyaoxia/p/4977125.html
         * */
        private int getMaxPosition(int[] positions) {
            int size = positions.length;
            int maxPosition = Integer.MIN_VALUE;
            for (int i = 0; i < size; i++) {
                maxPosition = Math.max(maxPosition, positions[i]);
            }
            return maxPosition;
        }
    };

    @Override
    protected FuliPresenterImpl createPresenter(BaseView view) {
        return new FuliPresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_fuli, null);

        ButterKnife.bind(this, view);

        initViews();

        mPageNum = mPresenter.getStartIndex();
        mPresenter.load(mPageNum);

        return view;
    }

    private void initViews() {

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mPageNum = mPresenter.getStartIndex();
                mPresenter.load(mPageNum);
            }
        });
        mAdapter = new FuliAdapter(getActivity());
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(mScrollListener);
    }

    @Override
    public void onFuliLoad(List<ImageBean> imageList) {

        if (mPageNum == mPresenter.getStartIndex()) {

            mAdapter.setData(imageList);
        } else {

            mAdapter.addData(imageList);
        }
        mPageNum++;
    }

    @Override
    public void onLoadFailed(String errMsg) {

        Snackbar.make(getView(), errMsg, Snackbar.LENGTH_SHORT).show();
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
