package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.SystemProperty;
import net.enderstone.api.common.properties.abstraction.BooleanProperty;
import net.enderstone.api.repository.SystemPropertyRepository;

import java.util.concurrent.locks.ReentrantLock;

public class BooleanSystemPropertyImpl extends BooleanProperty {

    private final SystemPropertyRepository repository;
    private final ReentrantLock lock = new ReentrantLock();

    public BooleanSystemPropertyImpl(SystemProperty key, Boolean value, SystemPropertyRepository repository) {
        super(key, value);
        this.repository = repository;
    }

    @Override
    public void set(Boolean value) {
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

    @Override
    public Boolean toggle() {
        lock.lock();

        final boolean newValue = !get();
        set(newValue);

        lock.unlock();
        return newValue;
    }
}
