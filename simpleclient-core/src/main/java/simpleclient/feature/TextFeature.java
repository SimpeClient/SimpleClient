package simpleclient.feature;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import simpleclient.adapter.ItemRendererAdapter;
import simpleclient.adapter.TextRendererAdapter;
import simpleclient.text.Style;
import simpleclient.text.Text;

public interface TextFeature {
    JsonObject getData();
    void setData(JsonObject data);

    public default JsonArray getDefaultFormat() {
        return new JsonArray();
    }

    public default String valueForParameter(String parameter) {
        return "";
    }

    public default String valueForDummyParameter(String parameter) {
        return "";
    }

    public default JsonArray getFormat() {
        JsonObject data = getData();
        if (!data.has("format")) data.add("format", getDefaultFormat());
        return data.get("format").getAsJsonArray();
    }

    public default void setFormat(JsonArray format) {
        JsonObject data = getData();
        data.add("format", format);
        setData(data);
    }

    default JsonObject text(String text, Style style) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "text");
        json.addProperty("value", text);
        json.add("style", style.serializeJson());
        return json;
    }

    default JsonObject parameter(String name, Style style) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "parameter");
        json.addProperty("value", name);
        json.add("style", style.serializeJson());
        return json;
    }

    public default void render(TextRendererAdapter textRenderer, ItemRendererAdapter itemRenderer, int width, int height, int x, int y) {
        textRenderer.render(toDummyText(), x, y);
    }

    public default void renderDummy(TextRendererAdapter textRenderer, ItemRendererAdapter itemRenderer, int width, int height, int x, int y) {
        textRenderer.render(toDummyText(), x, y);
    }

    public default int getWidth(TextRendererAdapter textRenderer) {
        return textRenderer.getWidth(toDummyText());
    }

    public default Text toText() {
        Text text = Text.empty();
        for (JsonElement element : getFormat()) {
            if (!element.isJsonObject()) continue;
            JsonObject object = element.getAsJsonObject();
            if (!object.has("type")) continue;
            if (!object.get("type").isJsonPrimitive()) continue;
            if (!object.get("type").getAsJsonPrimitive().isString()) continue;
            if (!object.has("value")) continue;
            if (!object.get("value").isJsonPrimitive()) continue;
            if (!object.get("value").getAsJsonPrimitive().isString()) continue;
            if (object.has("style") && !object.get("style").isJsonObject()) object.remove("style");
            if (!object.has("style")) object.add("style", new JsonObject());
            Style style = Style.deserializeJson(object.get("style").getAsJsonObject());
            if (object.get("type").getAsString().equals("text")) {
                text.append(Text.literal(object.get("value").getAsString()).setStyle(style));
            } else if (object.get("type").getAsString().equals("parameter")) {
                text.append(Text.literal(valueForParameter(object.get("value").getAsString())).setStyle(style));
            }
        }
        return text;
    }

    public default Text toDummyText() {
        Text text = Text.empty();
        for (JsonElement element : getFormat()) {
            if (!element.isJsonObject()) continue;
            JsonObject object = element.getAsJsonObject();
            if (!object.has("type")) continue;
            if (!object.get("type").isJsonPrimitive()) continue;
            if (!object.get("type").getAsJsonPrimitive().isString()) continue;
            if (!object.has("value")) continue;
            if (!object.get("value").isJsonPrimitive()) continue;
            if (!object.get("value").getAsJsonPrimitive().isString()) continue;
            if (object.has("style") && !object.get("style").isJsonObject()) object.remove("style");
            if (!object.has("style")) object.add("style", new JsonObject());
            Style style = Style.deserializeJson(object.get("style").getAsJsonObject());
            if (object.get("type").getAsString().equals("text")) {
                text.append(Text.literal(object.get("value").getAsString()).setStyle(style));
            } else if (object.get("type").getAsString().equals("parameter")) {
                text.append(Text.literal(valueForDummyParameter(object.get("value").getAsString())).setStyle(style));
            }
        }
        return text;
    }
}