package com.lazada.git.serp.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.lazada.git.RepoListQuery
import com.lazada.git.serp.api.ReposApi
import java.util.concurrent.Executor
import javax.inject.Inject

class SerpDataSourceFactory @Inject constructor(
    private val reposApi: ReposApi,
    private val executor: Executor
) : DataSource.Factory<String, RepoListQuery.Edge>() {

    private val mutableLiveData = MutableLiveData<SerpDataSource>()

    override fun create(): DataSource<String, RepoListQuery.Edge> {
        val dataSource = SerpDataSource(reposApi, executor)
        mutableLiveData.postValue(dataSource)
        return dataSource
    }

    fun getMutableLiveData(): MutableLiveData<SerpDataSource> {
        return mutableLiveData
    }

    fun retryNextPage() {
        mutableLiveData.value?.retryNextPage()
    }

    fun refresh() {
        mutableLiveData.value?.clear()
    }
}