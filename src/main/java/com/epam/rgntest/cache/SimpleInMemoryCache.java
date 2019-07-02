package com.epam.rgntest.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class SimpleInMemoryCache<K, V> {

    private final Map<K, V> cache = new ConcurrentHashMap<>();

    public void save(K key, Supplier<V> supplier) {
        cache.put(key, supplier.get());
    }

    public V get(K key, Supplier<V> supplier) {
        return cache.computeIfAbsent(key, s -> supplier.get());
    }

    public void remove(K key) {
        cache.remove(key);
    }

    public void clear() {
        cache.clear();
    }

    public long size() {
        return cache.size();
    }
}
