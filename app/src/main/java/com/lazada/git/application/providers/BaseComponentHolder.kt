package com.lazada.git.application.providers

import android.app.Application
import com.lazada.git.application.components.BaseComponent

object BaseComponentHolder {
    lateinit var baseComponent: BaseComponent
    var baseComponentProvider: BaseComponentProvider = DefaultBaseComponentProvider
    val isInitialized: Boolean = BaseComponentHolder::baseComponent.isInitialized
}

fun Application.baseComponent(): BaseComponent {
    if (!BaseComponentHolder.isInitialized) {
        BaseComponentHolder.baseComponent =
            BaseComponentHolder.baseComponentProvider.provideBaseComponent(this)
    }

    return BaseComponentHolder.baseComponent
}