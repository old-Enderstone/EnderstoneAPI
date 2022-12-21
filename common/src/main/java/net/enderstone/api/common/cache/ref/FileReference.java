package net.enderstone.api.common.cache.ref;

import net.enderstone.api.common.types.IStreamSerializable;
import net.enderstone.api.common.utils.FileUtils;
import net.enderstone.api.common.utils.SerializableUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileReference<T extends IStreamSerializable> implements ObjectReference<T> {

    private final File file;
    private Class<T> type;

    @SuppressWarnings("unchecked")
    public FileReference(final File file, final T value) {
        this.file = file;
        this.type = (Class<T>) value.getClass();
        write(value);
    }

    @SuppressWarnings("unchecked")
    public void write(T value) {
        type = (Class<T>) value.getClass();
        try {
            FileUtils.createFileIfNotExists(file);

            final OutputStream out = new FileOutputStream(file);
            SerializableUtils.write(out, value);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T get() {
        try {
            if(!file.exists()) return null;
            final InputStream in = new FileInputStream(file);
            final T result = SerializableUtils.read(in, type);
            in.close();

            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete() {
        file.delete();
    }
}
