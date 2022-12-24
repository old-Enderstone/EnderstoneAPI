package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.common.properties.abstraction.BooleanUserProperty;
import net.enderstone.api.repository.UserPropertyRepository;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class BooleanUserPropertyImpl extends BooleanUserProperty {

    private transient final UserPropertyRepository repo;
    private transient final ReentrantLock lock = new ReentrantLock();

    public BooleanUserPropertyImpl(UserProperty key, UUID owner, Boolean value, UserPropertyRepository repo) {
        super(key, owner, value);
        this.repo = repo;
    }

    @Override
    public void set(Boolean value) {
        if(value == null) throw new NullPointerException("Null not allowed here");
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
    public Boolean toggle() {
        lock.lock();

        boolean value = !get();
        set(value);

        lock.unlock();
        return value;
    }
}
