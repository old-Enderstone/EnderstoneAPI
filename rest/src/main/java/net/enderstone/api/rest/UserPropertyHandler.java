package net.enderstone.api.rest;

import com.bethibande.web.annotations.URI;
import net.enderstone.api.ApiContext;
import net.enderstone.api.Main;
import net.enderstone.api.annotations.Parameter;
import net.enderstone.api.annotations.Whitelisted;
import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.common.properties.abstraction.*;
import net.enderstone.api.common.types.Message;
import net.enderstone.api.common.utils.Regex;

import java.util.UUID;

public class UserPropertyHandler {

    @URI(value = "/toggle/player/property/" + Regex.UUID + "/" + Regex.PROPERTY, type = URI.URIType.REGEX)
    @Whitelisted
    public Object toggleProperty(@Parameter(3) String uId,
                                  @Parameter(4) String propertyStr,
                                  ApiContext context) {
        final UUID uuid = UUID.fromString(uId);
        final UserProperty propertyType = UserProperty.valueOf(propertyStr);

        final IUserProperty<?> property = Main.userPropertyService.getUserProperty(uuid, propertyType);

        if(!(property instanceof BooleanUserProperty bp)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, bp.toggle());
    }

    @URI(value = "/set/player/property/" + Regex.UUID + "/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object setValue(@Parameter(3) String uId,
                           @Parameter(4) String propertyStr,
                           @Parameter(5) String valueStr) {
        final UUID uuid = UUID.fromString(uId);
        final UserProperty propertyType = UserProperty.valueOf(propertyStr);

        final IUserProperty<?> property = Main.userPropertyService.getUserProperty(uuid, propertyType);

        if(property instanceof StringUserProperty p) {
            p.set(valueStr);
        }
        if(property instanceof BooleanUserProperty p) {
            p.set(Boolean.parseBoolean(valueStr));
        }
        if(property instanceof IntegerUserProperty p) {
            p.set(Integer.parseInt(valueStr));
        }
        if(property instanceof LongUserProperty p) {
            p.set(Long.parseLong(valueStr));
        }
        if(property instanceof FloatUserProperty p) {
            p.set(Float.parseFloat(valueStr));
        }
        if(property instanceof DoubleUserProperty p) {
            p.set(Double.parseDouble(valueStr));
        }

        return new Message(200, "OK");
    }

