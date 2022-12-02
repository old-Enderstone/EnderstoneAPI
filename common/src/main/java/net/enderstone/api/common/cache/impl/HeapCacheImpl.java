package net.enderstone.api.common.cache.impl;

import net.enderstone.api.common.cache.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class HeapCacheImpl<K, V> extends ICache<K, V> implements HasListeners<K, V>,
                                                                   HasSupplier<K, V>,
                                                                   HasMaxSize,
                                                                   HasLifetime {

    private final HashMap<K, SimpleItem<K, V>> cache = new HashMap<>();
    private final Deque<K> order = new ConcurrentLinkedDeque<>();

    private CacheLifetimeType lifetimeType;
    private Long lifetime;

    private Function<K, V> supplier;

    private BiConsumer<K, V> setListener;
    private BiConsumer<K, V> removeListener;

    private Integer maxSize = Integer.MAX_VALUE;

    public HeapCacheImpl(String name) {
        super(name);
    }

    public HeapCacheImpl(UUID id) {
        super(id);
    }

    @Override
    public void setMaxSize(final Integer maxSize) {
        this.maxSize = maxSize == null ? Integer.MAX_VALUE : maxSize;
    }

    @Override
    public int getMaxSize() {
        return maxSize;
    }

    @Override
    public void setSetListener(final BiConsumer<K, V> listener) {
        this.setListener = listener;
    }

    @Override
    public void setRemoveListener(final BiConsumer<K, V> listener) {
        this.removeListener = listener;
    }

    @Override
    public void setSupplier(final Function<K, V> supplier) {
        this.supplier = supplier;
    }

    @Override
    public Function<K, V> getSupplier() {
        return supplier;
    }

    @Override
    public CacheLifetimeType getLifetimeType() {
        return lifetimeType;
    }

    @Override
    public void setLifetimeType(@NotNull final CacheLifetimeType type) {
        this.lifetimeType = type;
    }

    @Override
    public Long getItemLifetime() {
        return this.lifetime;
    }

    @Override
    public void setItemLifetime(@Nullable final Long lifetime) {
        this.lifetime = lifetime;
        if(lifetimeType == null) lifetimeType = CacheLifetimeType.ON_ACCESS;
    }

    @Override
    public void setItemLifetime(final long lifetime, final TimeUnit unit) {
        this.lifetime = unit.toMillis(lifetime);
        if(lifetimeType == null) lifetimeType = CacheLifetimeType.ON_ACCESS;
    }

    @Override
    public int getSize() {
        return cache.size();
    }

    @Override
    public void set(final K key, final V value) {
        remove(key);

        final SimpleItem<K, V> item = new SimpleItem<>(key, getWriter().write(key, value));
        if(lifetime != null) item.setExpiryTime(System.currentTimeMillis() + lifetime);

        cache.put(key, item);
        order.offer(key);
        update();

        if(this.setListener != null) setListener.accept(key, value);
    }

    @Override
    public V get(final K key) {
        SimpleItem<K, V> item = cache.get(key);
        if(item == null && supplier == null) return null;
        if(item == null) {
            V value = supplier.apply(key);
            if(value == null) return null;

            set(key, value);
            item = cache.get(key);
        };

        if(lifetime != null && lifetimeType.equals(CacheLifetimeType.ON_ACCESS)) {
            item.setExpiryTime(System.currentTimeMillis() + lifetime);
            order.remove(key);
            order.offer(key);
        }
        return item.getValue();
    }

    @Override
    public V remove(final K key) {
        final SimpleItem<K, V> item = cache.remove(key);
        if(item == null) return null;

        item.getRef().delete();
        order.remove(key);
        if(removeListener != null) removeListener.accept(key, item.getValue());

        return item.getValue();
    }

    @Override
    public void clear() {
        cache.keySet().forEach(this::remove);
    }

    @Override
    public boolean isEmpty() {
        return cache.isEmpty();
    }

    @Override
    public boolean isNotEmpty() {
        return !cache.isEmpty();
    }

    @Override
    public boolean update() {
        if(getSize() > getMaxSize()) {
            K key = order.poll();
            remove(key);
        }

        if(lifetime != null && lifetime > 0) {
            final Iterator<K> it = order.iterator();
            final long currentTime = System.currentTimeMillis();

            while(it.hasNext()) {
                SimpleItem<K, V> item = cache.get(it.next());
                if(item.getExpiryTime() <= currentTime) {
                    it.remove();
                } else {
                    break;
                }
            }
        }
        return true;
    }
}
