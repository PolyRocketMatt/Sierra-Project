package com.github.itheos.sierra.utils;

/**
 * Created by PolyRocketMatt on 13/03/2021.
 */

public class KeyValuePair<K, V> {

    private K key;
    private V value;

    public KeyValuePair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
