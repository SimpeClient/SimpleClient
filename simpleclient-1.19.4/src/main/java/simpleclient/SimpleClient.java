package simpleclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simpleclient.feature.FeatureManager;

public class SimpleClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("simpleclient");
    public static String VERSION = "unknown";

    @Override
    public void onInitializeClient() {
        VERSION = loadVersion();
        FeatureManager.init();
    }

    public String loadVersion() {
        return FabricLoader.getInstance().getModContainer("simpleclient").get().getMetadata().getVersion().getFriendlyString();
    }
}
