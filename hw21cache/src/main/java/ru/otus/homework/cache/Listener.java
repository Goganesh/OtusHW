package ru.otus.homework.cache;

//Интерфейс для описания подписчика на события изменения кеша
public interface Listener<K, V> {

    //Вызывается при добавлении ключа в кеш.
    void onPut(K key, V value);
}