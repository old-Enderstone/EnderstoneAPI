package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.SystemProperty;
import net.enderstone.api.common.properties.abstraction.DoubleProperty;

import java.util.concurrent.locks.ReentrantLock;

public class DoubleSystemPropertyImpl extends DoubleProperty {

    private transient final SystemPropertyRepository repository;
    private transient final ReentrantLock lock = new ReentrantLock();

    public DoubleSystemPropertyImpl(SystemProperty key, Double value, SystemPropertyRepository repository) {
        super(key, value);
        this.repository = repository;
    }

    @Override
    public void set(Double value) {
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
    public Double add(Double value) {
        if(value == null) throw new NullPointerException("Null not allowed here");
        lock.lock();

        final Double newValue = get() + value;
        set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Double subtract(Double value) {
        if(value == null) throw new NullPointerException("Null not allowed here");
        lock.lock();

        final Double newValue = get() - value;
        set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Double multiply(Double value) {
        if(value == null) throw new NullPointerException("Null not allowed here");
        lock.lock();

        final Double newValue = get() * value;
        set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Double divide(Double value) {
        if(value == null) throw new NullPointerException("Null not allowed here");
        lock.lock();

        final Double newValue = get() / value;
        set(newValue);

        lock.unlock();
        return newValue;
    }
}
