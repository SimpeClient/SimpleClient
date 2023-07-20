package simpleclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simpleclient.adapter.LoggerAdapter;
import simpleclient.adapter.TranslationAdapter;
import simpleclient.adapter.TranslationAdapterImpl;
import simpleclient.feature.FeatureManager;
import simpleclient.util.DiscordRPC;

import java.net.InetSocketAddress;
import java.time.Instant;

public class SimpleClient implements ClientModInitializer {
    public static final LoggerAdapter LOGGER = new LoggerAdapter() {
        Logger logger = LoggerFactory.getLogger("SimpleClient");

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
    public static String MINECRAFT_VERSION = "1.20";

    @Override
    public void onInitializeClient() {
        VERSION = loadVersion();
        TranslationAdapter.INSTANCE = new TranslationAdapterImpl();
        FeatureManager.INSTANCE.init();
        DiscordRPC.INSTANCE.init(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && mc.level != null) {
                if (DiscordRPC.INSTANCE.getIngameTimestamp() == null) DiscordRPC.INSTANCE.setIngameTimestamp(Instant.now());
                if (mc.getSingleplayerServer() == null) {
                    InetSocketAddress address = (InetSocketAddress) mc.getConnection().getConnection().getRemoteAddress();
                    return DiscordRPC.activity("Multiplayer", address.getHostString(), DiscordRPC.INSTANCE.getIngameTimestamp());
                } else {
                    String gamemode = mc.player.isCreative() ? "Creative Mode" : mc.player.isSpectator() ? "Spectator Mode" : "Survival Mode";
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
