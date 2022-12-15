package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.SystemProperty;
import net.enderstone.api.common.properties.abstraction.StringProperty;
import net.enderstone.api.repository.SystemPropertyRepository;

import java.util.concurrent.locks.ReentrantLock;

public class StringSystemPropertyImpl extends StringProperty {

    private final SystemPropertyRepository repository;
    private final ReentrantLock lock = new ReentrantLock();

    public StringSystemPropertyImpl(SystemProperty key, String value, SystemPropertyRepository repository) {
        super(key, value);
        this.repository = repository;
    }

    @Override
    public void set(String value) {
        lock.lock();

        repository.setValue(super.key, value);
        super.set(value);

        lock.unlock();
    }
}
