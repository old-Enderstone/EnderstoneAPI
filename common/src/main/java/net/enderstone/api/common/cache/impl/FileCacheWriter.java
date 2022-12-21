package net.enderstone.api.common.cache.impl;

import net.enderstone.api.common.cache.ICacheWriter;
import net.enderstone.api.common.cache.ref.FileReference;
import net.enderstone.api.common.cache.ref.ObjectReference;
import net.enderstone.api.common.types.IStreamSerializable;
import net.enderstone.api.common.utils.Strings;

import java.io.File;

public class FileCacheWriter<K, V extends IStreamSerializable> implements ICacheWriter<K, V> {

    private final File root;

    public FileCacheWriter(File root) {
        this.root = root;
    }

    @Override
    public ObjectReference<V> write(K key, V value) {
        final File file = new File(root + "/" + Strings.toSHA256(key.toString()));
        return new FileReference<>(file, value);
    }
}
