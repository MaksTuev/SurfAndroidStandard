package ru.surfstudio.android.filestorage.sample.app.dagger

import dagger.Component
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.filestorage.sample.interactor.common.network.NetworkModule
import ru.surfstudio.android.filestorage.sample.interactor.common.network.OkHttpModule
import ru.surfstudio.android.filestorage.sample.interactor.common.network.ServerUrlModule
import ru.surfstudio.android.filestorage.sample.interactor.common.network.cache.CacheModule
import ru.surfstudio.android.filestorage.sample.interactor.ip.IpModule
import ru.surfstudio.android.filestorage.sample.interactor.ip.IpRepository
import ru.surfstudio.android.filestorage.sample.interactor.ip.cache.IpStorage
import ru.surfstudio.android.sample.dagger.app.dagger.DefaultAppComponent
import ru.surfstudio.android.sample.dagger.app.dagger.DefaultAppModule
import ru.surfstudio.android.sample.dagger.app.dagger.DefaultSharedPrefModule

@PerApplication
@Component(modules = [
    DefaultAppModule::class,
    DefaultSharedPrefModule::class,
    CacheModule::class,
    NetworkModule::class,
    OkHttpModule::class,
    ServerUrlModule::class,
    IpModule::class])
interface CustomAppComponent : DefaultAppComponent {
    fun ipRepository(): IpRepository
    fun ipStorage(): IpStorage
}
