package com.lazada.git.application.providers

import android.app.Application
import com.lazada.git.application.components.BaseComponent
import com.lazada.git.application.components.DaggerBaseComponent

interface BaseComponentProvider {
    fun provideBaseComponent(application: Application): BaseComponent
}

object DefaultBaseComponentProvider : BaseComponentProvider {
    override fun provideBaseComponent(application: Application): BaseComponent {
        return DaggerBaseComponent.builder()
            .application(application)
            .build()
    }
}