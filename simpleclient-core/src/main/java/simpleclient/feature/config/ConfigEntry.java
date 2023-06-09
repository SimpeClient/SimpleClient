package simpleclient.feature.config;

import com.google.gson.JsonObject;
import simpleclient.feature.Feature;

public interface ConfigEntry<T> {
    public T load(JsonObject json);
    public void save(JsonObject json, T value);
    public Object createWidget(Feature feature);
}