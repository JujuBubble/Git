package com.lazada.git.serp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lazada.git.RepoListQuery
import com.lazada.git.serp.paging.SerpDataSourceFactory
import java.util.concurrent.Executor
import javax.inject.Inject

class SerpViewModelImpl @Inject constructor(
    private val dataSourceFactory: SerpDataSourceFactory,
    executor: Executor
) : SerpViewModel() {
    private var dataLoadedState: LiveData<Boolean>
    private var initialDataError: LiveData<Error?>
    private var moreDataError: LiveData<Error?>
    private var repos: LiveData<PagedList<RepoListQuery.Edge>>

    init {
        dataLoadedState =
            Transformations.switchMap(dataSourceFactory.getMutableLiveData()) { dataSource ->
                dataSource.dataLoadedState
            }
        initialDataError =
            Transformations.switchMap(dataSourceFactory.getMutableLiveData()) { dataSource ->
                dataSource.initialDataErrorState
            }
        moreDataError =
            Transformations.switchMap(dataSourceFactory.getMutableLiveData()) { dataSource ->
                dataSource.moreDataErrorState
            }

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(20)
            .build()
        repos = LivePagedListBuilder<String, RepoListQuery.Edge>(dataSourceFactory, pagedListConfig)
            .setFetchExecutor(executor)
            .build()
    }

    override fun retryNextPage() {
        dataSourceFactory.retryNextPage()
    }

    override fun refresh() {
        dataSourceFactory.refresh()
    }

    override fun isDataLoaded(): LiveData<Boolean> = dataLoadedState
    override fun initialDataError(): LiveData<Error?> = initialDataError
    override fun moreDataError(): LiveData<Error?> = moreDataError
    override fun repos(): LiveData<PagedList<RepoListQuery.Edge>> = repos
}