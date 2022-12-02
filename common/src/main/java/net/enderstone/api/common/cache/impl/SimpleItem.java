package net.enderstone.api.common.cache.impl;

import net.enderstone.api.common.cache.ref.ObjectReference;

public class SimpleItem<K, V> {

    private final K key;
    private final ObjectReference<V> ref;
    private final long creationTime;
    private Long expiryTime;

    public SimpleItem(K key, ObjectReference<V> ref) {
        this.key = key;
        this.ref = ref;
        this.creationTime = System.currentTimeMillis();
    }

    public V getValue() {
        return ref.get();
    }

    public void setExpiryTime(Long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public K getKey() {
        return key;
    }

    public ObjectReference<V> getRef() {
        return ref;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public Long getExpiryTime() {
        return expiryTime;
    }
}
