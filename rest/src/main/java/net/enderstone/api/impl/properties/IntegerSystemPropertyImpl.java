package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.SystemProperty;
import net.enderstone.api.common.properties.abstraction.IntegerProperty;
import net.enderstone.api.repository.SystemPropertyRepository;

import java.util.concurrent.locks.ReentrantLock;

public class IntegerSystemPropertyImpl extends IntegerProperty {
    
    private transient final SystemPropertyRepository repository;
    private transient final ReentrantLock lock = new ReentrantLock();

    public IntegerSystemPropertyImpl(SystemProperty key, Integer value, SystemPropertyRepository repository) {
        super(key, value);
        this.repository = repository;
    }

    @Override
    public void set(Integer value) {
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
    public Integer add(Integer value) {
        if(value == null) throw new NullPointerException("Null not allowed here");
        lock.lock();

        final Integer newValue = get() + value;
        set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Integer subtract(Integer value) {
        if(value == null) throw new NullPointerException("Null not allowed here");
        lock.lock();

        final Integer newValue = get() - value;
        set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Integer multiply(Integer value) {
        if(value == null) throw new NullPointerException("Null not allowed here");
        lock.lock();

        final Integer newValue = get() * value;
        set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Integer divide(Integer value) {
        if(value == null) throw new NullPointerException("Null not allowed here");
        lock.lock();

        final Integer newValue = get() / value;
        set(newValue);

        lock.unlock();
        return newValue;
    }
    
}
