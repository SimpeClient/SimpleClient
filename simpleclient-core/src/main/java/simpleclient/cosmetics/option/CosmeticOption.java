package simpleclient.cosmetics.option;

import com.google.gson.JsonObject;
import simpleclient.feature.Feature;

public abstract class CosmeticOption<T> {
    private final String name;
    private final String id;

    public CosmeticOption(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public abstract T load(JsonObject json);
    public abstract void save(JsonObject json, T value);

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}