    @URI(value = "/addInt/player/property/" + Regex.UUID + "/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object addInt(@Parameter(3) String uId,
                         @Parameter(4) String propertyStr,
                         @Parameter(5) String valueStr,
                         ApiContext context) {
        final UUID uuid = UUID.fromString(uId);
        final UserProperty propertyType = UserProperty.valueOf(propertyStr);
        final int value = Integer.parseInt(valueStr);

        final IUserProperty<?> property = Main.userPropertyService.getUserProperty(uuid, propertyType);
        if(!(property instanceof IntegerUserProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.add(value));
    }

    @URI(value = "/subInt/player/property/" + Regex.UUID + "/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object subInt(@Parameter(3) String uId,
                         @Parameter(4) String propertyStr,
                         @Parameter(5) String valueStr,
                         ApiContext context) {
        final UUID uuid = UUID.fromString(uId);
        final UserProperty propertyType = UserProperty.valueOf(propertyStr);
        final int value = Integer.parseInt(valueStr);

        final IUserProperty<?> property = Main.userPropertyService.getUserProperty(uuid, propertyType);
        if(!(property instanceof IntegerUserProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.subtract(value));
    }

    @URI(value = "/divInt/player/property/" + Regex.UUID + "/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object divInt(@Parameter(3) String uId,
                         @Parameter(4) String propertyStr,
                         @Parameter(5) String valueStr,
                         ApiContext context) {
        final UUID uuid = UUID.fromString(uId);
        final UserProperty propertyType = UserProperty.valueOf(propertyStr);
        final int value = Integer.parseInt(valueStr);

        final IUserProperty<?> property = Main.userPropertyService.getUserProperty(uuid, propertyType);
        if(!(property instanceof IntegerUserProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.divide(value));
    }

    @URI(value = "/mulInt/player/property/" + Regex.UUID + "/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object mulInt(@Parameter(3) String uId,
                         @Parameter(4) String propertyStr,
                         @Parameter(5) String valueStr,
                         ApiContext context) {
        final UUID uuid = UUID.fromString(uId);
        final UserProperty propertyType = UserProperty.valueOf(propertyStr);
        final int value = Integer.parseInt(valueStr);

        final IUserProperty<?> property = Main.userPropertyService.getUserProperty(uuid, propertyType);
        if(!(property instanceof IntegerUserProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.multiply(value));
    }

    @URI(value = "/addLong/player/property/" + Regex.UUID + "/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object addLong(@Parameter(3) String uId,
                         @Parameter(4) String propertyStr,
                         @Parameter(5) String valueStr,
                         ApiContext context) {
        final UUID uuid = UUID.fromString(uId);
        final UserProperty propertyType = UserProperty.valueOf(propertyStr);
        final long value = Long.parseLong(valueStr);

        final IUserProperty<?> property = Main.userPropertyService.getUserProperty(uuid, propertyType);
        if(!(property instanceof LongUserProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.add(value));
    }

    @URI(value = "/subLong/player/property/" + Regex.UUID + "/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object subLong(@Parameter(3) String uId,
                          @Parameter(4) String propertyStr,
                          @Parameter(5) String valueStr,
                          ApiContext context) {
        final UUID uuid = UUID.fromString(uId);
        final UserProperty propertyType = UserProperty.valueOf(propertyStr);
        final long value = Long.parseLong(valueStr);

        final IUserProperty<?> property = Main.userPropertyService.getUserProperty(uuid, propertyType);
        if(!(property instanceof LongUserProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.subtract(value));
    }

    @URI(value = "/divLong/player/property/" + Regex.UUID + "/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object divLong(@Parameter(3) String uId,
                          @Parameter(4) String propertyStr,
                          @Parameter(5) String valueStr,
                          ApiContext context) {
        final UUID uuid = UUID.fromString(uId);
        final UserProperty propertyType = UserProperty.valueOf(propertyStr);
        final long value = Long.parseLong(valueStr);

        final IUserProperty<?> property = Main.userPropertyService.getUserProperty(uuid, propertyType);
        if(!(property instanceof LongUserProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.divide(value));
    }

    @URI(value = "/mulLong/player/property/" + Regex.UUID + "/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object mulLong(@Parameter(3) String uId,
                          @Parameter(4) String propertyStr,
                          @Parameter(5) String valueStr,
                          ApiContext context) {
        final UUID uuid = UUID.fromString(uId);
        final UserProperty propertyType = UserProperty.valueOf(propertyStr);
        final long value = Long.parseLong(valueStr);

        final IUserProperty<?> property = Main.userPropertyService.getUserProperty(uuid, propertyType);
        if(!(property instanceof LongUserProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.multiply(value));
    }

    @URI(value = "/addFloat/player/property/" + Regex.UUID + "/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object addFloat(@Parameter(3) String uId,
                          @Parameter(4) String propertyStr,
                          @Parameter(5) String valueStr,
                          ApiContext context) {
        final UUID uuid = UUID.fromString(uId);
        final UserProperty propertyType = UserProperty.valueOf(propertyStr);
        final float value = Float.parseFloat(valueStr);

        final IUserProperty<?> property = Main.userPropertyService.getUserProperty(uuid, propertyType);
        if(!(property instanceof FloatUserProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.add(value));
    }

    @URI(value = "/subFloat/player/property/" + Regex.UUID + "/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object subFloat(@Parameter(3) String uId,
                           @Parameter(4) String propertyStr,
                           @Parameter(5) String valueStr,
                           ApiContext context) {
        final UUID uuid = UUID.fromString(uId);
        final UserProperty propertyType = UserProperty.valueOf(propertyStr);
        final float value = Float.parseFloat(valueStr);

        final IUserProperty<?> property = Main.userPropertyService.getUserProperty(uuid, propertyType);
        if(!(property instanceof FloatUserProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.subtract(value));
    }

    @URI(value = "/divFloat/player/property/" + Regex.UUID + "/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object divFloat(@Parameter(3) String uId,
                           @Parameter(4) String propertyStr,
                           @Parameter(5) String valueStr,
                           ApiContext context) {
        final UUID uuid = UUID.fromString(uId);
        final UserProperty propertyType = UserProperty.valueOf(propertyStr);
        final float value = Float.parseFloat(valueStr);

        final IUserProperty<?> property = Main.userPropertyService.getUserProperty(uuid, propertyType);
        if(!(property instanceof FloatUserProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.divide(value));
    }

    @URI(value = "/mulFloat/player/property/" + Regex.UUID + "/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object mulFloat(@Parameter(3) String uId,
                           @Parameter(4) String propertyStr,
                           @Parameter(5) String valueStr,
                           ApiContext context) {
        final UUID uuid = UUID.fromString(uId);
        final UserProperty propertyType = UserProperty.valueOf(propertyStr);
        final float value = Float.parseFloat(valueStr);

        final IUserProperty<?> property = Main.userPropertyService.getUserProperty(uuid, propertyType);
        if(!(property instanceof FloatUserProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.multiply(value));
    }

    @URI(value = "/addDouble/player/property/" + Regex.UUID + "/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object addDouble(@Parameter(3) String uId,
                           @Parameter(4) String propertyStr,
                           @Parameter(5) String valueStr,
                           ApiContext context) {
        final UUID uuid = UUID.fromString(uId);
        final UserProperty propertyType = UserProperty.valueOf(propertyStr);
        final double value = Double.parseDouble(valueStr);

        final IUserProperty<?> property = Main.userPropertyService.getUserProperty(uuid, propertyType);
        if(!(property instanceof DoubleUserProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.add(value));
    }

    @URI(value = "/subDouble/player/property/" + Regex.UUID + "/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object subDouble(@Parameter(3) String uId,
                            @Parameter(4) String propertyStr,
                            @Parameter(5) String valueStr,
                            ApiContext context) {
        final UUID uuid = UUID.fromString(uId);
        final UserProperty propertyType = UserProperty.valueOf(propertyStr);
        final double value = Double.parseDouble(valueStr);

        final IUserProperty<?> property = Main.userPropertyService.getUserProperty(uuid, propertyType);
        if(!(property instanceof DoubleUserProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.subtract(value));
    }

    @URI(value = "/divDouble/player/property/" + Regex.UUID + "/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object divDouble(@Parameter(3) String uId,
                            @Parameter(4) String propertyStr,
                            @Parameter(5) String valueStr,
                            ApiContext context) {
        final UUID uuid = UUID.fromString(uId);
        final UserProperty propertyType = UserProperty.valueOf(propertyStr);
        final double value = Double.parseDouble(valueStr);

        final IUserProperty<?> property = Main.userPropertyService.getUserProperty(uuid, propertyType);
        if(!(property instanceof DoubleUserProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.divide(value));
    }

    @URI(value = "/mulDouble/player/property/" + Regex.UUID + "/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object mulDouble(@Parameter(3) String uId,
                            @Parameter(4) String propertyStr,
                            @Parameter(5) String valueStr,
                            ApiContext context) {
        final UUID uuid = UUID.fromString(uId);
        final UserProperty propertyType = UserProperty.valueOf(propertyStr);
        final double value = Double.parseDouble(valueStr);

        final IUserProperty<?> property = Main.userPropertyService.getUserProperty(uuid, propertyType);
        if(!(property instanceof DoubleUserProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.multiply(value));
    }

}
