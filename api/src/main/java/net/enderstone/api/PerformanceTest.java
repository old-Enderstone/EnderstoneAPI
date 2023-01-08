package net.enderstone.api;

import net.enderstone.api.common.EPlayer;
import net.enderstone.api.common.properties.impl.IntProperty;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class PerformanceTest {

    public static void main(String[] args) {
        final EnderStoneAPI api = EnderStoneAPI.getInstance();
        final UUID uId = UUID.fromString("d97d7d0a-4253-499c-b5b8-d401c1d88fc1");
        EPlayer player = api.getPlayerById(uId);
        if(player == null) {
            player = api.createPlayer(uId, "Test");
            System.out.println("Created player");
        }
        final IntProperty coinsProperty = (IntProperty) player.getCoinsProperty();

        long time = 0;
        long times = 200;
        for(int i = 0; i < times; i++) {
            final int number = ThreadLocalRandom.current().nextInt();
            final long start = System.currentTimeMillis();

            coinsProperty.set(number);

            final long end = System.currentTimeMillis();
            time += end - start;

            if((i + 1) % 1000 == 0) System.out.println((int)((double)(i+1) / (double)times*100));

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("total: " + TimeUnit.MILLISECONDS.toSeconds(time) + " seconds");
        System.out.println("Requests per second: " + (times / TimeUnit.MILLISECONDS.toSeconds(time)));
        System.out.println("avg per request: " + (time / times) + " ms");
    }

}
