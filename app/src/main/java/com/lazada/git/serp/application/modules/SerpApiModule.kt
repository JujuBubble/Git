package com.lazada.git.serp.application.modules

import com.apollographql.apollo.ApolloClient
import com.lazada.git.serp.api.ReposApi
import com.lazada.git.serp.application.scopes.SerpScope
import dagger.Module
import dagger.Provides

@Module
class SerpApiModule {
    @SerpScope
    @Provides
    fun providesReposApi(apolloClient: ApolloClient): ReposApi = ReposApi(apolloClient)
}