package net.enderstone.api.common.cache.ref;

public interface ObjectReference<T> {

    T get();
    void delete();

}
