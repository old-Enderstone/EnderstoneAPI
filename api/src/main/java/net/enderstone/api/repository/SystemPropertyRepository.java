package net.enderstone.api.repository;

import net.enderstone.api.EnderStoneAPI;
import net.enderstone.api.common.properties.SystemProperty;
import net.enderstone.api.common.types.Message;
import net.enderstone.api.common.types.TypedMessage;
import net.enderstone.api.utils.IOUtils;

import java.util.UUID;

public class SystemPropertyRepository {

    private final EnderStoneAPI api;

    public SystemPropertyRepository(EnderStoneAPI api) {
        this.api = api;
    }

    public void setValue(final SystemProperty property, final Object value) {
        final Message message = IOUtils.getJson(String.format("%s/%s/%s/%s",
                                                              api.getBaseUrl(),
                                                              "set/system/property",
                                                              property.toString(),
                                                              value.toString()), Message.class);
        if(message == null ||message.id() != 200) throw new RuntimeException("Failed to set system property.");
    }

    public boolean toggle(final SystemProperty property) {
        final Message message = IOUtils.getJson(String.format("%s/%s/%s",
                                                              api.getBaseUrl(),
                                                              "toggle/system/property",
                                                              property.toString()), Message.class);
        if(message == null ||message.id() != 200) throw new RuntimeException("Failed to set system property.");
        return (boolean) message.message();
    }

    public int addInt(SystemProperty property, int value) {
        final TypedMessage<Integer> message = IOUtils.getTypedMessage(String.format("%s%s/%s/%d",
                api.getBaseUrl(),
                "/addInt/system/property",
                                property.toString(),
                value), Integer.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set system property.");
        return message.message();
    }

    public int subInt(SystemProperty property, int value) {
        final TypedMessage<Integer> message = IOUtils.getTypedMessage(String.format("%s%s/%s/%d",
                api.getBaseUrl(),
                "/subInt/system/property",
                                property.toString(),
                value), Integer.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set system property.");
        return message.message();
    }

    public int divInt(SystemProperty property, int value) {
        final TypedMessage<Integer> message = IOUtils.getTypedMessage(String.format("%s%s/%s/%d",
                api.getBaseUrl(),
                "/divInt/system/property",
                                property.toString(),
                value), Integer.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set system property.");
        return message.message();
    }

    public int mulInt(SystemProperty property, int value) {
        final TypedMessage<Integer> message = IOUtils.getTypedMessage(String.format("%s%s/%s/%d",
                api.getBaseUrl(),
                "/mulInt/system/property",
                                property.toString(),
                value), Integer.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set system property.");
        return message.message();
    }

    public long addLong(SystemProperty property, long value) {
        final TypedMessage<Long> message = IOUtils.getTypedMessage(String.format("%s%s/%s/%d",
                api.getBaseUrl(),
                "/addLong/system/property",
                                property.toString(),
                value), Long.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set system property.");
        return message.message();
    }

    public long subLong(SystemProperty property, long value) {
        final TypedMessage<Long> message = IOUtils.getTypedMessage(String.format("%s%s/%s/%d",
                api.getBaseUrl(),
                "/subLong/system/property",
                                property.toString(),
                value), Long.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set system property.");
        return message.message();
    }

    public long divLong(SystemProperty property, long value) {
        final TypedMessage<Long> message = IOUtils.getTypedMessage(String.format("%s%s/%s/%d",
                api.getBaseUrl(),
                "/divLong/system/property",
                                property.toString(),
                value), Long.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set system property.");
        return message.message();
    }

    public long mulLong(SystemProperty property, long value) {
        final TypedMessage<Long> message = IOUtils.getTypedMessage(String.format("%s%s/%s/%d",
                api.getBaseUrl(),
                "/mulLong/system/property",
                                property.toString(),
                value), Long.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set system property.");
        return message.message();
    }

    public double addDouble(SystemProperty property, double value) {
        final TypedMessage<Double> message = IOUtils.getTypedMessage(String.format("%s%s/%s/%f",
                api.getBaseUrl(),
                "/addDouble/system/property",
                                property.toString(),
                value), Double.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set system property.");
        return message.message();
    }

    public double subDouble(SystemProperty property, double value) {
        final TypedMessage<Double> message = IOUtils.getTypedMessage(String.format("%s%s/%s/%f",
                api.getBaseUrl(),
                "/subDouble/system/property",
                                property.toString(),
                value), Double.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set system property.");
        return message.message();
    }

    public double divDouble(SystemProperty property, double value) {
        final TypedMessage<Double> message = IOUtils.getTypedMessage(String.format("%s%s/%s/%f",
                api.getBaseUrl(),
                "/divDouble/system/property",
                                property.toString(),
                value), Double.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set system property.");
        return message.message();
    }

    public double mulDouble(SystemProperty property, double value) {
        final TypedMessage<Double> message = IOUtils.getTypedMessage(String.format("%s%s/%s/%f",
                api.getBaseUrl(),
                "/mulDouble/system/property",
                                property.toString(),
                value), Double.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set system property.");
        return message.message();
    }

    public float addFloat(SystemProperty property, float value) {
        final TypedMessage<Float> message = IOUtils.getTypedMessage(String.format("%s%s/%s/%f",
                api.getBaseUrl(),
                "/addFloat/system/property",
                                property.toString(),
                value), Float.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set system property.");
        return message.message();
    }

    public float subFloat(SystemProperty property, float value) {
        final TypedMessage<Float> message = IOUtils.getTypedMessage(String.format("%s%s/%s/%f",
                api.getBaseUrl(),
                "/subFloat/system/property",
                                property.toString(),
                value), Float.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set system property.");
        return message.message();
    }

    public float divFloat(SystemProperty property, float value) {
        final TypedMessage<Float> message = IOUtils.getTypedMessage(String.format("%s%s/%s/%f",
                api.getBaseUrl(),
                "/divFloat/system/property",
                                property.toString(),
                value), Float.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set system property.");
        return message.message();
    }

    public float mulFloat(SystemProperty property, float value) {
        final TypedMessage<Float> message = IOUtils.getTypedMessage(String.format("%s%s/%s/%f",
                api.getBaseUrl(),
                "/mulFloat/system/property",
                                property.toString(),
                value), Float.class);
        if(message == null || message.id() != 200) throw new RuntimeException("Failed to set system property.");
        return message.message();
    }

}
