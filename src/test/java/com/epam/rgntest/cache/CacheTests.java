package com.epam.rgntest.cache;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Left thread-safety not covered as the cache completely reuses ConcurrentHashMap
 */
public class CacheTests {

    private final String key1 = "key1";
    private final String key2 = "key2";
    private final String value1 = "value1";
    private final String value2 = "value2";

    private final SimpleInMemoryCache<String, String> cache = new SimpleInMemoryCache<>();

    @Before
    public void before() {
        cache.clear();
    }

    @Test
    public void shouldSaveValueByNewKey() {
        Assert.assertEquals(0, cache.size());

        cache.save(key1, () -> value1);

        Assert.assertEquals(1, cache.size());
        Assert.assertEquals(value1, cache.get(key1, () -> ""));
    }

    @Test
    public void shouldUpdateValueWhenSaveByExistingKey() {
        cache.save(key1, () -> value1);
        Assert.assertEquals(value1, cache.get(key1, () -> ""));

        cache.save(key1, () -> value2);

        Assert.assertEquals(1, cache.size());
        Assert.assertEquals(value2, cache.get(key1, () -> ""));
    }

    @Test
    public void shouldDeleteValueWhenDeletingByExistingKey() {
        cache.save(key1, () -> value1);
        Assert.assertEquals(value1, cache.get(key1, () -> ""));

        cache.remove(key1);

        Assert.assertEquals(0, cache.size());
    }

    @Test
    public void shouldDeleteAllValuesWhenClearingCache() {
        cache.save(key1, () -> value1);
        cache.save(key2, () -> value2);
        Assert.assertEquals(2, cache.size());

        cache.clear();

        Assert.assertEquals(0, cache.size());
    }

}
