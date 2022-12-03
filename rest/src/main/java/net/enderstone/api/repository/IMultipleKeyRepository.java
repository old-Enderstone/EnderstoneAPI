package net.enderstone.api.repository;

import java.util.Map;

public interface IMultipleKeyRepository<K1, K2, V> extends IRepository<Map.Entry<K1, K2>, V> {

}
