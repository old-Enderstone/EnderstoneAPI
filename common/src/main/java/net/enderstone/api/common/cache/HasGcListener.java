package net.enderstone.api.common.cache;

import java.util.function.Consumer;

public interface HasGcListener<T> {

    void setOnCollect(final Consumer<T> onCollect);

}
