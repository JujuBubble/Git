package com.lazada.git.serp.application.modules

import android.app.Application
import com.apollographql.apollo.ApolloClient
import com.lazada.git.application.modules.BaseModule
import com.lazada.git.application.scopes.BaseScope
import com.lazada.git.config.GitConfiguration
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Module
class MockBaseModule {
    @BaseScope
    @Provides
    fun providesExecutor(): Executor = Executors.newFixedThreadPool(5)

    @BaseScope
    @Provides
    fun gitConfig(): GitConfiguration = GitConfiguration()

    @BaseScope
    @Provides
    fun httpClient(gitConfig: GitConfiguration): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain: Interceptor.Chain ->
            val original: Request = chain.request()
            val builder: Request.Builder =
                original.newBuilder().method(original.method(), original.body())
            builder.header("Authorization", "Bearer ${gitConfig.gitToken}")
            chain.proceed(builder.build())
        }
        .build()

    @BaseScope
    @Provides
    fun apolloClient(httpClient: OkHttpClient, gitConfig: GitConfiguration): ApolloClient =
        ApolloClient.builder()
            .okHttpClient(httpClient)
            .serverUrl(gitConfig.gitGraphQLEndpoint)
            .build()

    @BaseScope
    @Provides
    fun providesPicasso(application: Application): Picasso = Picasso.Builder(application)
        .downloader(OkHttp3Downloader(OkHttpClient.Builder().build()))
        .build()
}