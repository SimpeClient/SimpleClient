package simpleclient.feature;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import simpleclient.adapter.ItemRendererAdapter;
import simpleclient.adapter.TextRendererAdapter;
import simpleclient.text.LiteralText;
import simpleclient.text.Style;
import simpleclient.text.Text;

public class DraggableTextFeature extends DraggableFeature {
    public DraggableTextFeature(FeatureType type) {
        super(type);
    }

    @Override
    public void render(TextRendererAdapter textRenderer, ItemRendererAdapter itemRenderer, int width, int height, int x, int y) {
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
            Text text = null;
            if (object.get("type").getAsString().equals("text")) {
                text = Text.literal(object.get("value").getAsString()).setStyle(style);
            } else if (object.get("type").getAsString().equals("parameter")) {
                text = Text.literal(valueForParameter(object.get("value").getAsString())).setStyle(style);
            }
            textRenderer.render(text, x, y);
            x += textRenderer.getWidth(text);
        }
    }

    @Override
    public void renderDummy(TextRendererAdapter textRenderer, ItemRendererAdapter itemRenderer, int width, int height, int x, int y) {
        for (JsonElement element : getFormat()) {
            if (!element.isJsonObject()) continue;
            JsonObject object = element.getAsJsonObject();
            if (!object.has("type")) continue;
            if (!object.has("value")) continue;
            if (object.has("style")) {
                if (!object.get("style").isJsonObject()) object.remove("style");
            } else object.add("style", new JsonObject());
            Style style = Style.deserializeJson(object.get("style").getAsJsonObject());
            Text text = null;
            if (object.get("type").getAsString().equals("text")) {
                text = Text.literal(object.get("value").getAsString()).setStyle(style);
            } else if (object.get("type").getAsString().equals("parameter")) {
                text = Text.literal(valueForDummyParameter(object.get("value").getAsString())).setStyle(style);
            }
            textRenderer.render(text, x, y);
            x += textRenderer.getWidth(text);
        }
    }

    @Override
    public int getWidth(TextRendererAdapter textRenderer, ItemRendererAdapter itemRenderer, int width, int height) {
        int totalWidth = 0;
        for (JsonElement element : getFormat()) {
            if (!element.isJsonObject()) continue;
            JsonObject object = element.getAsJsonObject();
            if (!object.has("type")) continue;
            if (!object.get("type").isJsonPrimitive()) continue;
            if (!object.get("type").getAsJsonPrimitive().isString()) continue;
            if (!object.has("value")) continue;
            if (!object.get("value").isJsonPrimitive()) continue;
            if (!object.get("value").getAsJsonPrimitive().isString()) continue;
            if (object.has("style")) {
                if (!object.get("style").isJsonObject()) object.remove("style");
            } else object.add("style", new JsonObject());
            Style style = Style.deserializeJson(object.get("style").getAsJsonObject());
            Text text = null;
            if (object.get("type").getAsString().equals("text")) {
                text = Text.literal(object.get("value").getAsString()).setStyle(style);
            } else if (object.get("type").getAsString().equals("parameter")) {
                text = Text.literal(valueForDummyParameter(object.get("value").getAsString())).setStyle(style);
            }
            totalWidth += textRenderer.getWidth(text);
        }
        return totalWidth;
    }

    @Override
    public int getHeight(TextRendererAdapter textRenderer, ItemRendererAdapter itemRenderer, int width, int height) {
        return textRenderer.getHeight() - 2;
    }

    public String valueForParameter(String parameter) {
        return "";
    }

    public String valueForDummyParameter(String parameter) {
        return "";
    }

    public JsonArray getDefaultFormat() {
        return new JsonArray();
    }

    public JsonArray getFormat() {
        JsonObject json = FeatureManager.INSTANCE.getJson().getJson();
        if (!json.has(getId())) json.add(getId(), new JsonObject());
        if (!json.get(getId()).getAsJsonObject().has("format")) json.get(getId()).getAsJsonObject().add("format", getDefaultFormat());
        return json.get(getId()).getAsJsonObject().get("format").getAsJsonArray();
    }

    public void setFormat(JsonArray format) {
        JsonObject json = FeatureManager.INSTANCE.getJson().getJson();
        if (!json.has(getId())) json.add(getId(), new JsonObject());
        if (json.get(getId()).getAsJsonObject().has("format")) json.get(getId()).getAsJsonObject().remove("format");
        json.get(getId()).getAsJsonObject().add("format", format);
    }

    protected JsonObject text(String text, Style style) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "text");
        json.addProperty("value", text);
        json.add("style", style.serializeJson());
        return json;
    }

    protected JsonObject parameter(String name, Style style) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "parameter");
        json.addProperty("value", name);
        json.add("style", style.serializeJson());
        return json;
    }
}