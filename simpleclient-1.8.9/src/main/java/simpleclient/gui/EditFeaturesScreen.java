package simpleclient.gui;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Mouse;
import simpleclient.adapter.ItemRendererAdapter;
import simpleclient.adapter.ItemRendererAdapterImpl;
import simpleclient.adapter.TextRendererAdapter;
import simpleclient.adapter.TextRendererAdapterImpl;
import simpleclient.feature.*;
import simpleclient.text.Text;
import simpleclient.util.DrawUtil;

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
                    final float xPos = df.getXPos(width);
                    final float yPos = df.getYPos(height);
                    DrawUtil.border(xPos - 1, yPos - 1,
                            xPos + df.getWidth(textRenderer, itemRenderer, width, height) + 1,
                            yPos + df.getHeight(textRenderer, itemRenderer, width, height) + 1, 1.f, 1.f, 0xFFAAAAAA);
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
            float wSize = (width / 4 - 2 - count * 2) / count;
            float wX1 = 2 + (2 + wSize) * x;
            float wY1 = 2 + 2 + (2 + wSize) * y;
            float wX2 = (2 + wSize) * x + wSize;
            float wY2 = 2 + (2 + wSize) * y + wSize;
            // Background
            float corners = (wY2 - wY1) / 8;
            DrawUtil.roundedRectangle(wX1, wY1 + this.scroll, wX2, wY2 + this.scroll, corners, 0xFF555555);
            // Enable Button
            if (feature instanceof EnableableFeature ef) {
                float height = wSize / 6;
                DrawUtil.stadium(wX1 + wSize / 10, scroll + wY2 - wSize / 10 - wSize / 3 / 2,
                        wX1 + wSize / 10 + wSize / 3, scroll + wY2 - wSize / 10,
                        ef.isEnabled() ? 0xFF00FF00 : 0xFFFF0000);
                if (ef.isEnabled()) {
                    DrawUtil.circle(wX1 + wSize / 10 + wSize / 3 - height / 2,
                            scroll + wY2 - wSize / 10 - height / 2, height * 2 / 5, 0xFF000000);
                } else {
                    DrawUtil.circle(wX1 + wSize / 10 + height / 2, scroll + wY2 - wSize / 10 - height / 2,
                            height * 2 / 5, 0xFF000000);
                }
            }
            // Config Button
            if (feature.hasConfig()) {
                GlStateManager.pushMatrix();
                float height = wSize / 6;
                float cogwheelX = wX2 - wSize / 10 - height;
                float cogwheelY = scroll + wY2 - wSize / 10 - height;
                float degrees = 360.0F / 400 * ((float) (System.currentTimeMillis() % 4000) / 10);
                GlStateManager.translate(cogwheelX + height / 2, cogwheelY + height / 2, 0);
                // Rotate
                GlStateManager.pushMatrix();
                if (cogwheelX <= mouseX && mouseX < cogwheelX + height &&
                    cogwheelY <= mouseY && mouseY < cogwheelY + height) {
                    GlStateManager.rotate(degrees, 0.0F, 0.0F, 1.0F);
                }
                client.getTextureManager().bindTexture(new Identifier("simpleclient", "textures/settings.png"));
                DrawableHelper.drawTexture((int) (height / -2), (int) (height / -2), 0, 0, (int) height, (int) height, height, height);
                GlStateManager.popMatrix();
                GlStateManager.popMatrix();
            }
            // Name
            GlStateManager.pushMatrix();
            float scaleX = (wSize * 0.8F) / textRenderer.getWidth(feature.getName().split(" - ")[0]);
            float scaleY = (wSize * 0.3F) / textRenderer.getHeight();
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
                int cogwheelX = wX2 - wSize / 10 - height;
                int cogwheelY = scroll + wY2 - wSize / 10 - wSize / 3 / 2;
                if (feature.hasConfig() &&
                    cogwheelX <= mouseX && mouseX <= cogwheelX + h &&
                    cogwheelY <= mouseY && mouseY <= cogwheelY + h) {
                    client.setScreen(new EditFeatureConfigScreen(feature, this));
                } else if (feature instanceof EnableableFeature &&
                             wX1 <= mouseX && mouseX <= wX2 &&
                    wY1 + scroll <= mouseY && mouseY <= wY2 + scroll) {
                    ((EnableableFeature) feature).setEnabled(!((EnableableFeature) feature).isEnabled());
                }
            }
        } else active = null;
        super.mouseReleased(mouseX, mouseY, button);
    }

    private DraggableFeature getFeature(TextRendererAdapter textRenderer, ItemRendererAdapter itemRenderer, double mouseX, double mouseY) {
        for (Feature feature : FeatureManager.INSTANCE.getFeatures()) {
            if (feature instanceof DraggableFeature && ((DraggableFeature) feature).isEnabled()) {
                DraggableFeature df = (DraggableFeature) feature;
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
        scroll += Mouse.getDWheel() * height / 3000;
        super.handleMouse();
    }

    @Override
    public boolean shouldPauseGame() {
        return true;
    }
}