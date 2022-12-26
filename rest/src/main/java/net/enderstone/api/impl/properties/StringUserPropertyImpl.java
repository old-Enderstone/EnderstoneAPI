package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.common.properties.abstraction.StringUserProperty;
import net.enderstone.api.repository.UserPropertyRepository;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class StringUserPropertyImpl extends StringUserProperty {

    private transient final UserPropertyRepository repo;
    private transient final ReentrantLock lock = new ReentrantLock();

    public StringUserPropertyImpl(UserProperty key, UUID owner, String value, UserPropertyRepository repo) {
        super(key, owner, value);
        this.repo = repo;
    }

    @Override
    public void set(String value) {
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
    public String get() {
        return super.get();
    }
}
