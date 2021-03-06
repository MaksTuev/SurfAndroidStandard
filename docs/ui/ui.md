[Главная](../main.md)

[TOC]

# UI слой

Ядром UI-слоя является модуль [core-ui][core-ui].

Большинство наших приложений построено на MVP-архитектуре.
Слои View и Presenter принадлежат к UI. Для реализации MVP используется
надстройка над core-ui в виде [core-mvp][core-mvp].


#### Использование студийных модулей
Для построения ахитектуры UI слоя следует подключить в проект core-модули:
- [core-ui][core-ui]  - базовые классы ui-слоя
- [core-mvp][core-mvp]- mvp-обертка для core-ui
- [core-app](../../core-app/README.md) - стандартная конфигурация App + дополнительные сущности

Также при неодбходимости использовать диалоги с поддержкой mvp и виджеты:
- [mvp-dialog](../../mvp-dialog/README.md) - стандартные диалоги
- [mvp-widget](../../mvp-widget/README.md) - виджеты с поддержкой mvp

Опционально(**экспериметальный модуль**):
- [core-mvp-binding](../../core-mvp-binding/README.md) - модуль для биндинга

Как правильно построить UI слой можно посмотреть [здесь][core-mvp].

[core-ui]: ../../core-ui/README.md
[core-mvp]: ../../core-mvp/README.md




### Механизм ошибок

Обработка большинства ошибок [происходит на UI слое][handle_errors].
Для этого предусмотрен ErrorHandler,
стандартная его имплементация находится в базовой вью экрана и
используется в методе `BasePresenter`.

