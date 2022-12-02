package net.enderstone.api.common.cache;

import java.util.function.BiConsumer;

public interface HasListeners<K, V> {

    /**
     * Invoked everytime the {@link ICache#set(Object, Object)} method is called
     */
    void setSetListener(BiConsumer<K, V> listener);

    /**
     * Invoked everytime the {@link ICache#remove(Object)} method is called,
     * also invoked when removed by {@link ICache#update()} or {@link ICache#clear()}
     */
    void setRemoveListener(BiConsumer<K, V> listener);

}
