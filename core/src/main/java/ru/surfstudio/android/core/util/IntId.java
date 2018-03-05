package ru.surfstudio.android.core.util;


/**
 * Задает int значение
 * !!! Важно! При использовании в качестве идентификатора у значения enum
 * (по значению ordinal()), при этом этот enum сохраняется в кэш, НЕЛЬЗЯ
 * менять порядок элементов enum. Только добавлять в конец, иначе произойдет
 * рассинхронизация значения в хранилище (кэше) и фактических логических значений
 */
public interface IntId {
    public int id();
}