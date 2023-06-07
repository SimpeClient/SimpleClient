package simpleclient.feature;

import simpleclient.adapter.ItemRendererAdapter;
import simpleclient.adapter.TextRendererAdapter;

public class RenderableFeature extends EnableableFeature {
    public RenderableFeature(FeatureType type) {
        super(type);
    }

    public void render(TextRendererAdapter textRenderer, ItemRendererAdapter itemRenderer, int width, int height) {}
    public void renderDummy(TextRendererAdapter textRenderer, ItemRendererAdapter itemRenderer, int width, int height) {}
}