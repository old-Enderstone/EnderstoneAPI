package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.SystemProperty;
import net.enderstone.api.common.properties.abstraction.LongProperty;

import java.util.concurrent.locks.ReentrantLock;

public class LongSystemPropertyImpl extends LongProperty {
    
    private transient final SystemPropertyRepository repository;
    private transient final ReentrantLock lock = new ReentrantLock();

    public LongSystemPropertyImpl(SystemProperty key, Long value, SystemPropertyRepository repository) {
        super(key, value);
        this.repository = repository;
    }

    @Override
    public void set(Long value) {
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
    public Long add(Long value) {
        if(value == null) throw new NullPointerException("Null not allowed here");
        lock.lock();

        final Long newValue = get() + value;
        set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Long subtract(Long value) {
        if(value == null) throw new NullPointerException("Null not allowed here");
        lock.lock();

        final Long newValue = get() - value;
        set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Long multiply(Long value) {
        if(value == null) throw new NullPointerException("Null not allowed here");
        lock.lock();

        final Long newValue = get() * value;
        set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Long divide(Long value) {
        if(value == null) throw new NullPointerException("Null not allowed here");
        lock.lock();

        final Long newValue = get() / value;
        set(newValue);

        lock.unlock();
        return newValue;
    }
    
}
