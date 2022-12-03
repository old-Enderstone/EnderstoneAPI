package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.common.properties.abstraction.DoubleUserProperty;
import net.enderstone.api.repository.UserPropertyRepository;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class DoubleUserPropertyImpl extends DoubleUserProperty {

    private final UserPropertyRepository repo;
    private final ReentrantLock lock = new ReentrantLock();

    public DoubleUserPropertyImpl(UserProperty key, UUID owner, Double value, UserPropertyRepository repo) {
        super(key, owner, value);
        this.repo = repo;
    }

    @Override
    public void set(Double value) {
        lock.lock();

        if(super.value == null) {
            super.set(value);
            repo.insert(toEntry(), this);
        } else {
            super.set(value);
            repo.update(toEntry(), this);
        }

        lock.unlock();
    }

    @Override
    public Double add(Double value) {
        lock.lock();

        double newValue = get() + value;
        set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Double subtract(Double value) {
        lock.lock();

        double newValue = get() - value;
        set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Double multiply(Double value) {
        lock.lock();

        double newValue = get() * value;
        set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Double divide(Double value) {
        lock.lock();

        double newValue = get() / value;
        set(newValue);

        lock.unlock();
        return newValue;
    }
}
