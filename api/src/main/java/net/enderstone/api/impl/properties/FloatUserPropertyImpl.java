package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.common.properties.abstraction.FloatUserProperty;
import net.enderstone.api.repository.UserPropertyRepository;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class FloatUserPropertyImpl extends FloatUserProperty {
    
    private final UserPropertyRepository repository;
    private final ReentrantLock lock = new ReentrantLock();

    public FloatUserPropertyImpl(UserProperty key, UUID owner, Float value, UserPropertyRepository repository) {
        super(key, owner, value);
        this.repository = repository;
    }

    @Override
    public void set(Float value) {
        lock.lock();

        repository.setValue(getOwner(), getKey(), value);
        super.set(value);

        lock.unlock();
    }

    @Override
    public Float add(Float value) {
        lock.lock();

        float newValue = repository.addFloat(getOwner(), getKey(), value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Float subtract(Float value) {
        lock.lock();

        float newValue = repository.subFloat(getOwner(), getKey(), value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Float multiply(Float value) {
        lock.lock();

        float newValue = repository.mulFloat(getOwner(), getKey(), value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Float divide(Float value) {
        lock.lock();

        float newValue = repository.divFloat(getOwner(), getKey(), value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }
    
}
