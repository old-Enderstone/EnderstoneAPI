package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.common.properties.abstraction.IntegerUserProperty;
import net.enderstone.api.repository.UserPropertyRepository;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class IntegerUserPropertyImpl extends IntegerUserProperty {

    private final UserPropertyRepository repository;
    private final ReentrantLock lock = new ReentrantLock();

    public IntegerUserPropertyImpl(UserProperty key, UUID owner, Integer value, UserPropertyRepository repository) {
        super(key, owner, value);
        this.repository = repository;
    }

    @Override
    public void set(Integer value) {
        lock.lock();

        repository.setValue(getOwner(), getKey(), value);
        super.set(value);

        lock.unlock();
    }

    @Override
    public Integer add(Integer value) {
        lock.lock();

        int newValue = repository.addInt(getOwner(), getKey(), value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Integer subtract(Integer value) {
        lock.lock();

        int newValue = repository.subInt(getOwner(), getKey(), value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Integer multiply(Integer value) {
        lock.lock();

        int newValue = repository.mulInt(getOwner(), getKey(), value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Integer divide(Integer value) {
        lock.lock();

        int newValue = repository.divInt(getOwner(), getKey(), value);
        super.set(newValue);

        lock.unlock();
        return newValue;
    }
}
