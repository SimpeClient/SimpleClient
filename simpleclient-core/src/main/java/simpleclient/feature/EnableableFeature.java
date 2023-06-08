package simpleclient.feature;

import com.google.gson.JsonObject;

public class EnableableFeature extends Feature {
    public EnableableFeature(FeatureType type) {
        super(type);
    }

    public boolean isEnabled() {
        JsonObject json = FeatureManager.INSTANCE.getJson().getJson();
        if (!json.has(getId())) json.add(getId(), new JsonObject());
        if (!json.get(getId()).getAsJsonObject().has("enabled")) json.get(getId()).getAsJsonObject().addProperty("enabled", false);
        return json.get(getId()).getAsJsonObject().get("enabled").getAsBoolean();
    }

    public void setEnabled(boolean enabled) {
        JsonObject json = FeatureManager.INSTANCE.getJson().getJson();
        if (!json.has(getId())) json.add(getId(), new JsonObject());
        json.get(getId()).getAsJsonObject().addProperty("enabled", enabled);
    }
}