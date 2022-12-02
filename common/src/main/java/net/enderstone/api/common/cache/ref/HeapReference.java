package net.enderstone.api.common.cache.ref;

public class HeapReference<T> implements ObjectReference<T> {

    private T value;

    public HeapReference(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public void delete() {
        value = null;
    }
}
