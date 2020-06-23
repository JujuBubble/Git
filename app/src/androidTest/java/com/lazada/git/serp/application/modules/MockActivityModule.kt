package com.lazada.git.serp.application.modules

import com.lazada.git.serp.application.components.MockSerpSubomponent
import com.lazada.git.serp.view.SerpActivity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [MockSerpSubomponent::class])
abstract class MockActivityModule {
    @Binds
    @IntoMap
    @ClassKey(SerpActivity::class)
    abstract fun bindMockSerpSubomponentInjectorFactory(builder: MockSerpSubomponent.Builder): AndroidInjector.Factory<*>
}
