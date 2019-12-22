package ru.otus.homework.cache;

import java.util.function.Function;

public interface Cache<K, V> {

    //Добавить в кеш пару ключ/значение
    void put(K key, V value);

    //Получить из кеша значение по ключу
    V get(K key);

    //Возвращает хиты
    int getHitCount();

    //Возвращает миссы кеша
    int getMissCount();

    //Освобождает кеш
    void dispose();

    //Добавляет подписчика, который хочет слушать события об изменениях в кеше
    void addListener(Listener<K, V> listener);

    //Удаляет подписчика - слушателя событий
    void deleteListener(Listener<K, V> listener);
}