package net.enderstone.api.common.types;

import java.util.Map;

public class SimpleEntry<K, V> implements Map.Entry<K, V> {

    private final K key;
    private V value;

    public SimpleEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        final V old = this.value;
        this.value = value;
        return old;
    }

    @Override
    public String toString() {
        return (key != null ? key.toString(): "null") + ":" + (value != null ? value.toString(): "null");
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Map.Entry<?,?> entry)) return false;
        return ((key == null && entry.getKey() == null) || (key != null && key.equals(entry.getKey())))
                && ((value == null && entry.getValue() == null) || (value != null && value.equals(entry.getValue())));
    }
}
