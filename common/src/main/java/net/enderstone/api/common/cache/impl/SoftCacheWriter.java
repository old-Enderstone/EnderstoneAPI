package net.enderstone.api.common.cache.impl;

import net.enderstone.api.common.cache.ICacheWriter;
import net.enderstone.api.common.cache.ref.ObjectReference;
import net.enderstone.api.common.cache.ref.SoftObjectReference;

import java.lang.ref.ReferenceQueue;

public class SoftCacheWriter<K, V> implements ICacheWriter<K, V> {

    public final ReferenceQueue<V> queue;

    public SoftCacheWriter(final ReferenceQueue<V> queue) {
        this.queue = queue;
    }

    @Override
    public ObjectReference<V> write(final K key, final V value) {
        return new SoftObjectReference<>(value, queue);
    }
}
