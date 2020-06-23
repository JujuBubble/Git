package com.lazada.git.serp.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.lazada.git.RepoListQuery
import com.lazada.git.serp.api.ReposApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor

class SerpDataSource constructor(private val reposApi: ReposApi, private val executor: Executor) :
    PageKeyedDataSource<String, RepoListQuery.Edge>() {

    private val disposables = CompositeDisposable()
    val dataLoadedState = MutableLiveData<Boolean>()
    val initialDataErrorState = MutableLiveData<Error>()
    val moreDataErrorState = MutableLiveData<Error>()

    private var retryNextPage: (() -> Any)? = null

    init {
        updateState()
    }

    private fun updateState(
        dataLoaded: Boolean = false,
        initialDataError: Error? = null,
        moreDataError: Error? = null
    ) {
        dataLoadedState.postValue(dataLoaded)
        initialDataErrorState.postValue(initialDataError)
        moreDataErrorState.postValue(moreDataError)
    }

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, RepoListQuery.Edge>
    ) {
        updateState()

        val disposable = reposApi.getRepos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { repos -> onInitialDataFetched(repos, callback, repos.last().cursor) },
                this::onInitialFetchError
            )
        disposables.add(disposable)
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, RepoListQuery.Edge>
    ) {
    }

    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<String, RepoListQuery.Edge>
    ) {
        updateState(dataLoaded = true)

        val disposable = reposApi.getRepos(params.key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { repos -> onMoreDataFetched(repos, callback, repos.last().cursor) },
                {
                    retryNextPage = {
                        loadAfter(params, callback)
                    }
                    onFetchMoreError(it)
                }
            )
        disposables.add(disposable)
    }

    private fun onInitialFetchError(throwable: Throwable) {
        updateState(initialDataError = Error(throwable.localizedMessage))
    }

    private fun onFetchMoreError(throwable: Throwable) {
        updateState(dataLoaded = true, moreDataError = Error(throwable.localizedMessage))
    }

    private fun onInitialDataFetched(
        list: List<RepoListQuery.Edge>,
        callback: LoadInitialCallback<String, RepoListQuery.Edge>,
        nextPageKey: String
    ) {
        callback.onResult(list, null, nextPageKey)
        updateState(dataLoaded = list.isNotEmpty())
    }

    private fun onMoreDataFetched(
        list: List<RepoListQuery.Edge>,
        callback: LoadCallback<String, RepoListQuery.Edge>,
        nextPageKey: String
    ) {
        updateState(dataLoaded = list.isNotEmpty())
        callback.onResult(list, nextPageKey)
    }

    fun retryNextPage() {
        retryNextPage?.let {
            executor.execute {
                it.invoke()
            }
        }
    }

    fun clear() {
        disposables.clear()
        invalidate()
    }
}