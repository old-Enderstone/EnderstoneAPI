package net.enderstone.api.common.cache.impl;

import net.enderstone.api.common.cache.HasGcListener;
import net.enderstone.api.common.cache.HasSupplier;
import net.enderstone.api.common.cache.ICache;
import net.enderstone.api.common.cache.ref.ObjectReference;
import net.enderstone.api.common.cache.ref.SoftObjectReference;

import java.lang.ref.Cleaner;
import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This is a cache implementation using soft references.
 * <br><br>
 * !!! This cache implementation does not allow null values !!!
 * @param <K> key type
 * @param <V> value type
 */
public class SoftCacheImpl<K, V> extends ICache<K, V> implements HasGcListener<K>,
                                                                 HasSupplier<K, V> {

    private final ReferenceQueue<V> queue = new ReferenceQueue<>();
    private final SoftCacheWriter<K, V> writer = new SoftCacheWriter<>(queue);
    private final Cleaner cleaner = Cleaner.create();
    private final HashMap<K, SoftObjectReference<V>> cache = new HashMap<>();

    private Function<K, V> supplier = null;
    private Consumer<K> onCollect = null;

    public SoftCacheImpl(final String name) {
        super(name);
    }

    public SoftCacheImpl(final UUID id) {
        super(id);
    }

    public Consumer<K> getOnCollect() {
        return onCollect;
    }

    /**
     * Listener executed when value is collected by gc
     */
    @Override
    public void setOnCollect(final Consumer<K> onCollect) {
        this.onCollect = onCollect;
    }

    @Override
    public Function<K, V> getSupplier() {
        return supplier;
    }

    @Override
    public void setSupplier(final Function<K, V> supplier) {
        this.supplier = supplier;
    }

    private void onCollect(final K key) {
        if(this.onCollect == null) return;
        this.onCollect.accept(key);
    }

    @Override
    public int getSize() {
        return cache.size();
    }

    @Override
    public void set(final K key, final V value) {
        final ObjectReference<V> ref = writer.write(key, value);
        if(!(ref instanceof SoftObjectReference<V> softRef)) return;
        if(onCollect != null) cleaner.register(value, () -> this.onCollect(key));
        cache.put(key, softRef);
    }

    @Override
    public V get(final K key) {
        final SoftObjectReference<V> softRef = cache.get(key);
        V value = softRef == null ? null: softRef.get();

        if(value == null) { // call supplier if key doesn't exist or gc collected soft reference
            if(this.supplier == null) return null;

            value = this.supplier.apply(key);
            set(key, value);

            return value;
        }

        return value;
    }

    @Override
    public Set<K> getAllKeys() {
        return cache.keySet();
    }

    @Override
    public V remove(final K key) {
        final SoftObjectReference<V> softRef = cache.remove(key);
        if(softRef == null) return null;

        final V value = softRef.get();
        softRef.delete();

        return value;
    }

    @Override
    public void clear() {
        cache.keySet().forEach(this::remove);
    }

    @Override
    public boolean isEmpty() {
        return cache.size() == 0;
    }

    @Override
    public boolean isNotEmpty() {
        return cache.size() != 0;
    }

    @Override
    public boolean update() {
        return true;
    }
}
