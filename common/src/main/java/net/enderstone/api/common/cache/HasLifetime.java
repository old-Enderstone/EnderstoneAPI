package net.enderstone.api.common.cache;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.TimeUnit;

/**
 * All caches implementing this interface have to delete items after their lifetime expires, this is usually done using the caches {@link ICache#update()} method
 */
public interface HasLifetime {

    CacheLifetimeType getLifetimeType();
    void setLifetimeType(CacheLifetimeType type);

    /**
     * Max item lifetime in ms, null means an indefinite lifetime
     */
    Long getItemLifetime();

    /**
     * Max item lifetime in ms, if value is null, lifetime will be indefinitely
     */
    void setItemLifetime(@Nullable final Long lifetime);

    /**
     * Max item lifetime in the specified time unit, will internally be converted to ms
     */
    void setItemLifetime(final long lifetime, final TimeUnit unit);

}
