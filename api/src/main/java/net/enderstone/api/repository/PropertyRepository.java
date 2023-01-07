package net.enderstone.api.repository;

import net.enderstone.api.EnderStoneAPI;
import net.enderstone.api.common.properties.AbstractProperty;
import net.enderstone.api.common.properties.NumberProperty;
import net.enderstone.api.common.properties.PropertyKey;
import net.enderstone.api.common.repository.NumberPropertyRepository;
import net.enderstone.api.common.types.Message;
import net.enderstone.api.common.types.TypedMessage;
import net.enderstone.api.utils.IOUtils;
import org.jetbrains.annotations.Nullable;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class PropertyRepository implements NumberPropertyRepository {

    private final EnderStoneAPI api;

    public PropertyRepository(final EnderStoneAPI api) {
        this.api = api;
    }

    @SuppressWarnings("unchecked")
    public <T> AbstractProperty<T> getProperty(final PropertyKey<T> key, final @Nullable UUID owner) {
        final String url;
        if(owner == null) {
            url = String.format("%s/property/get/%s", api.getBaseUrl(), key.identifier());
        } else {
            url = String.format("%s/property/get/%s?owner=%s", api.getBaseUrl(), key.identifier(), owner);
        }

        return IOUtils.getJson(url, AbstractProperty.class);
    }

    public void setProperty(final AbstractProperty<?> property) {
        final UUID owner = property.getOwner();
        final String url;

        if(owner == null) {
            url = String.format("%s/property/set/%s/%s",
                    api.getBaseUrl(),
                    property.getKey().identifier(),
                    URLEncoder.encode(property.asString(), StandardCharsets.UTF_8));
        } else {
            url = String.format("%s/property/set/%s/%s?owner=%s",
                    api.getBaseUrl(),
                    property.getKey().identifier(),
                    URLEncoder.encode(property.asString(), StandardCharsets.UTF_8),
                    owner);
        }

        final Message message = IOUtils.getJson(url, Message.class);
        if(message == null) throw new RuntimeException("Couldn't set property");
    }

    @Override
    public <T extends Number> T add(final NumberProperty<T> property, final T number) {
        final UUID owner = property.getOwner();
        final String url;

        if(owner == null) {
            url = String.format("%s/property/add/%s/%s",
                    api.getBaseUrl(),
                    property.getKey().identifier(),
                    number);
        } else {
            url = String.format("%s/property/add/%s/%s?owner=%s",
                    api.getBaseUrl(),
                    property.getKey().identifier(),
                    number,
                    owner);
        }

        final TypedMessage<String> message = IOUtils.getJson(url, TypedMessage.getType(String.class));
        if(message == null) throw new RuntimeException("Couldn't set property");
        property.fromString(message.message());

        return property.get();
    }

    @Override
    public <T extends Number> T subtract(final NumberProperty<T> property, final T number) {
        final UUID owner = property.getOwner();
        final String url;

        if(owner == null) {
            url = String.format("%s/property/subtract/%s/%s",
                    api.getBaseUrl(),
                    property.getKey().identifier(),
                    number);
        } else {
            url = String.format("%s/property/subtract/%s/%s?owner=%s",
                    api.getBaseUrl(),
                    property.getKey().identifier(),
                    number,
                    owner);
        }

        final TypedMessage<String> message = IOUtils.getJson(url, TypedMessage.getType(String.class));
        if(message == null) throw new RuntimeException("Couldn't set property");
        property.fromString(message.message());

        return property.get();
    }

    @Override
    public <T extends Number> T divide(final NumberProperty<T> property, final T number) {
        final UUID owner = property.getOwner();
        final String url;

        if(owner == null) {
            url = String.format("%s/property/divide/%s/%s",
                    api.getBaseUrl(),
                    property.getKey().identifier(),
                    number);
        } else {
            url = String.format("%s/property/divide/%s/%s?owner=%s",
                    api.getBaseUrl(),
                    property.getKey().identifier(),
                    number,
                    owner);
        }

        final TypedMessage<String> message = IOUtils.getJson(url, TypedMessage.getType(String.class));
        if(message == null) throw new RuntimeException("Couldn't set property");
        property.fromString(message.message());

        return property.get();
    }

    @Override
    public <T extends Number> T multiply(final NumberProperty<T> property, final T number) {
        final UUID owner = property.getOwner();
        final String url;

        if(owner == null) {
            url = String.format("%s/property/multiply/%s/%s",
                    api.getBaseUrl(),
                    property.getKey().identifier(),
                    number);
        } else {
            url = String.format("%s/property/multiply/%s/%s?owner=%s",
                    api.getBaseUrl(),
                    property.getKey().identifier(),
                    number,
                    owner);
        }

        final TypedMessage<String> message = IOUtils.getJson(url, TypedMessage.getType(String.class));
        if(message == null) throw new RuntimeException("Couldn't set property");
        property.fromString(message.message());

        return property.get();
    }
}
