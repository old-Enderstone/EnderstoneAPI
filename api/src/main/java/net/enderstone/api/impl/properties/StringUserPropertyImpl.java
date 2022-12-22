package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.common.properties.abstraction.StringUserProperty;
import net.enderstone.api.repository.UserPropertyRepository;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class StringUserPropertyImpl extends StringUserProperty {

    private final UserPropertyRepository repository;
    private final ReentrantLock lock = new ReentrantLock();

    public StringUserPropertyImpl(UserProperty key, UUID owner, String value, UserPropertyRepository repository) {
        super(key, owner, value);
        this.repository = repository;
    }

    @Override
    public void set(String value) {
        lock.lock();

        super.set(value);
        repository.setValue(getOwner(), getKey(), value);

        lock.unlock();
    }
}
