package net.enderstone.api.rest;

import com.bethibande.web.annotations.URI;
import net.enderstone.api.Main;
import net.enderstone.api.annotations.Parameter;
import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.common.properties.abstraction.*;
import net.enderstone.api.common.types.Message;
import net.enderstone.api.common.utils.Regex;

import java.util.UUID;

public class UserPropertyHandler {

    @URI(value = "/toggle/player/property/" + Regex.UUID + "/" + Regex.PROPERTY, type = URI.URIType.REGEX)
    public Message toggleProperty(@Parameter(3) String uId, @Parameter(4) String propertyStr) {
        final UUID uuid = UUID.fromString(uId);
        final UserProperty propertyType = UserProperty.valueOf(propertyStr);

        final IUserProperty<?> property = Main.userPropertyService.getUserProperty(uuid, propertyType);

        if(!(property instanceof BooleanUserProperty bp)) {
            return new Message(400, "Specified Property is not a boolean property.");
        }

        return new Message(200, bp.toggle());
    }

    @URI(value = "/set/player/property/" + Regex.UUID + "/" + Regex.PROPERTY + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
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

}
