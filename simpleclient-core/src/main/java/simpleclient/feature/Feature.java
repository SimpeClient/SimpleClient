package simpleclient.feature;

import com.google.gson.JsonObject;
import simpleclient.feature.config.ConfigEntry;
import simpleclient.util.JsonFile;

import java.util.ArrayList;
import java.util.List;

public class Feature {
    private final FeatureType type;
    private final List<ConfigEntry<?>> config = new ArrayList<>();

    public Feature(FeatureType type) {
        this.type = type;
    }

    public JsonObject getData() {
        JsonFile json = FeatureManager.INSTANCE.getJson();
        if (!json.has(getId())) json.set(getId(), new JsonObject());
        return json.getObject(getId());
    }

    public void setData(JsonObject data) {
        JsonFile json = FeatureManager.INSTANCE.getJson();
        json.set(getId(), data);
        json.save();
    }

    public void addConfigEntry(ConfigEntry<?> entry) {
        config.add(entry);
    }

    public <T> T getConfigValue(ConfigEntry<T> entry) {
        return entry.load(getData());
    }

    public <T> void setConfigValue(ConfigEntry<T> entry, T value) {
        JsonObject data = getData();
        entry.save(data, value);
        setData(data);
    }

    public boolean hasConfig() {
        return !config.isEmpty();
    }

    public List<ConfigEntry<?>> getConfig() {
        return config;
    }

    public String getId() {
        return type.getId();
    }

    public String getName() {
        return type.getName();
    }

    public FeatureType getType() {
        return type;
    }
}
