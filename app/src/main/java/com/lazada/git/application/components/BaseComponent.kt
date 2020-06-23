package com.lazada.git.application.components

import android.app.Application
import com.apollographql.apollo.ApolloClient
import com.lazada.git.MainApplication
import com.lazada.git.application.modules.ActivityModule
import com.lazada.git.application.modules.BaseModule
import com.lazada.git.application.scopes.BaseScope
import com.squareup.picasso.Picasso
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import java.util.concurrent.Executor

@BaseScope
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    BaseModule::class
])
interface BaseComponent: AndroidInjector<MainApplication> {
    fun application(): Application
    fun apolloClient(): ApolloClient
    fun picasso(): Picasso
    fun executor(): Executor

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): BaseComponent
    }
}
