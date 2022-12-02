package net.enderstone.api.common.cache;

import net.enderstone.api.common.utils.Strings;

import java.util.UUID;

public abstract class ICache<K, V> {

    private final UUID id;
    private ICacheWriter<K, V> writer;

    public ICache(String name) {
        this.id = Strings.nameToUUID(name);
    }

    public ICache(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setWriter(ICacheWriter<K, V> writer) {
        this.writer = writer;
    }

    public ICacheWriter<K, V> getWriter() {
        return writer;
    }

    /**
     * Get the current cache size, the cache size is the amount of entries currently stored in the cache
     */
    public abstract int getSize();

    public abstract void set(K key, V value);

    public abstract V get(K key);

    public abstract V remove(K key);

    /**
     * Delete all items from cache
     */
    public abstract void clear();

    public abstract boolean isEmpty();
    public abstract boolean isNotEmpty();

    /**
     * Update the cache, used to clean up the cache, by removing redundant entries
     * @return true if the cache was successfully updated
     */
    public abstract boolean update();

}
