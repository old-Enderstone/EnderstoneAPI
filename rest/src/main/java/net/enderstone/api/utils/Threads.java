package net.enderstone.api.utils;

public class Threads {

    public static void sleep(final long millis) {
        if(millis == 0) return;
        try {
            Thread.sleep(millis);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
