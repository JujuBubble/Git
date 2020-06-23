package com.lazada.git

import android.app.Application
import com.lazada.git.application.components.BaseComponent
import com.lazada.git.application.providers.BaseComponentHolder
import com.lazada.git.application.providers.BaseComponentProvider
import com.lazada.git.application.providers.baseComponent
import com.lazada.git.serp.application.components.DaggerMockBaseComponent
import dagger.android.HasAndroidInjector

open class InstrumentationTestApplication : MainApplication(), HasAndroidInjector,
    BaseComponentProvider {

    override fun onCreate() {
        super.onCreate()

        initComponents()

        baseComponent().inject(this)
    }

    private fun initComponents() {
        BaseComponentHolder.baseComponentProvider = this
    }

    override fun provideBaseComponent(application: Application): BaseComponent {
        return DaggerMockBaseComponent.builder()
            .application(application)
            .build()
    }
}