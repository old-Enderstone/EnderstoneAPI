package net.enderstone.api.common.repository;

import net.enderstone.api.common.properties.NumberProperty;

public interface NumberPropertyRepository {

    <T extends Number> T add(final NumberProperty<T> property, T number);
    <T extends Number> T subtract(final NumberProperty<T> property, T number);
    <T extends Number> T divide(final NumberProperty<T> property, T number);
    <T extends Number> T multiply(final NumberProperty<T> property, T number);

}
