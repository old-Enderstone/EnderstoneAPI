package net.enderstone.api.rest;

import com.bethibande.web.annotations.QueryField;
import com.bethibande.web.annotations.URI;
import net.enderstone.api.ApiContext;
import net.enderstone.api.annotations.Parameter;
import net.enderstone.api.annotations.Whitelisted;
import net.enderstone.api.common.properties.AbstractProperty;
import net.enderstone.api.common.properties.PropertyKey;
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

        return propertyService.getProperty(propertyKey, owner).asString();
    }

    @URI(value = "/property/get-all/", type = URI.URIType.REGEX)
    public Object getAllByOwner(final @QueryField("owner") String ownerStr,
                                final PropertyService propertyService,
                                final ApiContext context) {
        if(ownerStr != null && !ownerStr.matches(Regex.UUID)) return context.invalidParameterMessage("owner");
        final UUID owner = ownerStr != null ? UUID.fromString(ownerStr): null;

        return propertyService.getPropertiesByOwner(owner);
    }

}
