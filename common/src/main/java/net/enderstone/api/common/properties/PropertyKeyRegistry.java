package net.enderstone.api.common.properties;

import net.enderstone.api.common.properties.impl.BooleanProperty;
import net.enderstone.api.common.properties.impl.DoubleProperty;
import net.enderstone.api.common.properties.impl.FloatProperty;
import net.enderstone.api.common.properties.impl.IntProperty;
import net.enderstone.api.common.properties.impl.LocaleProperty;
import net.enderstone.api.common.properties.impl.LongProperty;
import net.enderstone.api.common.properties.impl.StringProperty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class PropertyKeyRegistry {

    private final HashMap<String, PropertyKey<?>> keys = new HashMap<>();
    private Consumer<AbstractProperty<?>> onUpdate;

    public Collection<PropertyKey<?>> getPropertyKeys() {
        return keys.values();
    }

    public Set<String> getKeys() {
        return keys.keySet();
    }

    public PropertyKey<?> getKeyByIdentifier(final String identifier) {
        return keys.get(identifier);
    }

    public void setOnUpdate(final Consumer<AbstractProperty<?>> onUpdate) {
        this.onUpdate = onUpdate;
    }

    private <T> void onUpdate(final AbstractProperty<T> property) {
        onUpdate.accept(property);
    }

    public PropertyKey<Boolean> createBooleanProperty(final String label,
                                                      final Boolean def,
                                                      final boolean nullable) {
        return createKey(label, def, nullable, (key) -> new BooleanProperty(key, null), this::onUpdate);
    }

    public PropertyKey<Double> createDoubleKey(final String label,
                                               final Double def,
                                               final boolean nullable) {
        return createKey(label, def, nullable, (key) -> new DoubleProperty(key, null), this::onUpdate);
    }

    public PropertyKey<Float> createFloatKey(final String label,
                                             final Float def,
                                             final boolean nullable) {
        return createKey(label, def, nullable, (key) -> new FloatProperty(key, null), this::onUpdate);
    }

    public PropertyKey<Long> createLongKey(final String label,
                                           final Long def,
                                           final boolean nullable) {
        return createKey(label, def, nullable, (key) -> new LongProperty(key, null), this::onUpdate);
    }

    public PropertyKey<Locale> createLocaleKey(final String label,
                                               final Locale def,
                                               final boolean nullable) {
        return createKey(label, def, nullable, (key) -> new LocaleProperty(key, null), this::onUpdate);
    }

    public PropertyKey<String> createStringKey(final String label,
                                               final String def,
                                               final boolean nullable) {
        return createKey(label, def, nullable, (key) -> new StringProperty(key, null), this::onUpdate);
    }

    public PropertyKey<Integer> createIntKey(final String label,
                                             final Integer def,
                                             final boolean nullable) {
        return createKey(label, def, nullable, (key) -> new IntProperty(key, null), this::onUpdate);
    }

    public <T> PropertyKey<T> createKey(final String label,
                                        final T def,
                                        final boolean nullable,
                                        final Function<PropertyKey<T>, AbstractProperty<T>> supplier,
                                        final Consumer<AbstractProperty<T>> onUpdate) {
        final PropertyKey<T> key = new PropertyKey<>(label, def, nullable, supplier, onUpdate);
        keys.put(label, key);

        return key;
    }

}
