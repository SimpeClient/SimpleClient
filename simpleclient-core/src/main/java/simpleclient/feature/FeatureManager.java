package simpleclient.feature;

import simpleclient.SimpleClient;
import simpleclient.util.JsonFile;

import java.util.ArrayList;
import java.util.List;

public class FeatureManager {
    public static FeatureManager INSTANCE = null;
    private final List<Feature> features = new ArrayList<>();
    private final JsonFile json = new JsonFile("features.json");

    public FeatureManager() {
        json.load();
    }

    public void init() {
        SimpleClient.LOGGER.info("Features:");
        for (FeatureType type : FeatureType.values()) {
            if (features.stream().anyMatch(f -> f.getType() == type)) {
                SimpleClient.LOGGER.info("- " + type.getName());
            }
        }
        SimpleClient.LOGGER.info("Missing Features:");
        for (FeatureType type : FeatureType.values()) {
            if (type.getAvailableMinecraftVersions().contains(SimpleClient.MINECRAFT_VERSION) &&
                    features.stream().noneMatch(f -> f.getType() == type)) {
                SimpleClient.LOGGER.info("- " + type.getName());
            }
        }
        features.forEach(Feature::init);
    }

    public void addFeature(Feature feature) {
        features.add(feature);
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public List<EnableableFeature> getEnableableFeatures() {
        List<EnableableFeature> enableableFeatures = new ArrayList<>();
        features.forEach(f -> {if (f instanceof EnableableFeature) enableableFeatures.add((EnableableFeature) f);});
        return enableableFeatures;
    }

    public JsonFile getJson() {
        return json;
    }
}
