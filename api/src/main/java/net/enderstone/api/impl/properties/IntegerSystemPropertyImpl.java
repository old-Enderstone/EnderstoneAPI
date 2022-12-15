package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.SystemProperty;
import net.enderstone.api.common.properties.abstraction.IntegerProperty;
import net.enderstone.api.repository.SystemPropertyRepository;

import java.util.concurrent.locks.ReentrantLock;

public class IntegerSystemPropertyImpl extends IntegerProperty {
    
    private final SystemPropertyRepository repository;
    private final ReentrantLock lock = new ReentrantLock();

    public IntegerSystemPropertyImpl(SystemProperty key, Integer value, SystemPropertyRepository repository) {
        super(key, value);
        this.repository = repository;
    }

    @Override
    public void set(Integer value) {
        lock.lock();

        repository.setValue(super.key, value);
        super.set(value);

        lock.unlock();
    }

    @Override
    public Integer add(Integer value) {
        lock.lock();

        Integer newValue = repository.addInt(super.key, value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Integer subtract(Integer value) {
        lock.lock();

        Integer newValue = repository.subInt(super.key, value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Integer multiply(Integer value) {
        lock.lock();

        Integer newValue = repository.mulInt(super.key, value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Integer divide(Integer value) {
        lock.lock();

        Integer newValue = repository.divInt(super.key, value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }
    
}
