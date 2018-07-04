package ru.surfstudio.android.mvpwidget.sample.interactor.ui.base.configurator;

import ru.surfstudio.android.core.ui.activity.CoreActivityInterface;
import ru.surfstudio.android.mvp.widget.configurator.BaseWidgetViewConfigurator;
import ru.surfstudio.android.mvpwidget.sample.interactor.ui.base.dagger.activity.ActivityComponent;
import ru.surfstudio.android.mvpwidget.sample.interactor.ui.base.dagger.widget.WidgetScreenModule;

/**
 * Базовый конфигуратор для ConstraintWidgetView
 */

public abstract class WidgetScreenConfigurator
        extends BaseWidgetViewConfigurator<ActivityComponent, WidgetScreenModule> {

    @Override
    protected WidgetScreenModule getWidgetScreenModule() {
        return new WidgetScreenModule(getPersistentScope());
    }

    @Override
    protected ActivityComponent getParentComponent() {
        return (ActivityComponent) ((CoreActivityInterface) getTargetWidgetView().getContext())
                .getPersistentScope()
                .getConfigurator()
                .getActivityComponent();
    }
}
