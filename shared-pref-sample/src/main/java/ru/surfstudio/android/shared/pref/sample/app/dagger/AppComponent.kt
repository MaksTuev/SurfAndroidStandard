package ru.surfstudio.android.shared.pref.sample.app.dagger

import android.content.Context
import dagger.Component
import ru.surfstudio.android.connection.ConnectionProvider
import ru.surfstudio.android.core.app.ActiveActivityHolder
import ru.surfstudio.android.core.app.StringsProvider
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.shared.pref.sample.interactor.common.network.NetworkModule
import ru.surfstudio.android.shared.pref.sample.interactor.common.network.OkHttpModule
import ru.surfstudio.android.shared.pref.sample.interactor.common.network.ServerUrlModule
import ru.surfstudio.android.shared.pref.sample.interactor.common.network.cache.CacheModule
import ru.surfstudio.android.shared.pref.sample.interactor.ip.IpModule
import ru.surfstudio.android.shared.pref.sample.interactor.ip.IpRepository
import ru.surfstudio.android.shared.pref.sample.interactor.ip.cache.IpStorage
import ru.surfstudio.android.rx.extension.scheduler.SchedulersProvider
import ru.surfstudio.android.sample.dagger.app.dagger.AppModule
import ru.surfstudio.android.sample.dagger.app.dagger.SharedPrefModule

@PerApplication
@Component(modules = [
    AppModule::class,
    SharedPrefModule::class,
    CacheModule::class,
    NetworkModule::class,
    OkHttpModule::class,
    ServerUrlModule::class,
    IpModule::class])
interface AppComponent {
    fun context(): Context
    fun activeActivityHolder(): ActiveActivityHolder
    fun connectionProvider(): ConnectionProvider
    fun schedulerProvider(): SchedulersProvider
    fun stringsProvider(): StringsProvider
    fun ipRepository(): IpRepository
    fun ipStorage(): IpStorage
}
