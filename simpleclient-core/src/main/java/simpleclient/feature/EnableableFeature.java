package simpleclient.feature;

import com.google.gson.JsonObject;

public class EnableableFeature extends Feature {
    public EnableableFeature(FeatureType type) {
        super(type);
    }

    public boolean isEnabled() {
        JsonObject data = getData();
        if (!data.has("enabled")) data.addProperty("enabled", false);
        return data.get("enabled").getAsBoolean();
    }

    public void setEnabled(boolean enabled) {
        JsonObject data = getData();
        data.addProperty("enabled", enabled);
        setData(data);
    }
}