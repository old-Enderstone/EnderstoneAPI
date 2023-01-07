package net.enderstone.api.rest;

import com.bethibande.web.annotations.QueryField;
import com.bethibande.web.annotations.URI;
import net.enderstone.api.ApiContext;
import net.enderstone.api.annotations.Parameter;
import net.enderstone.api.annotations.Whitelisted;
import net.enderstone.api.common.properties.AbstractProperty;
import net.enderstone.api.common.properties.PropertyKey;
import net.enderstone.api.common.properties.impl.BooleanProperty;
import net.enderstone.api.common.properties.impl.DoubleProperty;
import net.enderstone.api.common.properties.impl.FloatProperty;
import net.enderstone.api.common.properties.impl.IntProperty;
import net.enderstone.api.common.properties.impl.LongProperty;
import net.enderstone.api.common.types.Message;
import net.enderstone.api.common.utils.Regex;
import net.enderstone.api.service.PropertyService;

import java.util.UUID;

public class PropertyHandler {

    @URI(value = "/property/set/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object setProperty(final @Parameter(2) String identifier,
                              final @Parameter(3) String valueStr,
                              final @QueryField("owner") String ownerStr,
                              final PropertyService propertyService,
                              final ApiContext context) {
        final PropertyKey<?> propertyKey = propertyService.getKeyByIdentifier(identifier);
        if(propertyKey == null) return context.entityNotFoundMessage();

        if(ownerStr != null && !ownerStr.matches(Regex.UUID)) return context.invalidParameterMessage("owner");
        final UUID owner = ownerStr != null ? UUID.fromString(ownerStr): null;

        final AbstractProperty<?> property = propertyService.getProperty(propertyKey, owner);
        property.fromString(valueStr);

        return new Message(200, "OK");
    }

    @URI(value = "/property/get/" + Regex.PROPERTY, type = URI.URIType.REGEX)
    public Object getProperty(final @Parameter(2) String identifier,
                              final @QueryField("owner") String ownerStr,
                              final PropertyService propertyService,
                              final ApiContext context) {
        final PropertyKey<?> propertyKey = propertyService.getKeyByIdentifier(identifier);
        if(propertyKey == null) return context.entityNotFoundMessage();

        if(ownerStr != null && !ownerStr.matches(Regex.UUID)) return context.invalidParameterMessage("owner");
        final UUID owner = ownerStr != null ? UUID.fromString(ownerStr): null;

        return new Message(200, propertyService.getProperty(propertyKey, owner));
    }

    @URI(value = "/property/get-all/", type = URI.URIType.REGEX)
    public Object getAllByOwner(final @QueryField("owner") String ownerStr,
                                final PropertyService propertyService,
                                final ApiContext context) {
        if(ownerStr != null && !ownerStr.matches(Regex.UUID)) return context.invalidParameterMessage("owner");
        final UUID owner = ownerStr != null ? UUID.fromString(ownerStr): null;

        return propertyService.getPropertiesByOwner(owner);
    }

    @URI(value = "/property/toggle/" + Regex.PROPERTY, type = URI.URIType.REGEX)
    @Whitelisted
    public Object onToggle(final @Parameter(2) String identifier,
                           final @QueryField("owner") String ownerStr,
                           final PropertyService propertyService,
                           final ApiContext context) {
        final PropertyKey<?> propertyKey = propertyService.getKeyByIdentifier(identifier);
        if(propertyKey == null) return context.entityNotFoundMessage();

        if(ownerStr != null && !ownerStr.matches(Regex.UUID)) return context.invalidParameterMessage("owner");
        final UUID owner = ownerStr != null ? UUID.fromString(ownerStr): null;

        final AbstractProperty<?> property = propertyService.getProperty(propertyKey, owner);

        if(!(property instanceof BooleanProperty booleanProperty)) return context.invalidParameterMessage("identifier");

        return new Message(200, booleanProperty.toggle());
    }

    @URI(value = "/property/add/" + Regex.PROPERTY + "/" + Regex.NUMBER, type = URI.URIType.REGEX)
    @Whitelisted
    public Object add(final @Parameter(2) String identifier,
                      final @Parameter(3) String numberStr,
                      final @QueryField("owner") String ownerStr,
                      final PropertyService propertyService,
                      final ApiContext context) {
        final PropertyKey<?> propertyKey = propertyService.getKeyByIdentifier(identifier);
        if(propertyKey == null) return context.entityNotFoundMessage();

        if(ownerStr != null && !ownerStr.matches(Regex.UUID)) return context.invalidParameterMessage("owner");
        final UUID owner = ownerStr != null ? UUID.fromString(ownerStr): null;

        final AbstractProperty<?> property = propertyService.getProperty(propertyKey, owner);

        if(property instanceof IntProperty numberProperty) {
            numberProperty.add(Integer.parseInt(numberStr));
            return new Message(200, numberProperty.asString());
        }
        if(property instanceof LongProperty numberProperty) {
            numberProperty.add(Long.parseLong(numberStr));
            return new Message(200, numberProperty.asString());
        }
        if(property instanceof FloatProperty numberProperty) {
            numberProperty.add(Float.parseFloat(numberStr));
            return new Message(200, numberProperty.asString());
        }
        if(property instanceof DoubleProperty numberProperty) {
            numberProperty.add(Double.parseDouble(numberStr));
            return new Message(200, numberProperty.asString());
        }

        return context.invalidParameterMessage("identifier");
    }

