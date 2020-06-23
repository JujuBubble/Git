package com.lazada.git.serp.application.components

import android.app.Application
import com.lazada.git.application.components.BaseComponent
import com.lazada.git.application.scopes.BaseScope
import com.lazada.git.serp.application.modules.MockActivityModule
import com.lazada.git.serp.application.modules.MockBaseModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@BaseScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        MockActivityModule::class,
        MockBaseModule::class
    ]
)
interface MockBaseComponent : BaseComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): MockBaseComponent
    }
}