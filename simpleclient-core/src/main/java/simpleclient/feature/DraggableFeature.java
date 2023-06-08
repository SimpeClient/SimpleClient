package simpleclient.feature;

import com.google.gson.JsonObject;
import simpleclient.adapter.ItemRendererAdapter;
import simpleclient.adapter.TextRendererAdapter;
import simpleclient.util.JsonFile;

public class DraggableFeature extends RenderableFeature {
    public DraggableFeature(FeatureType type) {
        super(type);
    }

    public void render(TextRendererAdapter textRenderer, ItemRendererAdapter itemRenderer, int width, int height, int x, int y) {}
    public void renderDummy(TextRendererAdapter textRenderer, ItemRendererAdapter itemRenderer, int width, int height, int x, int y) {}
    public double getDefaultX() {return 0.5;}
    public double getDefaultY() {return 0.5;}
    public int getWidth(TextRendererAdapter textRenderer, ItemRendererAdapter itemRenderer, int width, int height) {return 0;}
    public int getHeight(TextRendererAdapter textRenderer, ItemRendererAdapter itemRenderer, int width, int height) {return 0;}

    @Override
    public void render(TextRendererAdapter textRenderer, ItemRendererAdapter itemRenderer, int width, int height) {
        render(textRenderer, itemRenderer, width, height, getXPos(width), getYPos(height));
    }

    @Override
    public void renderDummy(TextRendererAdapter textRenderer, ItemRendererAdapter itemRenderer, int width, int height) {
        renderDummy(textRenderer, itemRenderer, width, height, getXPos(width), getYPos(height));
    }

    public double getX() {
        JsonFile json = FeatureManager.INSTANCE.getJson();
        if (!json.has(getId())) json.set(getId(), new JsonObject());
        if (!json.get(getId()).getAsJsonObject().has("x")) json.get(getId()).getAsJsonObject().addProperty("x", getDefaultX());
        return json.get(getId()).getAsJsonObject().get("x").getAsDouble();
    }

    public int getXPos(double width) {
        return (int) (width * getX());
    }

    public void setX(double x) {
        JsonObject json = FeatureManager.INSTANCE.getJson().getJson();
        if (!json.has(getId())) json.add(getId(), new JsonObject());
        json.get(getId()).getAsJsonObject().addProperty("x", x);
    }

    public void setXPos(int x, double width) {
        setX(1.0 / width * x);
    }

    public double getY() {
        JsonFile json = FeatureManager.INSTANCE.getJson();
        if (!json.has(getId())) json.set(getId(), new JsonObject());
        if (!json.get(getId()).getAsJsonObject().has("y")) json.get(getId()).getAsJsonObject().addProperty("y", getDefaultY());
        return json.get(getId()).getAsJsonObject().get("y").getAsDouble();
    }

    public int getYPos(double height) {
        return (int) (height * getY());
    }

    public void setY(double y) {
        JsonFile json = FeatureManager.INSTANCE.getJson();
        if (!json.has(getId())) json.set(getId(), new JsonObject());
        json.get(getId()).getAsJsonObject().addProperty("y", y);
    }

    public void setYPos(int y, double height) {
        setY(1.0 / height * y);
    }
}