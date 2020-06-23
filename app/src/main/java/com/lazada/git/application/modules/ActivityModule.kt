package com.lazada.git.application.modules

import com.lazada.git.serp.application.components.SerpSubcomponent
import com.lazada.git.serp.view.SerpActivity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [SerpSubcomponent::class])
abstract class ActivityModule {
    @Binds
    @IntoMap
    @ClassKey(SerpActivity::class)
    abstract fun bindSerpSubcomponentInjectorFactory(builder: SerpSubcomponent.Builder): AndroidInjector.Factory<*>
}
