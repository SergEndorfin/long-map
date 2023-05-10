package de.comparus.opensource.longmap;

import org.junit.Before;
import org.junit.Test;

import java.util.function.Consumer;

public class LongMapImplTest {

    private final LongMapImplActions<String> actions = new LongMapImplActions<>();

    private Consumer<LongMap<String>> testData; //testing with String for simplicity

    private final String testValue = "test-data";
    private final long testKey = 16;

    @Before
    public void init() {
        testData = actions.setTestData(testKey, testValue);
    }

    @Test
    public void putOneValue_thenCheckSize() {
        testData.andThen(actions.checkSize(1))
                .accept(new LongMapImpl<>());
    }

    @Test
    public void putTwoValues_thenCheckSizeAndValues() {
        long key = 33;
        String data = "test_data_2";
        testData.andThen(actions.setTestData(key, data))
                .andThen(actions.checkSize(2))
                .andThen(actions.getAndCheck(testKey, testValue))
                .andThen(actions.getAndCheck(key, data))
                .accept(new LongMapImpl<>());
    }

    @Test
    public void putValueWithSameKey_thenReturnUpdatedValue() {
        long key = 222222;
        String data = "test_data";
        String updatedData = "updated_data";
        testData.andThen(actions.setTestData(key, data))
                .andThen(actions.checkSize(2))
                .andThen(actions.getAndCheck(key, data))
                .andThen(actions.setTestData(key, updatedData))
                .andThen(actions.checkSize(2))
                .andThen(actions.getAndCheck(key, updatedData))
                .accept(new LongMapImpl<>());
    }

    @Test
    public void isEmpty() {
        actions.isEmpty.accept(new LongMapImpl<>());
    }

    @Test
    public void isNotEmpty() {
        testData.andThen(actions.isNotEmpty)
                .accept(new LongMapImpl<>());
    }

    @Test
    public void whenGetByWrongKey_thenDataAbsentAndNullReturned() {
        testData.andThen(actions.isNotEmpty)
                .andThen(actions.getAndCheckIfAbsent(123))
                .accept(new LongMapImpl<>());
    }

    @Test
    public void remove() {
        testData.andThen(actions.removeAndCheckRemovedValue(testKey, testValue))
                .andThen(actions.checkSize(0))
                .andThen(actions.setTestData(12, "test"))
                .andThen(actions.removeAndCheckIfValueAbsent(123))
                .accept(new LongMapImpl<>());
    }

    @Test
    public void putTwoValues_thenRemoveOneCheckSizeAndValue() {
        long key = 777;
        String data = "test data 2";
        testData.andThen(actions.setTestData(11, "no matter data"))
                .andThen(actions.checkSize(2))
                .andThen(actions.setTestData(key, data))
                .andThen(actions.checkSize(3))
                .andThen(actions.removeAndCheckRemovedValue(testKey, testValue))
                .andThen(actions.checkSize(2))
                .andThen(actions.getAndCheck(key, data))
                .accept(new LongMapImpl<>());
    }

    @Test
    public void putAndRemoveValuesWithCollision_thenCheckSizeAndValues() {
        long key1 = 33;
        String data1 = "test_1";
        long key2 = 3;
        String data2 = "test_2";
        long key3 = 15;
        String data3 = "test_2";
        testData.andThen(actions.setTestData(key1, data1))
                .andThen(actions.checkSize(2))
                .andThen(actions.setTestData(key2, data2))
                .andThen(actions.checkSize(3))
                .andThen(actions.setTestData(key3, data3))
                .andThen(actions.checkSize(4))
                .andThen(actions.getAndCheck(key2, data2))
                .andThen(actions.removeAndCheckRemovedValue(key3, data3))
                .andThen(actions.checkSize(3))
                .accept(new LongMapImpl<>(2));
    }

    @Test
    public void getAllKeys() {
        long key1 = 7;
        String data1 = "test data 1";
        long key2 = 17;
        String data2 = "test data 2";
        testData.andThen(actions.setTestData(key1, data1))
                .andThen(actions.setTestData(key2, data2))
                .andThen(actions.getAllKeysAndCheck(new long[]{key1, key2, testKey}))
                .accept(new LongMapImpl<>());
    }

    @Test
    public void getAllValues() {
        long key1 = 1;
        String data1 = "test-1";
        long key2 = 2;
        String data2 = "test-2";
        testData.andThen(actions.setTestData(key1, data1))
                .andThen(actions.setTestData(key2, data2))
                .andThen(actions.getAllValuesAndCheck(new String[]{testValue, data1, data2}))
                .accept(new LongMapImpl<>());
    }

    @Test
    public void containsKey() {
        long key = 118;
        String data = "some data";
        long dummyKey = 123;
        testData.andThen(actions.setTestData(key, data))
                .andThen(actions.checkSize(2))
                .andThen(actions.checkKeyIfPresent(key))
                .andThen(actions.checkKeyIfAbsent(dummyKey))
                .accept(new LongMapImpl<>());
    }

    @Test
    public void containsValue() {
        long key = 111;
        String data = "some data";
        String dummyValue = "dummyValue";
        testData.andThen(actions.setTestData(key, data))
                .andThen(actions.checkSize(2))
                .andThen(actions.checkValueIfPresent(data))
                .andThen(actions.checkValueIfAbsent(dummyValue))
                .accept(new LongMapImpl<>(3));
    }

    @Test
    public void clear() {
        testData.andThen(actions.checkSize(1))
                .andThen(actions.clean)
                .andThen(actions.isEmpty)
                .andThen(actions.checkSize(0))
                .accept(new LongMapImpl<>());
    }
}