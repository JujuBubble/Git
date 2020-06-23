package com.lazada.git

import android.app.Application
import com.lazada.git.application.components.BaseComponent
import com.lazada.git.application.components.DaggerBaseComponent
import com.lazada.git.application.providers.BaseComponentHolder
import com.lazada.git.application.providers.BaseComponentProvider
import com.lazada.git.application.providers.baseComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

open class MainApplication : Application(), HasAndroidInjector, BaseComponentProvider {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()

        initComponents()

        baseComponent().inject(this)
    }

    private fun initComponents() {
        BaseComponentHolder.baseComponentProvider = this
    }

    override fun provideBaseComponent(application: Application): BaseComponent {
        return DaggerBaseComponent.builder()
            .application(application)
            .build()
    }
}