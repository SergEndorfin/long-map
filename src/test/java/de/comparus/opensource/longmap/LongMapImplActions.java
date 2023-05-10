package de.comparus.opensource.longmap;

import java.util.Arrays;
import java.util.function.Consumer;

import static org.junit.Assert.*;


public class LongMapImplActions<V> {

    // actions:
    public Consumer<LongMap<V>> setTestData(long key, V testData) {
        return map -> map.put(key, testData);
    }

    public Consumer<LongMap<V>> clean = LongMap::clear;

    // assertions:
    public final Consumer<LongMap<V>> checkSize(long expectedSize) {
        return map -> assertEquals(expectedSize, map.size());
    }

    public final Consumer<LongMap<V>> getAndCheck(long key, V expectedValue) {
        return map -> assertEquals(expectedValue, map.get(key));
    }

    public final Consumer<LongMap<V>> getAndCheckIfAbsent(long key) {
        return map -> assertNull(map.get(key));
    }

    public final Consumer<LongMap<V>> isEmpty = map -> assertTrue(map.isEmpty());

    public final Consumer<LongMap<V>> isNotEmpty = map -> assertFalse(map.isEmpty());

    public final Consumer<LongMap<V>> removeAndCheckRemovedValue(long key, V expectedValue) {
        return map -> assertEquals(expectedValue, map.remove(key));
    }

    public final Consumer<LongMap<V>> removeAndCheckIfValueAbsent(long key) {
        return map -> assertNull(map.remove(key));
    }

    public final Consumer<LongMap<V>> getAllKeysAndCheck(long[] expectedKeys) {
        return map -> assertArrayEquals(
                Arrays.stream(expectedKeys).sorted().toArray(),
                Arrays.stream(map.keys()).sorted().toArray()
        );
    }

    public final Consumer<LongMap<V>> getAllValuesAndCheck(V[] expectedValues) {
        return map -> assertArrayEquals(
                Arrays.stream(expectedValues).sorted().toArray(),
                Arrays.stream(map.values()).sorted().toArray()
        );
    }

    public final Consumer<LongMap<V>> checkKeyIfPresent(long key) {
        return map -> assertTrue(map.containsKey(key));
    }

    public final Consumer<LongMap<V>> checkKeyIfAbsent(long key) {
        return map -> assertFalse(map.containsKey(key));
    }

    public final Consumer<LongMap<V>> checkValueIfPresent(V value) {
        return map -> assertTrue(map.containsValue(value));
    }

    public final Consumer<LongMap<V>> checkValueIfAbsent(V value) {
        return map -> assertFalse(map.containsValue(value));
    }

}
