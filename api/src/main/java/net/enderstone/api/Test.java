package net.enderstone.api;

import net.enderstone.api.common.Player;
import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.common.properties.abstraction.IntegerUserProperty;

import java.util.UUID;

public class Test {

    public static void main(String[] args) {
        EnderStoneAPI api = EnderStoneAPI.getInstance();

        final UUID uId = UUID.fromString("d97d7d0a-4253-499c-b5b8-d401c1d88fc1");
        Player player = api.getPlayerById(uId);

        if(player == null) {
            player = api.createPlayer(uId, "Test");
            System.out.println("Created player");
        }

        final IntegerUserProperty coinsProperty = ((IntegerUserProperty)player.getProperty(UserProperty.COINS));
        coinsProperty.set(200);
        coinsProperty.multiply(2);
        System.out.println("value: " + coinsProperty.get());
    }

}
