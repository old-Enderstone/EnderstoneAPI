package net.enderstone.api.rest;

import com.bethibande.web.annotations.URI;
import net.enderstone.api.ApiContext;
import net.enderstone.api.annotations.Parameter;
import net.enderstone.api.annotations.Whitelisted;
import net.enderstone.api.common.properties.IProperty;
import net.enderstone.api.common.properties.SystemProperty;
import net.enderstone.api.common.properties.abstraction.*;
import net.enderstone.api.common.types.Message;
import net.enderstone.api.common.utils.Regex;

import java.util.Collection;

@SuppressWarnings("unused")
public class SystemPropertyHandler {

    @URI("/get/system/all")
    public Collection<IProperty<?>> getAllProperties(final SystemPropertyService systemPropertyService) {
        return systemPropertyService.getAllProperties();
    }

    @URI(value = "/get/system/property/" + Regex.PROPERTY, type = URI.URIType.REGEX)
    public Object getProperty(final @Parameter(3) String propertyStr,
                                    final SystemPropertyService systemPropertyService,
                                    final ApiContext context) {
        final SystemProperty property = SystemProperty.valueOf(propertyStr);

        final IProperty<?> value =  systemPropertyService.getProperty(property);
        if(value == null) return context.entityNotFoundMessage();
        return value;
    }

    @URI(value = "/toggle/system/property/" + Regex.PROPERTY, type = URI.URIType.REGEX)
    @Whitelisted
    public Object toggleProperty(final @Parameter(3) String propertyStr,
                                 final ApiContext context,
                                 final SystemPropertyService systemPropertyService) {
        final SystemProperty systemProperty = SystemProperty.valueOf(propertyStr);

        final IProperty<?> property = systemPropertyService.getProperty(systemProperty);

        if(!(property instanceof BooleanProperty bp)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, bp.toggle());
    }

    @URI(value = "/set/system/property/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object setValue(final @Parameter(3) String propertyStr,
                           final @Parameter(4) String valueStr,
                           final SystemPropertyService systemPropertyService) {
        final SystemProperty propertyType = SystemProperty.valueOf(propertyStr);

        final IProperty<?> property = systemPropertyService.getProperty(propertyType);

        if(property instanceof StringProperty p) {
            p.set(valueStr);
        }
        if(property instanceof BooleanProperty p) {
            p.set(Boolean.parseBoolean(valueStr));
        }
        if(property instanceof IntegerProperty p) {
            p.set(Integer.parseInt(valueStr));
        }
        if(property instanceof LongProperty p) {
            p.set(Long.parseLong(valueStr));
        }
        if(property instanceof FloatProperty p) {
            p.set(Float.parseFloat(valueStr));
        }
        if(property instanceof DoubleProperty p) {
            p.set(Double.parseDouble(valueStr));
        }

        return new Message(200, "OK");
    }

