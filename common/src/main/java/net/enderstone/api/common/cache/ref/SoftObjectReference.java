package net.enderstone.api.common.cache.ref;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public class SoftObjectReference<T> implements ObjectReference<T> {

    private final SoftReference<T> ref;

    public SoftObjectReference(final T value, final ReferenceQueue<T> queue) {
        this.ref = new SoftReference<>(value, queue);
    }

    @Override
    public T get() {
        return ref.get();
    }

    @Override
    public void delete() {
        ref.clear();
    }
}
