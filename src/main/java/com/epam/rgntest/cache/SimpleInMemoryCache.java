package com.epam.rgntest.cache;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@Slf4j
public class SimpleInMemoryCache<K, V> {

    private final Map<K, V> cache = new ConcurrentHashMap<>();

    public void save(K key, Supplier<V> supplier) {
        log.debug("Cache save operation");
        cache.put(key, supplier.get());
    }

    public V get(K key, Supplier<V> supplier) {
        log.debug("Cache get operation");
        return cache.computeIfAbsent(key, s -> supplier.get());
    }

    public void remove(K key) {
        log.debug("Cache remove operation");
        cache.remove(key);
    }

    public void clear() {
        log.debug("Cache clear operation");
        cache.clear();
    }

    public long size() {
        log.debug("Cache size operation");
        return cache.size();
    }
}
