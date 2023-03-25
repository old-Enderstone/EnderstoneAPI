package net.enderstone.api.tests;

import net.enderstone.api.RestAPI;
import net.enderstone.api.utils.Arrays;

import java.util.ConcurrentModificationException;

public class TestUtil {

    public static void before() {
        RestAPI.main(Arrays.of(RestAPI.ARG_UNIT_TEST));
    }

    public static void after() {
        try {
            RestAPI.exit(0);
        } catch(ConcurrentModificationException ignored) { }
    }

}
