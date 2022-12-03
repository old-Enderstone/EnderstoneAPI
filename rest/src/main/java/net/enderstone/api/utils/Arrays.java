package net.enderstone.api.utils;

public class Arrays {

    public static <T> boolean contains(T[] arr, T value) {
        for(T v : arr) {
            if(v.equals(value)) return true;
        }
        return false;
    }

}
