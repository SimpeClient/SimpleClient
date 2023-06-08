package simpleclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simpleclient.adapter.TranslationAdapter;
import simpleclient.adapter.TranslationAdapterImpl;
import simpleclient.feature.FeatureManager;
import simpleclient.feature.FeatureManagerImpl;

public class SimpleClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("simpleclient");
    public static String VERSION = "unknown";

    @Override
    public void onInitializeClient() {
        VERSION = loadVersion();
        TranslationAdapter.INSTANCE = new TranslationAdapterImpl();
        FeatureManager.INSTANCE = new FeatureManagerImpl();
        FeatureManager.INSTANCE.init();
    }

    public String loadVersion() {
        return FabricLoader.getInstance().getModContainer("simpleclient").get().getMetadata().getVersion().getFriendlyString();
    }
}
