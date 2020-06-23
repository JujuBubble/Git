package com.lazada.git.serp.application.modules

import com.lazada.git.serp.api.ReposApi
import com.lazada.git.serp.application.scopes.SerpScope
import com.lazada.git.serp.paging.SerpDataSourceFactory
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor

@Module
class SerpDataModule {
    @SerpScope
    @Provides
    fun providesDataFactory(reposApi: ReposApi, executor: Executor): SerpDataSourceFactory =
        SerpDataSourceFactory(reposApi, executor)
}