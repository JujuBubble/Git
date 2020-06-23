package com.lazada.git.serp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lazada.git.serp.paging.SerpDataSourceFactory
import java.util.concurrent.Executor

class SerpViewModelFactory constructor(
    private val dataSourceFactory: SerpDataSourceFactory,
    private val executor: Executor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SerpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            SerpViewModelImpl(dataSourceFactory, executor) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}
