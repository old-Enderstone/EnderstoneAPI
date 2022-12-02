package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.common.properties.abstraction.StringUserProperty;
import net.enderstone.api.repo.UserPropertyRepository;

import java.util.UUID;

public class StringUserPropertyImpl extends StringUserProperty {

    private UserPropertyRepository repo;

    public StringUserPropertyImpl(UserProperty key, UUID owner, String value, UserPropertyRepository repo) {
        super(key, owner, value);
    }

    @Override
    public void set(String value) {
        if(super.value == null) {
            super.set(value);
            repo.insert(toEntry(), this);
        } else {
            super.set(value);
            repo.update(toEntry(), this);
        }
    }

    @Override
    public String get() {
        return super.get();
    }
}
