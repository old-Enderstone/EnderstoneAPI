package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.SystemProperty;
import net.enderstone.api.common.properties.abstraction.LongProperty;
import net.enderstone.api.repository.SystemPropertyRepository;

import java.util.concurrent.locks.ReentrantLock;

public class LongSystemPropertyImpl extends LongProperty {
    
    private final SystemPropertyRepository repository;
    private final ReentrantLock lock = new ReentrantLock();

    public LongSystemPropertyImpl(SystemProperty key, Long value, SystemPropertyRepository repository) {
        super(key, value);
        this.repository = repository;
    }

    @Override
    public void set(Long value) {
        lock.lock();

        repository.setValue(super.key, value);
        super.set(value);

        lock.unlock();
    }

    @Override
    public Long add(Long value) {
        lock.lock();

        Long newValue = repository.addLong(super.key, value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Long subtract(Long value) {
        lock.lock();

        Long newValue = repository.subLong(super.key, value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Long multiply(Long value) {
        lock.lock();

        Long newValue = repository.mulLong(super.key, value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Long divide(Long value) {
        lock.lock();

        Long newValue = repository.divLong(super.key, value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }
    
}
