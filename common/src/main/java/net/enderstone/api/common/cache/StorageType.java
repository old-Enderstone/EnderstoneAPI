package net.enderstone.api.common.cache;

public enum StorageType {

    /**
     * Values stored in heap using soft references
     */
    SOFT_HEAP,
    HEAP,
    JSON_FILE,
    SERIALIZED_FILE;

}
