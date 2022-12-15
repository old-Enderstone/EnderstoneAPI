package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.SystemProperty;
import net.enderstone.api.common.properties.abstraction.DoubleProperty;
import net.enderstone.api.repository.SystemPropertyRepository;

import java.util.concurrent.locks.ReentrantLock;

public class DoubleSystemPropertyImpl extends DoubleProperty {
    
    private final SystemPropertyRepository repository;
    private final ReentrantLock lock = new ReentrantLock();

    public DoubleSystemPropertyImpl(SystemProperty key, Double value, SystemPropertyRepository repository) {
        super(key, value);
        this.repository = repository;
    }

    @Override
    public void set(Double value) {
        lock.lock();

        repository.setValue(super.key, value);
        super.set(value);

        lock.unlock();
    }

    @Override
    public Double add(Double value) {
        lock.lock();

        Double newValue = repository.addDouble(super.key, value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Double subtract(Double value) {
        lock.lock();

        Double newValue = repository.subDouble(super.key, value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Double multiply(Double value) {
        lock.lock();

        Double newValue = repository.mulDouble(super.key, value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Double divide(Double value) {
        lock.lock();

        Double newValue = repository.divDouble(super.key, value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }
    
}
