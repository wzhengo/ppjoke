package com.wz.ppjoke.ui.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.ItemKeyedDataSource;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.wz.libnavannotation.FragmentDestination;
import com.wz.ppjoke.model.Feed;
import com.wz.ppjoke.ui.AbsListFragment;
import com.wz.ppjoke.ui.MutableDataSource;

import java.util.List;

@FragmentDestination(pageUrl = "main/tabs/home", asStarter = true)
public class HomeFragment extends AbsListFragment<Feed, HomeViewModel> {


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.getCacheLiveData().observe(this, feeds -> adapter.submitList(feeds));
    }

    @Override
    public PagedListAdapter getAdapter() {
        final String feedType = getArguments() == null ? "all" : getArguments().getString("feedType");
        return new FeedAdapter(getContext(), feedType);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        final Feed feed = adapter.getCurrentList().get(adapter.getItemCount() - 1);
        mViewModel.loadAfter(feed.id, new ItemKeyedDataSource.LoadCallback<Feed>() {
            @Override
            public void onResult(@NonNull List<Feed> data) {
                final PagedList.Config config = adapter.getCurrentList().getConfig();
                if (data != null&&data.size()>0) {
                    final MutableDataSource<Object, Object> dataSource = new MutableDataSource<>();
                    dataSource.data.addAll(data);
                    final PagedList pagedList = dataSource.buildNewPagedList(config);
                    submitList(pagedList);

                }
            }
        });
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        //invalidate 之后Paging会重新创建一个DataSource 重新调用它的loadInitial方法加载初始化数据
        //详情见：LivePagedListBuilder#compute方法
        mViewModel.getDataSource().invalidate();
    }
}