    @URI(value = "/property/subtract/" + Regex.PROPERTY + "/" + Regex.NUMBER, type = URI.URIType.REGEX)
    @Whitelisted
    public Object subtract(final @Parameter(2) String identifier,
                      final @Parameter(3) String numberStr,
                      final @QueryField("owner") String ownerStr,
                      final PropertyService propertyService,
                      final ApiContext context) {
        final PropertyKey<?> propertyKey = propertyService.getKeyByIdentifier(identifier);
        if(propertyKey == null) return context.entityNotFoundMessage();

        if(ownerStr != null && !ownerStr.matches(Regex.UUID)) return context.invalidParameterMessage("owner");
        final UUID owner = ownerStr != null ? UUID.fromString(ownerStr): null;

        final AbstractProperty<?> property = propertyService.getProperty(propertyKey, owner);

        if(property instanceof IntProperty numberProperty) {
            numberProperty.subtract(Integer.parseInt(numberStr));
            return new Message(200, numberProperty.asString());
        }
        if(property instanceof LongProperty numberProperty) {
            numberProperty.subtract(Long.parseLong(numberStr));
            return new Message(200, numberProperty.asString());
        }
        if(property instanceof FloatProperty numberProperty) {
            numberProperty.subtract(Float.parseFloat(numberStr));
            return new Message(200, numberProperty.asString());
        }
        if(property instanceof DoubleProperty numberProperty) {
            numberProperty.subtract(Double.parseDouble(numberStr));
            return new Message(200, numberProperty.asString());
        }

        return context.invalidParameterMessage("identifier");
    }

    @URI(value = "/property/multiply/" + Regex.PROPERTY + "/" + Regex.NUMBER, type = URI.URIType.REGEX)
    @Whitelisted
    public Object multiply(final @Parameter(2) String identifier,
                      final @Parameter(3) String numberStr,
                      final @QueryField("owner") String ownerStr,
                      final PropertyService propertyService,
                      final ApiContext context) {
        final PropertyKey<?> propertyKey = propertyService.getKeyByIdentifier(identifier);
        if(propertyKey == null) return context.entityNotFoundMessage();

        if(ownerStr != null && !ownerStr.matches(Regex.UUID)) return context.invalidParameterMessage("owner");
        final UUID owner = ownerStr != null ? UUID.fromString(ownerStr): null;

        final AbstractProperty<?> property = propertyService.getProperty(propertyKey, owner);

        if(property instanceof IntProperty numberProperty) {
            numberProperty.multiply(Integer.parseInt(numberStr));
            return new Message(200, numberProperty.asString());
        }
        if(property instanceof LongProperty numberProperty) {
            numberProperty.multiply(Long.parseLong(numberStr));
            return new Message(200, numberProperty.asString());
        }
        if(property instanceof FloatProperty numberProperty) {
            numberProperty.multiply(Float.parseFloat(numberStr));
            return new Message(200, numberProperty.asString());
        }
        if(property instanceof DoubleProperty numberProperty) {
            numberProperty.multiply(Double.parseDouble(numberStr));
            return new Message(200, numberProperty.asString());
        }

        return context.invalidParameterMessage("identifier");
    }

    @URI(value = "/property/divide/" + Regex.PROPERTY + "/" + Regex.NUMBER, type = URI.URIType.REGEX)
    @Whitelisted
    public Object divide(final @Parameter(2) String identifier,
                      final @Parameter(3) String numberStr,
                      final @QueryField("owner") String ownerStr,
                      final PropertyService propertyService,
                      final ApiContext context) {
        final PropertyKey<?> propertyKey = propertyService.getKeyByIdentifier(identifier);
        if(propertyKey == null) return context.entityNotFoundMessage();

        if(ownerStr != null && !ownerStr.matches(Regex.UUID)) return context.invalidParameterMessage("owner");
        final UUID owner = ownerStr != null ? UUID.fromString(ownerStr): null;

        final AbstractProperty<?> property = propertyService.getProperty(propertyKey, owner);

        if(property instanceof IntProperty numberProperty) {
            numberProperty.divide(Integer.parseInt(numberStr));
            return new Message(200, numberProperty.asString());
        }
        if(property instanceof LongProperty numberProperty) {
            numberProperty.divide(Long.parseLong(numberStr));
            return new Message(200, numberProperty.asString());
        }
        if(property instanceof FloatProperty numberProperty) {
            numberProperty.divide(Float.parseFloat(numberStr));
            return new Message(200, numberProperty.asString());
        }
        if(property instanceof DoubleProperty numberProperty) {
            numberProperty.divide(Double.parseDouble(numberStr));
            return new Message(200, numberProperty.asString());
        }

        return context.invalidParameterMessage("identifier");
    }

}
