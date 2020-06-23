package com.lazada.git.serp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.lazada.git.RepoListQuery

abstract class SerpViewModel : ViewModel() {
    abstract fun retryNextPage()
    abstract fun refresh()
    abstract fun isDataLoaded(): LiveData<Boolean>
    abstract fun initialDataError(): LiveData<Error?>
    abstract fun moreDataError(): LiveData<Error?>
    abstract fun repos(): LiveData<PagedList<RepoListQuery.Edge>>
}

