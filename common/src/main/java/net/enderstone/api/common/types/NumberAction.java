package net.enderstone.api.common.types;

import net.enderstone.api.common.properties.NumberProperty;

public interface NumberAction<T extends Number> {

    T perform(final NumberProperty<T> property, T number);

}
