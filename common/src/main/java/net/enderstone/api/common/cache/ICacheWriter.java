package net.enderstone.api.common.cache;

import net.enderstone.api.common.cache.ref.ObjectReference;

public interface ICacheWriter<K, V> {

    ObjectReference<V> write(K key, V value);

}
