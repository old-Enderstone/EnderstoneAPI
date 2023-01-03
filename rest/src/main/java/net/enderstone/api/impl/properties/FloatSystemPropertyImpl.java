package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.SystemProperty;
import net.enderstone.api.common.properties.abstraction.FloatProperty;

import java.util.concurrent.locks.ReentrantLock;

public class FloatSystemPropertyImpl extends FloatProperty {

    private transient final SystemPropertyRepository repository;
    private transient final ReentrantLock lock = new ReentrantLock();

    public FloatSystemPropertyImpl(SystemProperty key, Float value, SystemPropertyRepository repository) {
        super(key, value);
        this.repository = repository;
    }

    @Override
    public void set(Float value) {
        if(value == null) throw new NullPointerException("Null not allowed here");
        lock.lock();

        if(super.value == null) {
            super.set(value);
            repository.insert(super.key, this);
        } else {
            super.set(value);
            repository.update(super.key, this);
        }

        lock.unlock();
    }

    @Override
    public Float add(Float value) {
        if(value == null) throw new NullPointerException("Null not allowed here");
        lock.lock();

        final Float newValue = get() + value;
        set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Float subtract(Float value) {
        if(value == null) throw new NullPointerException("Null not allowed here");
        lock.lock();

        final Float newValue = get() - value;
        set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Float multiply(Float value) {
        if(value == null) throw new NullPointerException("Null not allowed here");
        lock.lock();

        final Float newValue = get() * value;
        set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Float divide(Float value) {
        if(value == null) throw new NullPointerException("Null not allowed here");
        lock.lock();

        final Float newValue = get() / value;
        set(newValue);

        lock.unlock();
        return newValue;
    }
    
}
