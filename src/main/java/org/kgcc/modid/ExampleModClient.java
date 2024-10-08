package org.kgcc.modid;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.text.Text;

public class ExampleModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(ExampleMod.FANTAL_POLLUTION, (client, handler, buf, responseSender) -> {
            ExampleMod.LOGGER.info("Received total fantal pollution from server");
            var totalFantalPollution = buf.readInt();
            var playerSpecificDirtBlocksBroken = buf.readInt();
            var name = client.player.getDisplayName().getString();

            client.execute(() -> {
                if (client.player != null) {
                    client.player.sendMessage(Text.literal("全体の侵食度：" + totalFantalPollution));
                    client.player.sendMessage(Text.literal(name + "の侵食度：" + playerSpecificDirtBlocksBroken));
                }
            });
        });
    }
}