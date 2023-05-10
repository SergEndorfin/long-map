package de.comparus.opensource.longmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LongMapImpl<V> implements LongMap<V> {

    private int capacity = 16;

    // To simplify coding we can use of LinkedList instead of array here.
    // It allows to use streams against imperative style.
    private Entry<Long, V>[] table;
    private long size = 0;

    public LongMapImpl() {
        table = new Entry[capacity];
    }

    public LongMapImpl(int capacity) {
        this.capacity = capacity;
        table = new Entry[capacity];
    }


    public V put(long key, V value) {
        int index = getIndex(key);
        Entry<Long, V> newEntry = new Entry<>(key, value, null);
        if (table[index] == null) {
            table[index] = newEntry;
        } else {
            Entry<Long, V> previousNode = null;
            Entry<Long, V> currentNode = table[index];
            while (currentNode != null) {
                if (currentNode.getKey() == key) {
                    currentNode.setValue(value);
                    size--;
                    break;
                }
                previousNode = currentNode;
                currentNode = currentNode.getNext();
            }
            if (previousNode != null) {
                previousNode.setNext(newEntry);
            }
        }
        size++;
        return value;
    }

    public V get(long key) {
        int index = getIndex(key);
        Entry<Long, V> entry = table[index];
        while (entry != null) {
            if (entry.getKey() == key) {
                return entry.getValue();
            }
            entry = entry.getNext();
        }
        return null;
    }

    public V remove(long key) {
        int index = getIndex(key);
        Entry<Long, V> previous = null;
        Entry<Long, V> entry = table[index];
        while (entry != null) {
            if (entry.getKey() == key) {
                V removedValue;
                if (previous == null) {
                    removedValue = entry.getValue();
                    entry = entry.getNext();
                    table[index] = entry;
                } else {
                    removedValue = previous.getValue();
                    previous.setNext(entry.getNext());
                }
                size--;
                return removedValue;
            }
            previous = entry;
            entry = entry.getNext();
        }
        return null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(long key) {
        return Arrays.stream(table)
                .parallel()
                .map(this::getAllKeysFromCurrentCell)
                .anyMatch(keys -> keys.contains(key));
    }

    public boolean containsValue(V value) {
        return Arrays.stream(this.table)
                .parallel()
                .map(this::getAllValuesFromCurrentSell)
                .anyMatch(values -> values.contains(value));
    }

    public long[] keys() {
        return Arrays.stream(this.table)
                .map(this::getAllKeysFromCurrentCell)
                .flatMap(List::stream)
                .mapToLong(k -> k)
                .toArray();
    }

    public V[] values() {
        return (V[]) Arrays.stream(this.table)
                .map(this::getAllValuesFromCurrentSell)
                .flatMap(List::stream)
                .toArray();
    }

    public long size() {
        return size;
    }

    public void clear() {
        this.table = new Entry[capacity];
        this.size = 0;
    }


    private int getIndex(long key) {
        return (int) Math.abs(key % capacity);
    }

    private List<Long> getAllKeysFromCurrentCell(Entry<Long, V> entry) {
        List<Long> keys = new ArrayList<>();
        while (entry != null) {
            keys.add(entry.getKey());
            entry = entry.getNext();
        }
        return keys;
    }

    private List<V> getAllValuesFromCurrentSell(Entry<Long, V> entry) {
        List<V> values = new ArrayList<>();
        while (entry != null) {
            values.add(entry.getValue());
            entry = entry.getNext();
        }
        return values;
    }
}
