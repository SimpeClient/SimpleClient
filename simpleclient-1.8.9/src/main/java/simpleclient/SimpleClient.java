package simpleclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.util.log.LogLevel;
import net.legacyfabric.fabric.api.logger.v1.Logger;
import simpleclient.adapter.LoggerAdapter;
import simpleclient.adapter.TranslationAdapter;
import simpleclient.adapter.TranslationAdapterImpl;
import simpleclient.feature.FeatureManager;

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

    @Override
    public void onInitializeClient() {
        VERSION = loadVersion();
        TranslationAdapter.INSTANCE = new TranslationAdapterImpl();
        FeatureManager.INSTANCE.init();
    }

    public String loadVersion() {
        return FabricLoader.getInstance().getModContainer("simpleclient").get().getMetadata().getVersion().getFriendlyString();
    }
}
