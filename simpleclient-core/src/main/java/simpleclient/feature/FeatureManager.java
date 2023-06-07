package simpleclient.feature;

import simpleclient.SimpleClient;
import simpleclient.util.JsonFile;

import java.util.ArrayList;
import java.util.List;

public class FeatureManager {
    public static final List<Feature> FEATURES = new ArrayList<>();
    public static JsonFile JSON = new JsonFile("mods.json");

    public static void init() {
        String features = "Features:";
        String missingFeatures = "Missing Features:";
        for (FeatureType type : FeatureType.values()) {
            boolean missing = FEATURES.stream().filter(f -> f.getType() == type).findAny().isEmpty();
            if (missing) missingFeatures += "\n- " + type.getName();
            else features += "\n- " + type.getName();
        }
        SimpleClient.LOGGER.info(features + missingFeatures);
    }
}
