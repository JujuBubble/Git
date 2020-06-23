package com.lazada.git.serp.application.modules

import com.apollographql.apollo.ApolloClient
import com.lazada.git.serp.api.ReposApi
import com.lazada.git.serp.application.scopes.SerpScope
import com.lazada.git.serp.mocks.MockReposApi
import dagger.Module
import dagger.Provides

@Module
class MockSerpApiModule {
    @SerpScope
    @Provides
    fun providesReposApi(apolloClient: ApolloClient): ReposApi = MockReposApi(apolloClient)
}