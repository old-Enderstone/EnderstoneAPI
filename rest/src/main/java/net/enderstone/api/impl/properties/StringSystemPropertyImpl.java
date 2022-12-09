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

}
