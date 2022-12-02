package net.enderstone.api.common.cache;

import java.util.function.Function;

/**
 * All caches implementing this interface have to call the supplier if a key that does not currently exist in the cache is requested.
 * The value returned by the supplier will then be stored in cache along with the requested key.
 * Note: the supplier may only return null values, if the supplier cannot supply a value for the given key.
 */
public interface HasSupplier<K, V> {

    void setSupplier(Function<K, V> supplier);

    Function<K, V> getSupplier();

}
