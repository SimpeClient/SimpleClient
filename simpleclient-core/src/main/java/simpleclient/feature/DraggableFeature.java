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
        JsonObject data = getData();
       if (!data.has("x")) data.addProperty("x", getDefaultX());
        return data.get("x").getAsDouble();
    }

    public int getXPos(double width) {
        return (int) (width * getX());
    }

    public void setX(double x) {
        JsonObject data = getData();
        data.addProperty("x", x);
        setData(data);
    }

    public void setXPos(int x, double width) {
        setX(1.0 / width * x);
    }

    public double getY() {
        JsonObject data = getData();
        if (!data.has("y")) data.addProperty("y", getDefaultY());
        return data.get("y").getAsDouble();
    }

    public int getYPos(double height) {
        return (int) (height * getY());
    }

    public void setY(double y) {
        JsonObject data = getData();
        data.addProperty("y", y);
        setData(data);
    }

    public void setYPos(int y, double height) {
        setY(1.0 / height * y);
    }
}