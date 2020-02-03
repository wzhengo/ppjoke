package com.wz.ppjoke;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PagedList.BoundaryCallback;

/**
 * @author wangzhen
 * @date 2020/02/03
 */
public abstract class AbsViewModel<T> extends ViewModel {


    private final PagedList.Config config;
    private DataSource dataSource;
    private final LiveData<PagedList<T>> pageData;

    private MutableLiveData<Boolean> boundaryPageData = new MutableLiveData<>();

    public AbsViewModel() {
        config = new PagedList.Config.Builder()
                .setPageSize(10)
                .setInitialLoadSizeHint(12)
                // .setMaxSize(100)；
                // .setEnablePlaceholders(false)
                // .setPrefetchDistance()
                .build();

        pageData = new LivePagedListBuilder<>(factory, config)
                .setInitialLoadKey(0)
                //.setFetchExecutor()
                .setBoundaryCallback(callback)
                .build();


    }

    //PagedList数据被加载 情况的边界回调callback
    //但 不是每一次分页 都会回调这里，具体请看 ContiguousPagedList#mReceiver#onPageResult
    //deferBoundaryCallbacks
    final BoundaryCallback<T> callback = new BoundaryCallback<T>() {
        @Override
        public void onZeroItemsLoaded() {
            //新提交的PagedList中没有数据
            boundaryPageData.postValue(false);
        }

        @Override
        public void onItemAtFrontLoaded(@NonNull T itemAtFront) {
            //新提交的PagedList中第一条数据被加载到列表上
            boundaryPageData.postValue(true);
        }

        @Override
        public void onItemAtEndLoaded(@NonNull T itemAtEnd) {

        }
    };


    public LiveData<PagedList<T>> getPageData() {
        return pageData;
    }

    public DataSource getDataSource() {
        return dataSource;
    }


    public MutableLiveData<Boolean> getBoundaryPageData() {
        return boundaryPageData;
    }

    DataSource.Factory factory = new DataSource.Factory() {
        @NonNull
        @Override
        public DataSource create() {
            dataSource = createDataSource();
            return dataSource;
        }
    };

    public abstract DataSource createDataSource();

}
