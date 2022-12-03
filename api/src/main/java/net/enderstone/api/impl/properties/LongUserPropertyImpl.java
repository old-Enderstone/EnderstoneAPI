package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.common.properties.abstraction.LongUserProperty;
import net.enderstone.api.repository.UserPropertyRepository;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class LongUserPropertyImpl extends LongUserProperty {
    
    private final UserPropertyRepository repository;
    private final ReentrantLock lock = new ReentrantLock();

    public LongUserPropertyImpl(UserProperty key, UUID owner, Long value, UserPropertyRepository repository) {
        super(key, owner, value);
        this.repository = repository;
    }

    @Override
    public void set(Long value) {
        lock.lock();

        repository.setValue(getOwner(), getKey(), value);
        super.set(value);

        lock.unlock();
    }

    @Override
    public Long add(Long value) {
        lock.lock();

        long newValue = repository.addLong(getOwner(), getKey(), value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Long subtract(Long value) {
        lock.lock();

        long newValue = repository.subLong(getOwner(), getKey(), value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Long multiply(Long value) {
        lock.lock();

        long newValue = repository.mulLong(getOwner(), getKey(), value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Long divide(Long value) {
        lock.lock();

        long newValue = repository.divLong(getOwner(), getKey(), value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }
    
}
