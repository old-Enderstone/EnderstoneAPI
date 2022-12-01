package net.enderstone.api.common;

import java.util.UUID;


// TODO: stats
// TODO: top users by stats
// TODO: i18n service
// TODO: delete all user data
// TODO: IntProperty, StringProperty [...]
// TODO: terms of service
public class User {

    private final UUID id;
    private String lastKnownName;

    public User(final UUID id, final String lastKnownName) {
        this.id = id;
        this.lastKnownName = lastKnownName;
    }

    public void setLastKnownName(final String lastKnownName) {
        this.lastKnownName = lastKnownName;
    }

    public UUID getId() {
        return id;
    }

    public String getLastKnownName() {
        return lastKnownName;
    }
}
