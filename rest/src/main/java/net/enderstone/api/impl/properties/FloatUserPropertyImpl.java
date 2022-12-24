package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.common.properties.abstraction.FloatUserProperty;
import net.enderstone.api.repository.UserPropertyRepository;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class FloatUserPropertyImpl extends FloatUserProperty {

    private transient final UserPropertyRepository repo;
    private transient final ReentrantLock lock = new ReentrantLock();

    public FloatUserPropertyImpl(UserProperty key, UUID owner, Float value, UserPropertyRepository repo) {
        super(key, owner, value);
        this.repo = repo;
    }

    @Override
    public void set(Float value) {
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
    public Float add(Float value) {
        lock.lock();

        float newValue = get() + value;
        set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Float subtract(Float value) {
        lock.lock();

        float newValue = get() - value;
        set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Float multiply(Float value) {
        lock.lock();

        float newValue = get() * value;
        set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Float divide(Float value) {
        lock.lock();

        float newValue = get() / value;
        set(newValue);

        lock.unlock();
        return newValue;
    }
}
