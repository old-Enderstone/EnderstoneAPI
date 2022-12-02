package net.enderstone.api.common.properties;

public interface IPropertyFactory {

    <T> IProperty<T> createNew(SystemProperty type, T value);

}
