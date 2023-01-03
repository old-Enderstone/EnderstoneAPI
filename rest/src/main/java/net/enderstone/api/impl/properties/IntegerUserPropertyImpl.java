package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.common.properties.abstraction.IntegerUserProperty;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class IntegerUserPropertyImpl extends IntegerUserProperty {

    private transient final UserPropertyRepository repo;
    private transient final ReentrantLock lock = new ReentrantLock();

    public IntegerUserPropertyImpl(UserProperty key, UUID owner, Integer value, UserPropertyRepository repo) {
        super(key, owner, value);
        this.repo = repo;
    }

    @Override
    public void set(Integer value) {
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
    public Integer add(Integer value) {
        lock.lock();

        int newValue = get() + value;
        set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Integer subtract(Integer value) {
        lock.lock();

        int newValue = get() - value;
        set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Integer multiply(Integer value) {
        lock.lock();

        int newValue = get() * value;
        set(newValue);

        lock.unlock();
        return newValue;
    }

    @Override
    public Integer divide(Integer value) {
        lock.lock();

        int newValue = get() / value;
        set(newValue);

        lock.unlock();
        return newValue;
    }
}
