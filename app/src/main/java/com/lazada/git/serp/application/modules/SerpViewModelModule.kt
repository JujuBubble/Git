package com.lazada.git.serp.application.modules

import androidx.lifecycle.ViewModelProvider
import com.lazada.git.serp.application.scopes.SerpScope
import com.lazada.git.serp.paging.SerpDataSourceFactory
import com.lazada.git.serp.viewmodel.SerpViewModelFactory
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor

@Module
class SerpViewModelModule {
    @SerpScope
    @Provides
    fun providesViewModelFactory(
        dataSourceFactory: SerpDataSourceFactory,
        executor: Executor
    ): ViewModelProvider.Factory =
        SerpViewModelFactory(dataSourceFactory, executor)
}