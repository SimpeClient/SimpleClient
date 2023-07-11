package simpleclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.legacyfabric.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.legacyfabric.fabric.api.logger.v1.Logger;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.world.level.LevelInfo;
import simpleclient.adapter.LoggerAdapter;
import simpleclient.adapter.TranslationAdapter;
import simpleclient.adapter.TranslationAdapterImpl;
import simpleclient.feature.FeatureManager;
import simpleclient.util.DiscordRPC;

import java.net.InetSocketAddress;
import java.time.Instant;

public class SimpleClient implements ClientModInitializer {
    public static final LoggerAdapter LOGGER = new LoggerAdapter() {
        final Logger logger = Logger.get("SimpleClient");

        @Override
        public void info(String message, Object... args) {
            logger.info(message, args);
        }

        @Override
        public void error(String message, Object... args) {
            logger.error(message, args);
        }

        @Override
        public void debug(String message, Object... args) {
            logger.debug(message, args);
        }
    };
    public static String VERSION = "unknown";
    public static String MINECRAFT_VERSION = "1.8.9";

    @Override
    public void onInitializeClient() {
        VERSION = loadVersion();
        TranslationAdapter.INSTANCE = new TranslationAdapterImpl();
        FeatureManager.INSTANCE.init();
        DiscordRPC.INSTANCE.init(() -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player != null && mc.world != null) {
                if (DiscordRPC.INSTANCE.getIngameTimestamp() == null) DiscordRPC.INSTANCE.setIngameTimestamp(Instant.now());
                if (mc.getServer() == null) {
                    InetSocketAddress address = (InetSocketAddress) mc.getNetworkHandler().getClientConnection().getAddress();
                    return DiscordRPC.activity("Multiplayer", address.getHostName(), DiscordRPC.INSTANCE.getIngameTimestamp());
                } else {
                    PlayerListEntry entry = mc.getNetworkHandler().getPlayerListEntry(mc.player.getGameProfile().getId());
                    LevelInfo.GameMode mode = entry.getGameMode();
                    String gamemode = mode == LevelInfo.GameMode.CREATIVE ? "Creative Mode" : mode == LevelInfo.GameMode.SPECTATOR ? "Spectator Mode" : "Survival Mode";
                    return DiscordRPC.activity("Singleplayer", gamemode, DiscordRPC.INSTANCE.getIngameTimestamp());
                }
            } else {
                if (DiscordRPC.INSTANCE.getIngameTimestamp() != null) DiscordRPC.INSTANCE.setIngameTimestamp(null);
                return DiscordRPC.activity("Not playing", null, DiscordRPC.INSTANCE.getStartTimestamp());
            }
        });
        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> DiscordRPC.INSTANCE.close());
    }

    public String loadVersion() {
        return FabricLoader.getInstance().getModContainer("simpleclient").get().getMetadata().getVersion().getFriendlyString();
    }
}
