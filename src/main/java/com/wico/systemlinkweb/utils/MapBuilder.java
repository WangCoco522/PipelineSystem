package com.wico.systemlinkweb.utils;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder<K, V> {
    private final Map<K, V> mapper;

    private MapBuilder() {
        this.mapper = new HashMap<>();
    }

    public static <K, V> MapBuilder<K, V> builder() {
        return new MapBuilder<>();
    }

    public MapBuilder<K, V> put(K key, V value) {
        this.mapper.put(key, value);
        return this;
    }

    public Map<K, V> build() {
        return this.mapper;
    }
}
