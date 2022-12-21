package net.enderstone.api.common.utils;

import java.lang.reflect.InvocationTargetException;

public class ReflectUtils {

    @SuppressWarnings("unchecked")
    public static <T> T createInstance(Class<T> type) {
        try {
            return (T) type.getDeclaredConstructors()[0].newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
