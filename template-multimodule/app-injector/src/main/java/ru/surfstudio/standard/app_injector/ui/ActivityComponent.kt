package ru.surfstudio.standard.app_injector.ui

import android.content.Context
import dagger.Component
import ru.surfstudio.android.connection.ConnectionProvider
import ru.surfstudio.android.core.ui.bus.RxBus
import ru.surfstudio.android.core.ui.navigation.activity.navigator.GlobalNavigator
import ru.surfstudio.android.core.ui.navigation.fragment.FragmentNavigator
import ru.surfstudio.android.core.ui.navigation.fragment.tabfragment.TabFragmentNavigator
import ru.surfstudio.android.core.ui.provider.ActivityProvider
import ru.surfstudio.android.core.ui.scope.ActivityPersistentScope
import ru.surfstudio.android.dagger.scope.PerActivity
import ru.surfstudio.android.rx.extension.scheduler.SchedulersProvider

/**
 * Компонент для @PerActivity скоупа
 */
@PerActivity
@Component(dependencies = [(ru.surfstudio.standard.app_injector.AppComponent::class)],
        modules = [(ActivityModule::class)])
interface ActivityComponent {
    fun schedulerProvider(): SchedulersProvider
    fun connectionProvider(): ConnectionProvider
    fun activityProvider(): ActivityProvider

    fun activityPersistentScope(): ActivityPersistentScope
    fun context(): Context
    fun fragmentNavigator(): FragmentNavigator
    fun tabFragmentNavigator(): TabFragmentNavigator
    fun globalNavigator(): GlobalNavigator
    fun rxBus(): RxBus
}