package net.enderstone.api.common.properties.impl;

import com.google.gson.Gson;
import net.enderstone.api.common.properties.AbstractProperty;
import net.enderstone.api.common.properties.PropertyKey;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

public class ArrayProperty<T> extends AbstractProperty<T[]> {

    private final Class<T> type;

    public ArrayProperty(final Class<T> type,
                         final PropertyKey<T[]> key,
                         final T[] value) {
        super(key, value);
        this.type = type;
    }

    public void set(final int index, final T value) {
        super.lock.lock();

        Objects.checkIndex(index, super.value.length);
        checkNullValue(value == null);
        super.value[index] = value;

        super.lock.unlock();
    }

    @SuppressWarnings("unchecked")
    public void remove(final T value) {
        super.lock.lock();

        checkNullValue(value == null);
        final T[] old = super.value;
        final T[] _new = (T[]) Array.newInstance(type, old == null ? 1: old.length-1);
        final int index = net.enderstone.api.common.utils.Arrays.findIndex(old, value);
        if(index == -1) return;

        if(old != null && _new.length != 0) {
            net.enderstone.api.common.utils.Arrays.rangeCopy(old, _new, index, 1);
        }

        set(_new);

        super.lock.unlock();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void fromString(final String value) {
        if(value == null) {
            set(null);
            return;
        }
        set((T[]) new Gson().fromJson(value, type.arrayType())); // TODO: global gson instance???
    }

    @Override
    public String asString() {
        return super.value == null ? "[]": Arrays.toString(super.value);
    }

    @SuppressWarnings("unchecked")
    public void add(final T value) {
        super.lock.lock();

        checkNullValue(value == null);
        final T[] old = super.value;
        final T[] _new = (T[]) Array.newInstance(type, old == null ? 1: old.length+1);

        if(old != null) System.arraycopy(old, 0, _new, 0, old.length);
        _new[_new.length-1] = value;

        set(_new);

        super.lock.unlock();;
    }

    public Class<T> getType() {
        return type;
    }
}
