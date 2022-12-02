package net.enderstone.api.common.cache;

/**
 * All caches implementing this interface may not have more entries then {@link #getMaxSize()},
 * when this number of entries is reached the cache will either have to delete other entries or refuse new entries.
 */
public interface HasMaxSize {

    /**
     * Set the new max cache size. A null value will set the value to Integer.MAX_VALUE
     */
    void setMaxSize(Integer maxSize);

    int getMaxSize();

}
