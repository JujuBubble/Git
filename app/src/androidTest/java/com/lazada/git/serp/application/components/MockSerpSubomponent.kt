package com.lazada.git.serp.application.components

import com.lazada.git.serp.application.modules.MockSerpApiModule
import com.lazada.git.serp.application.modules.SerpDataModule
import com.lazada.git.serp.application.modules.SerpViewModelModule
import com.lazada.git.serp.application.scopes.SerpScope
import com.lazada.git.serp.list.SerpActivityTest
import com.lazada.git.serp.view.SerpActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@SerpScope
@Subcomponent(
    modules = [
        MockSerpApiModule::class,
        SerpDataModule::class,
        SerpViewModelModule::class
    ]
)
interface MockSerpSubomponent : AndroidInjector<SerpActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<SerpActivity>()
}
