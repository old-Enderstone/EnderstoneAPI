package net.enderstone.api.repository;

import net.enderstone.api.Main;
import net.enderstone.api.common.properties.IProperty;
import net.enderstone.api.common.properties.SystemProperty;
import net.enderstone.api.impl.properties.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SystemPropertyRepository implements IRepository<SystemProperty, IProperty<?>> {

    @Override
    public void setupDatabase() {
        Main.connector.update("""        
                CREATE TABLE `SystemProperty` (
                  `property` varchar(128) PRIMARY KEY NOT NULL,
                  `value` varchar(1024)
                );
                """);
    }

    private IProperty<?> createProperty(SystemProperty property, String value) {
        return switch(property.type) {
            case STRING -> new StringSystemPropertyImpl(property, value, this);
            case BOOLEAN -> new BooleanSystemPropertyImpl(property, Boolean.parseBoolean(value), this);
            case INTEGER -> new IntegerSystemPropertyImpl(property, Integer.parseInt(value), this);
            case LONG -> new LongSystemPropertyImpl(property, Long.parseLong(value), this);
            case DOUBLE -> new DoubleSystemPropertyImpl(property, Double.parseDouble(value), this);
            case FLOAT -> new FloatSystemPropertyImpl(property, Float.parseFloat(value), this);
        };
    }

    public Collection<IProperty<?>> getAllProperties() {
        final ResultSet rs = Main.connector.query("select `property`, `value` from `SystemProperty` where 1=1;");
        final List<IProperty<?>> properties = new ArrayList<>();
        try {
            while(rs.next()) {
                final String propertyStr = rs.getString("property");
                final String value = rs.getString("value");

                properties.add(createProperty(SystemProperty.valueOf(propertyStr), value));
            }
            rs.close();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }

    @Override
    public boolean hasKey(SystemProperty key) {
        final ResultSet rs = Main.connector.query("select `property` from `SystemProperty` where `property`=?;", key.toString());
        try {
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(SystemProperty key, IProperty<?> value) {
        Main.connector.update("insert into `SystemProperty` values (?, ?);", key.toString(), value.get().toString());
    }

    @Override
    public void update(SystemProperty key, IProperty<?> value) {
        Main.connector.update("update `SystemProperty` set `value`=? where `property`=?;", value.get().toString(), key.toString());
    }

    @Override
    public IProperty<?> get(SystemProperty key) {
        final ResultSet rs = Main.connector.query("select `value` from `SystemProperty` where `property`=?;", key.toString());
        try {
            if(!rs.next()) return null;
            final String value = rs.getString("value");

            return createProperty(key, value);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(SystemProperty key) {
        Main.connector.update("delete from `SystemProperty` where `property`=?;", key.toString());
    }
}
