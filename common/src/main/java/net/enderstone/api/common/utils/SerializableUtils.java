package net.enderstone.api.common.utils;

import net.enderstone.api.common.types.IStreamSerializable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerializableUtils {

    public static void write(final OutputStream out, final IStreamSerializable serializable) {
        try {
            serializable.serialize(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends IStreamSerializable> T read(final InputStream in, Class<T> type) {
        final T instance = ReflectUtils.createInstance(type);
        try {
            instance.deserialize(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return instance;
    }

}
