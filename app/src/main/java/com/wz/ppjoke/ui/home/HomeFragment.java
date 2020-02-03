package com.wz.ppjoke.ui.home;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.wz.libnavannotation.FragmentDestination;
import com.wz.ppjoke.model.Feed;
import com.wz.ppjoke.ui.AbsListFragment;

@FragmentDestination(pageUrl = "main/tabs/home", asStarter = true)
public class HomeFragment extends AbsListFragment<Feed, HomeViewModel> {


    @Override
    protected void afterCreateView() {

    }

    @Override
    public PagedListAdapter getAdapter() {
        final String feedType = getArguments() == null ? "all" : getArguments().getString("feedType");
        return new FeedAdapter(getContext(), feedType);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }
}