    @URI(value = "/addInt/system/property/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object addInt(final @Parameter(3) String propertyStr,
                         final @Parameter(4) String valueStr,
                         final ApiContext context,
                         final SystemPropertyService systemPropertyService) {
        final SystemProperty propertyType = SystemProperty.valueOf(propertyStr);
        final int value = Integer.parseInt(valueStr);

        final IProperty<?> property = systemPropertyService.getProperty(propertyType);
        if(!(property instanceof IntegerProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.add(value));
    }

    @URI(value = "/subInt/system/property/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object subInt(final @Parameter(3) String propertyStr,
                         final @Parameter(4) String valueStr,
                         final ApiContext context,
                         final SystemPropertyService systemPropertyService) {
        final SystemProperty propertyType = SystemProperty.valueOf(propertyStr);
        final int value = Integer.parseInt(valueStr);

        final IProperty<?> property = systemPropertyService.getProperty(propertyType);
        if(!(property instanceof IntegerProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.subtract(value));
    }

    @URI(value = "/divInt/system/property/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object divInt(final @Parameter(3) String propertyStr,
                         final @Parameter(4) String valueStr,
                         final ApiContext context,
                         final SystemPropertyService systemPropertyService) {
        final SystemProperty propertyType = SystemProperty.valueOf(propertyStr);
        final int value = Integer.parseInt(valueStr);

        final IProperty<?> property = systemPropertyService.getProperty(propertyType);
        if(!(property instanceof IntegerProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.divide(value));
    }

    @URI(value = "/mulInt/system/property/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object mulInt(final @Parameter(3) String propertyStr,
                         final @Parameter(4) String valueStr,
                         final ApiContext context,
                         final SystemPropertyService systemPropertyService) {
        final SystemProperty propertyType = SystemProperty.valueOf(propertyStr);
        final int value = Integer.parseInt(valueStr);

        final IProperty<?> property = systemPropertyService.getProperty(propertyType);
        if(!(property instanceof IntegerProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.multiply(value));
    }

    @URI(value = "/addLong/system/property/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object addLong(final @Parameter(3) String propertyStr,
                          final @Parameter(4) String valueStr,
                          final  ApiContext context,
                          final SystemPropertyService systemPropertyService) {
        final SystemProperty propertyType = SystemProperty.valueOf(propertyStr);
        final long value = Long.parseLong(valueStr);

        final IProperty<?> property = systemPropertyService.getProperty(propertyType);
        if(!(property instanceof LongProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.add(value));
    }

    @URI(value = "/subLong/system/property/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object subLong(final @Parameter(3) String propertyStr,
                          final  @Parameter(4) String valueStr,
                          final ApiContext context,
                          final SystemPropertyService systemPropertyService) {
        final SystemProperty propertyType = SystemProperty.valueOf(propertyStr);
        final long value = Long.parseLong(valueStr);

        final IProperty<?> property = systemPropertyService.getProperty(propertyType);
        if(!(property instanceof LongProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.subtract(value));
    }

    @URI(value = "/divLong/system/property/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object divLong(final @Parameter(3) String propertyStr,
                          final @Parameter(4) String valueStr,
                          final ApiContext context,
                          final SystemPropertyService systemPropertyService) {
        final SystemProperty propertyType = SystemProperty.valueOf(propertyStr);
        final long value = Long.parseLong(valueStr);

        final IProperty<?> property = systemPropertyService.getProperty(propertyType);
        if(!(property instanceof LongProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.divide(value));
    }

    @URI(value = "/mulLong/system/property/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object mulLong(final @Parameter(3) String propertyStr,
                          final @Parameter(4) String valueStr,
                          final ApiContext context,
                          final SystemPropertyService systemPropertyService) {
        final SystemProperty propertyType = SystemProperty.valueOf(propertyStr);
        final long value = Long.parseLong(valueStr);

        final IProperty<?> property = systemPropertyService.getProperty(propertyType);
        if(!(property instanceof LongProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.multiply(value));
    }

    @URI(value = "/addFloat/system/property/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object addFloat(final @Parameter(3) String propertyStr,
                           final @Parameter(4) String valueStr,
                           final ApiContext context,
                           final SystemPropertyService systemPropertyService) {
        final SystemProperty propertyType = SystemProperty.valueOf(propertyStr);
        final float value = Float.parseFloat(valueStr);

        final IProperty<?> property = systemPropertyService.getProperty(propertyType);
        if(!(property instanceof FloatProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.add(value));
    }

    @URI(value = "/subFloat/system/property/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object subFloat(final @Parameter(3) String propertyStr,
                           final @Parameter(4) String valueStr,
                           final ApiContext context,
                           final SystemPropertyService systemPropertyService) {
        final SystemProperty propertyType = SystemProperty.valueOf(propertyStr);
        final float value = Float.parseFloat(valueStr);

        final IProperty<?> property = systemPropertyService.getProperty(propertyType);
        if(!(property instanceof FloatProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.subtract(value));
    }

    @URI(value = "/divFloat/system/property/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object divFloat(final @Parameter(3) String propertyStr,
                           final @Parameter(4) String valueStr,
                           final ApiContext context,
                           final SystemPropertyService systemPropertyService) {
        final SystemProperty propertyType = SystemProperty.valueOf(propertyStr);
        final float value = Float.parseFloat(valueStr);

        final IProperty<?> property = systemPropertyService.getProperty(propertyType);
        if(!(property instanceof FloatProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.divide(value));
    }

    @URI(value = "/mulFloat/system/property/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object mulFloat(final @Parameter(3) String propertyStr,
                           final @Parameter(4) String valueStr,
                           final  ApiContext context,
                           final SystemPropertyService systemPropertyService) {
        final SystemProperty propertyType = SystemProperty.valueOf(propertyStr);
        final float value = Float.parseFloat(valueStr);

        final IProperty<?> property = systemPropertyService.getProperty(propertyType);
        if(!(property instanceof FloatProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.multiply(value));
    }

    @URI(value = "/addDouble/system/property/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object addDouble(final @Parameter(3) String propertyStr,
                            final @Parameter(4) String valueStr,
                            final ApiContext context,
                            final SystemPropertyService systemPropertyService) {
        final SystemProperty propertyType = SystemProperty.valueOf(propertyStr);
        final double value = Double.parseDouble(valueStr);

        final IProperty<?> property = systemPropertyService.getProperty(propertyType);
        if(!(property instanceof DoubleProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.add(value));
    }

    @URI(value = "/subDouble/system/property/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object subDouble(final @Parameter(3) String propertyStr,
                            final @Parameter(4) String valueStr,
                            final ApiContext context,
                            final SystemPropertyService systemPropertyService) {
        final SystemProperty propertyType = SystemProperty.valueOf(propertyStr);
        final double value = Double.parseDouble(valueStr);

        final IProperty<?> property = systemPropertyService.getProperty(propertyType);
        if(!(property instanceof DoubleProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.subtract(value));
    }

    @URI(value = "/divDouble/system/property/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object divDouble(final @Parameter(3) String propertyStr,
                            final @Parameter(4) String valueStr,
                            final ApiContext context,
                            final SystemPropertyService systemPropertyService) {
        final SystemProperty propertyType = SystemProperty.valueOf(propertyStr);
        final double value = Double.parseDouble(valueStr);

        final IProperty<?> property = systemPropertyService.getProperty(propertyType);
        if(!(property instanceof DoubleProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.divide(value));
    }

    @URI(value = "/mulDouble/system/property/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object mulDouble(final @Parameter(3) String propertyStr,
                            final @Parameter(4) String valueStr,
                            final ApiContext context,
                            final SystemPropertyService systemPropertyService) {
        final SystemProperty propertyType = SystemProperty.valueOf(propertyStr);
        final double value = Double.parseDouble(valueStr);

        final IProperty<?> property = systemPropertyService.getProperty(propertyType);
        if(!(property instanceof DoubleProperty up)) {
            return context.invalidPropertyMessage();
        }

        return new Message(200, up.multiply(value));
    }
    
}
