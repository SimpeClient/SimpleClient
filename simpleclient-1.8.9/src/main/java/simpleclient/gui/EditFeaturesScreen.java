package simpleclient.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;
import org.lwjgl.input.Mouse;
import simpleclient.adapter.ItemRendererAdapter;
import simpleclient.adapter.ItemRendererAdapterImpl;
import simpleclient.adapter.TextRendererAdapter;
import simpleclient.adapter.TextRendererAdapterImpl;
import simpleclient.feature.*;
import simpleclient.text.Text;

import java.util.List;

public class EditFeaturesScreen extends Screen {
    public DraggableFeature active;
    public double offsetX = 0;
    public double offsetY = 0;
    public int scroll = 0;

    public EditFeaturesScreen() {
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        TextRendererAdapter textRenderer = new TextRendererAdapterImpl(client.textRenderer);
        ItemRendererAdapter itemRenderer = new ItemRendererAdapterImpl();
        // Render Renderable Features
        for (Feature feature : FeatureManager.INSTANCE.getFeatures()) {
            if (feature instanceof RenderableFeature rf && rf.isEnabled()) {
                rf.renderDummy(textRenderer, itemRenderer, width, height);
                if (rf instanceof DraggableFeature df) {
                    int x1 = df.getXPos(width) - 1;
                    int y1 = df.getYPos(height) - 1;
                    int x2 = df.getXPos(width) + df.getWidth(textRenderer, itemRenderer, width, height) + 1;
                    int y2 = df.getYPos(height) + df.getHeight(textRenderer, itemRenderer, width, height) + 1;
                    DrawableHelper.fill(x1 - 1, y1 - 1, x2 + 1, y1, 0xFFAAAAAA);
                    DrawableHelper.fill(x1 - 1, y2, x2 + 1, y2 + 1, 0xFFAAAAAA);
                    DrawableHelper.fill(x1 - 1, y1 - 1, x1, y2 + 1, 0xFFAAAAAA);
                    DrawableHelper.fill(x2, y1 - 1, x2 + 1, y2 + 1, 0xFFAAAAAA);
                }
            }
        }
        // Render Enableable Features
        DrawableHelper.fill(0, 0, width / 4, height, 0x77000000);
        List<Feature> features = FeatureManager.INSTANCE.getFeatures();
        for (int i = 0; i < features.size(); i++) {
            Feature feature = features.get(i);
            int count = (int) Math.ceil((double) width / 4 / 100);
            int x = i % count;
            int y = i / count;
            int wSize = (width / 4 - 2 - count * 2) / count;
            int wX1 = 2 + (2 + wSize) * x;
            int wY1 = 2 + 2 + (2 + wSize) * y;
            int wX2 = (2 + wSize) * x + wSize;
            int wY2 = 2 + (2 + wSize) * y + wSize;
            // Background
            DrawableHelper.fill(wX1, scroll + wY1, wX2, scroll + wY2, 0xff555555);
            // Enable Button
            if (feature instanceof EnableableFeature ef) {
                DrawableHelper.fill(wX1 + wSize / 10, scroll + wY2 - wSize / 10 - wSize / 3 / 2, wX1 + wSize / 10 + wSize / 3, scroll + wY2 - wSize / 10, ef.isEnabled() ? 0xff00ff00 : 0xffff0000);
                if (ef.isEnabled()) {
                    DrawableHelper.fill(wX1 + wSize / 10 + wSize / 3 / 2 + wSize / 20, scroll + wY2 - wSize / 10 - wSize / 3 / 2 + wSize / 20, wX1 + wSize / 10 + wSize / 3 - wSize / 20, scroll + wY2 - wSize / 10 - wSize / 20, 0xff000000);
                } else {
                    DrawableHelper.fill(wX1 + wSize / 10 + wSize / 20, scroll + wY2 - wSize / 10 - wSize / 3 / 2 + wSize / 20, wX1 + wSize / 10 + wSize / 3 / 2 - wSize / 20, scroll + wY2 - wSize / 10 - wSize / 20, 0xff000000);
                }
            }
            // Config Button
            if (feature.hasConfig()) {
                GlStateManager.pushMatrix();
                int h = wSize / 3 / 2;
                int cogwheelX = wX1 + wSize / 10 * 4 + wSize / 3;
                int cogwheelY = scroll + wY2 - wSize / 10 - wSize / 3 / 2;
                if (cogwheelX <= mouseX && mouseX <= cogwheelX + h &&
                    cogwheelY <= mouseY && mouseY <= cogwheelY + h) {
                    GlStateManager.rotate((float) (System.currentTimeMillis() % 4000) / 400, cogwheelX + h / 2, cogwheelY + h / 2, 0.0F);
                }
                client.getTextureManager().bindTexture(new Identifier("simpleclient", "textures/settings.png"));
                DrawableHelper.drawTexture(cogwheelX, cogwheelY, 0, 0, h, h, h, h);
                GlStateManager.popMatrix();
            }
            // Name
            GlStateManager.pushMatrix();
            float scaleX = (((float) wSize) * 0.8F) / textRenderer.getWidth(feature.getName().split(" - ")[0]);
            float scaleY = (((float) wSize) * 0.3F) / textRenderer.getHeight();
            float scale = Math.min(scaleX, scaleY);
            GlStateManager.scale(scale, scale, scale);
            int yOffset = 0;
            for (String str : feature.getName().split(" - ")) {
                textRenderer.render(Text.literal(str), (int) ((wX1 + (wSize - textRenderer.getWidth(str) * scale) / 2) / scale), (int) ((scroll + wY1 + wSize * 0.1F + yOffset) / scale), 0xffffff);
                yOffset += textRenderer.getHeight();
            }
            GlStateManager.popMatrix();
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        TextRendererAdapter textRenderer = new TextRendererAdapterImpl(client.textRenderer);
        ItemRendererAdapter itemRenderer = new ItemRendererAdapterImpl();
        active = getFeature(textRenderer, itemRenderer, mouseX, mouseY);
        if (active != null) {
            offsetX = mouseX - active.getXPos(width);
            offsetY = mouseY - active.getYPos(height);
        }
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseDragged(int mouseX, int mouseY, int button, long mouseLastClicked) {
        if (active != null) {
            active.setXPos((int) (mouseX - offsetX), width);
            active.setYPos((int) (mouseY - offsetY), height);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {
        if (active == null) {
            List<Feature> features = FeatureManager.INSTANCE.getFeatures();
            for (int i = 0; i < features.size(); i++) {
                Feature feature = features.get(i);
                int count = (int) Math.ceil((double) width / 4 / 100);
                int x = i % count;
                int y = i / count;
                int wSize = (width / 4 - 2 - count * 2) / count;
                int wX1 = 2 + (2 + wSize) * x;
                int wY1 = 2 + 2 + (2 + wSize) * y;
                int wX2 = (2 + wSize) * x + wSize;
                int wY2 = 2 + (2 + wSize) * y + wSize;
                int h = wSize / 3 / 2;
                int cogwheelX = wX1 + wSize / 10 * 4 + wSize / 3;
                int cogwheelY = scroll + wY2 - wSize / 10 - wSize / 3 / 2;
                if (feature.hasConfig() &&
                    cogwheelX <= mouseX && mouseX <= cogwheelX + h &&
                    cogwheelY <= mouseY && mouseY <= cogwheelY + h) {
                    client.setScreen(new EditFeatureConfigScreen(feature, this));
                } else if (feature instanceof EnableableFeature ef &&
                             wX1 <= mouseX && mouseX <= wX2 &&
                    wY1 + scroll <= mouseY && mouseY <= wY2 + scroll) {
                    ef.setEnabled(!ef.isEnabled());
                }
            }
        } else active = null;
        super.mouseReleased(mouseX, mouseY, button);
    }

    private DraggableFeature getFeature(TextRendererAdapter textRenderer, ItemRendererAdapter itemRenderer, double mouseX, double mouseY) {
        for (Feature feature : FeatureManager.INSTANCE.getFeatures()) {
            if (feature instanceof DraggableFeature df && df.isEnabled()) {
                int x1 = df.getXPos(width) - 1;
                int y1 = df.getYPos(height) - 1;
                int x2 = df.getXPos(width) + df.getWidth(textRenderer, itemRenderer, width, height) + 1;
                int y2 = df.getYPos(height) + df.getHeight(textRenderer, itemRenderer, width, height) + 1;
                if (x1 <= mouseX && mouseX <= x2 &&
                    y1 <= mouseY && mouseY <= y2) return df;
            }
        }
        return null;
    }

    @Override
    public void removed() {
        FeatureManager.INSTANCE.getJson().save();
    }

    @Override
    public void handleMouse() {
        scroll += Mouse.getDWheel() * height / 30;
        super.handleMouse();
    }

    @Override
    public boolean shouldPauseGame() {
        return true;
    }
}