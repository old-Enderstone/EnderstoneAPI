package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.common.properties.abstraction.DoubleUserProperty;
import net.enderstone.api.repository.UserPropertyRepository;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class DoubleUserPropertyImpl extends DoubleUserProperty {
    
    private final UserPropertyRepository repository;
    private final ReentrantLock lock = new ReentrantLock();

    public DoubleUserPropertyImpl(UserProperty key, UUID owner, Double value, UserPropertyRepository repository) {
        super(key, owner, value);
        this.repository = repository;
    }

    @Override
    public void set(Double value) {
        lock.lock();

        repository.setValue(getOwner(), getKey(), value);
        super.set(value);

        lock.unlock();
    }

    @Override
    public Double add(Double value) {
        lock.lock();

        double newValue = repository.addDouble(getOwner(), getKey(), value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Double subtract(Double value) {
        lock.lock();

        double newValue = repository.subDouble(getOwner(), getKey(), value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Double multiply(Double value) {
        lock.lock();

        double newValue = repository.mulDouble(getOwner(), getKey(), value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Double divide(Double value) {
        lock.lock();

        double newValue = repository.divDouble(getOwner(), getKey(), value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }
    
}
