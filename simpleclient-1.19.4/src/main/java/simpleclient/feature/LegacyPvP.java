package simpleclient.feature;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import simpleclient.SimpleClient;
import simpleclient.gui.SimpleClientToast;
import simpleclient.item.Item;
import simpleclient.item.ItemStack;
import simpleclient.text.Text;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class LegacyPvP {
    public static boolean ENABLED = false;
    public static boolean BLOCKING = false;
    private static final Set<UUID> blockingPlayers = new HashSet<>();

    public static void init() {
        ClientEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity == Minecraft.getInstance().player) {
                BLOCKING = false;
                blockingPlayers.clear();
            }
        });
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            ByteBuf buf = Unpooled.buffer();
            buf.writeBytes(SimpleClient.VERSION.getBytes());
            ClientPlayNetworking.send(new ResourceLocation("simpleclient", "handshake"), new FriendlyByteBuf(buf));
        });
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> LegacyPvP.ENABLED = false);
        ClientPlayNetworking.registerGlobalReceiver(new ResourceLocation("simpleclient", "legacypvp"), (client, handler, buf, sender) -> {
            byte method = buf.readByte();
            if (method == 0) {
                LegacyPvP.ENABLED = true;
                client.getToasts().addToast(new SimpleClientToast(
                        new ItemStack(new Item("minecraft:golden_sword")),
                        Text.literal("Legacy PvP ist auf"),
                        Text.literal("diesem Server aktiviert"),
                        1000 * 10
                ));
            }
            if (method == 1) LegacyPvP.ENABLED = false;
            if (method == 2) blockingPlayers.add(buf.readUUID());
            if (method == 3) blockingPlayers.remove(buf.readUUID());
        });
    }

    public static boolean isBlocking(UUID uuid) {
        return (uuid.equals(Minecraft.getInstance().player.getUUID()) && BLOCKING) || blockingPlayers.contains(uuid);
    }

    public static boolean isBlocking(Player player) {
        return isBlocking(player.getUUID());
    }
}