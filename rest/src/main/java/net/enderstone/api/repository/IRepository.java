package net.enderstone.api.repository;

public interface IRepository<K, V> {

    void setupDatabase();

    boolean hasKey(final K key);

    void insert(final K key, final V value);
    void update(final K key, final V value);

    V get(final K key);

    void delete(final K key);

}
