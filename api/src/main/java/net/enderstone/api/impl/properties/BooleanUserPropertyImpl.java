package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.common.properties.abstraction.BooleanUserProperty;
import net.enderstone.api.repository.UserPropertyRepository;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class BooleanUserPropertyImpl extends BooleanUserProperty {

    private final UserPropertyRepository repository;
    private final ReentrantLock lock = new ReentrantLock();

    public BooleanUserPropertyImpl(UserProperty key, UUID owner, Boolean value, UserPropertyRepository repository) {
        super(key, owner, value);
        this.repository = repository;
    }

    @Override
    public void set(Boolean value) {
        lock.lock();

        super.set(value);
        repository.setValue(getOwner(), getKey(), value);

        lock.unlock();
    }

    @Override
    public Boolean toggle() {
        lock.lock();

        final boolean newValue = repository.toggle(getOwner(), getKey());
        super.set(newValue);

        lock.unlock();
        return newValue;
    }
}
