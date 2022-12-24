package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.common.properties.abstraction.LongUserProperty;
import net.enderstone.api.repository.UserPropertyRepository;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class LongUserPropertyImpl extends LongUserProperty {
    
    private transient final UserPropertyRepository repo;
    private transient final ReentrantLock lock = new ReentrantLock();

    public LongUserPropertyImpl(UserProperty key, UUID owner, Long value, UserPropertyRepository repo) {
        super(key, owner, value);
        this.repo = repo;
    }

    @Override
    public void set(Long value) {
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
    public Long add(Long value) {
        lock.lock();

        long newValue = get() + value;
        set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Long subtract(Long value) {
        lock.lock();

        long newValue = get() - value;
        set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Long multiply(Long value) {
        lock.lock();

        long newValue = get() * value;
        set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Long divide(Long value) {
        lock.lock();

        long newValue = get() / value;
        set(newValue);

        lock.unlock();
        return newValue;
    }
}
