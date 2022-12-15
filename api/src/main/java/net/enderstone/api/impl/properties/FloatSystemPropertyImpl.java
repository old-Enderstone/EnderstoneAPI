package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.SystemProperty;
import net.enderstone.api.common.properties.abstraction.FloatProperty;
import net.enderstone.api.repository.SystemPropertyRepository;

import java.util.concurrent.locks.ReentrantLock;

public class FloatSystemPropertyImpl extends FloatProperty {
    
    private final SystemPropertyRepository repository;
    private final ReentrantLock lock = new ReentrantLock();

    public FloatSystemPropertyImpl(SystemProperty key, Float value, SystemPropertyRepository repository) {
        super(key, value);
        this.repository = repository;
    }

    @Override
    public void set(Float value) {
        lock.lock();

        repository.setValue(super.key, value);
        super.set(value);

        lock.unlock();
    }

    @Override
    public Float add(Float value) {
        lock.lock();

        Float newValue = repository.addFloat(super.key, value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Float subtract(Float value) {
        lock.lock();

        Float newValue = repository.subFloat(super.key, value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Float multiply(Float value) {
        lock.lock();

        Float newValue = repository.mulFloat(super.key, value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Float divide(Float value) {
        lock.lock();

        Float newValue = repository.divFloat(super.key, value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }
    
}
