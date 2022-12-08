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
        final Message message = IOUtils.getJson(String.format("%s%s/%s/%s/%s", api.getBaseUrl(), "/set/player/property", user.toString(), property.toString(), value.toString()), Message.class);
        if(message == null ||message.id() != 200) throw new RuntimeException("Failed to set user property.");
    }

    public boolean toggle(UUID user, UserProperty property) {
        final Message message = IOUtils.getJson(String.format("%s%s/%s/%s", api.getBaseUrl(), "/toggle/player/property", user.toString(), property.toString()), Message.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set user property.");
        return (boolean) message.message();
    }

    public int addInt(UUID user, UserProperty property, int value) {
        final Message message = IOUtils.getJson(String.format("%s%s/%s/%s/%d", api.getBaseUrl(), "/addInt/player/property", user.toString(), property.toString(), value), Message.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set user property.");
        return (int) message.message();
    }

    public int subInt(UUID user, UserProperty property, int value) {
        final Message message = IOUtils.getJson(String.format("%s%s/%s/%s/%d", api.getBaseUrl(), "/subInt/player/property", user.toString(), property.toString(), value), Message.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set user property.");
        return (int) message.message();
    }

    public int divInt(UUID user, UserProperty property, int value) {
        final Message message = IOUtils.getJson(String.format("%s%s/%s/%s/%d", api.getBaseUrl(), "/divInt/player/property", user.toString(), property.toString(), value), Message.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set user property.");
        return (int) message.message();
    }

    public int mulInt(UUID user, UserProperty property, int value) {
        final Message message = IOUtils.getJson(String.format("%s%s/%s/%s/%d", api.getBaseUrl(), "/mulInt/player/property", user.toString(), property.toString(), value), Message.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set user property.");
        return (int) message.message();
    }

    public long addLong(UUID user, UserProperty property, long value) {
        final Message message = IOUtils.getJson(String.format("%s%s/%s/%s/%d", api.getBaseUrl(), "/addLong/player/property", user.toString(), property.toString(), value), Message.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set user property.");
        return (long) message.message();
    }

    public long subLong(UUID user, UserProperty property, long value) {
        final Message message = IOUtils.getJson(String.format("%s%s/%s/%s/%d", api.getBaseUrl(), "/subLong/player/property", user.toString(), property.toString(), value), Message.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set user property.");
        return (long) message.message();
    }

    public long divLong(UUID user, UserProperty property, long value) {
        final Message message = IOUtils.getJson(String.format("%s%s/%s/%s/%d", api.getBaseUrl(), "/divLong/player/property", user.toString(), property.toString(), value), Message.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set user property.");
        return (long) message.message();
    }

    public long mulLong(UUID user, UserProperty property, long value) {
        final Message message = IOUtils.getJson(String.format("%s%s/%s/%s/%d", api.getBaseUrl(), "/mulLong/player/property", user.toString(), property.toString(), value), Message.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set user property.");
        return (long) message.message();
    }

    public double addDouble(UUID user, UserProperty property, double value) {
        final Message message = IOUtils.getJson(String.format("%s%s/%s/%s/%f", api.getBaseUrl(), "/addDouble/player/property", user.toString(), property.toString(), value), Message.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set user property.");
        return (double) message.message();
    }

    public double subDouble(UUID user, UserProperty property, double value) {
        final Message message = IOUtils.getJson(String.format("%s%s/%s/%s/%f", api.getBaseUrl(), "/subDouble/player/property", user.toString(), property.toString(), value), Message.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set user property.");
        return (double) message.message();
    }

    public double divDouble(UUID user, UserProperty property, double value) {
        final Message message = IOUtils.getJson(String.format("%s%s/%s/%s/%f", api.getBaseUrl(), "/divDouble/player/property", user.toString(), property.toString(), value), Message.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set user property.");
        return (double) message.message();
    }

    public double mulDouble(UUID user, UserProperty property, double value) {
        final Message message = IOUtils.getJson(String.format("%s%s/%s/%s/%f", api.getBaseUrl(), "/mulDouble/player/property", user.toString(), property.toString(), value), Message.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set user property.");
        return (double) message.message();
    }

    public float addFloat(UUID user, UserProperty property, float value) {
        final Message message = IOUtils.getJson(String.format("%s%s/%s/%s/%f", api.getBaseUrl(), "/addFloat/player/property", user.toString(), property.toString(), value), Message.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set user property.");
        return (float) message.message();
    }

    public float subFloat(UUID user, UserProperty property, float value) {
        final Message message = IOUtils.getJson(String.format("%s%s/%s/%s/%f", api.getBaseUrl(), "/subFloat/player/property", user.toString(), property.toString(), value), Message.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set user property.");
        return (float) message.message();
    }

    public float divFloat(UUID user, UserProperty property, float value) {
        final Message message = IOUtils.getJson(String.format("%s%s/%s/%s/%f", api.getBaseUrl(), "/divFloat/player/property", user.toString(), property.toString(), value), Message.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set user property.");
        return (float) message.message();
    }

    public float mulFloat(UUID user, UserProperty property, float value) {
        final Message message = IOUtils.getJson(String.format("%s%s/%s/%s/%f", api.getBaseUrl(), "/mulFloat/player/property", user.toString(), property.toString(), value), Message.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set user property.");
        return (float) message.message();
    }

}
