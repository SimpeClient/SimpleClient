package simpleclient.feature;

import com.google.gson.JsonObject;
import simpleclient.util.JsonFile;

public class Feature {
    private final FeatureType type;

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
