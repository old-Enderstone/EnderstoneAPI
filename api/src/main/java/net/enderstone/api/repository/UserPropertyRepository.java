package net.enderstone.api.repository;

import net.enderstone.api.EnderStoneAPI;
import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.common.types.Message;
import net.enderstone.api.utils.IOUtils;

import java.util.UUID;

public class UserPropertyRepository {

    private final EnderStoneAPI api;

    public UserPropertyRepository(EnderStoneAPI api) {
        this.api = api;
    }

    public void setValue(UUID user, UserProperty property, Object value) {
        Message message = IOUtils.getJson(String.format("%s%s/%s/%s/%s", api.getBaseUrl(), "/set/player/property", user.toString(), property.toString(), value.toString()), Message.class);
        if(message == null ||message.id() != 200) throw new RuntimeException("Failed to set user property.");
    }

    public boolean toggle(UUID user, UserProperty property) {
        Message message = IOUtils.getJson(String.format("%s%s/%s/%s", api.getBaseUrl(), "/toggle/player/property", user.toString(), property.toString()), Message.class);
        if(message == null ||message.id() != 200) throw new RuntimeException("Failed to set user property.");
        return (boolean) message.message();
    }

    public int addInt(UUID user, UserProperty property, int value) {
        return 0;
    }

    public int subInt(UUID user, UserProperty property, int value) {
        return 0;
    }

    public int divInt(UUID user, UserProperty property, int value) {
        return 0;
    }

    public int mulInt(UUID user, UserProperty property, int value) {
        return 0;
    }

    public long addLong(UUID user, UserProperty property, long value) {
        return 0;
    }

    public long subLong(UUID user, UserProperty property, long value) {
        return 0;
    }

    public long divLong(UUID user, UserProperty property, long value) {
        return 0;
    }

    public long mulLong(UUID user, UserProperty property, long value) {
        return 0;
    }

    public double addDouble(UUID user, UserProperty property, double value) {
        return 0;
    }

    public double subDouble(UUID user, UserProperty property, double value) {
        return 0;
    }

    public double divDouble(UUID user, UserProperty property, double value) {
        return 0;
    }

    public double mulDouble(UUID user, UserProperty property, double value) {
        return 0;
    }

    public float addFloat(UUID user, UserProperty property, float value) {
        return 0;
    }

    public float subFloat(UUID user, UserProperty property, float value) {
        return 0;
    }

    public float divFloat(UUID user, UserProperty property, float value) {
        return 0;
    }

    public float mulFloat(UUID user, UserProperty property, float value) {
        return 0;
    }

}
