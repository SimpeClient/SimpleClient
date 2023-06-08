package simpleclient.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
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
        super(Component.translatable("simpleclient.edit_features"));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        TextRendererAdapter textRenderer = new TextRendererAdapterImpl(poseStack, font);
        ItemRendererAdapter itemRenderer = new ItemRendererAdapterImpl(poseStack);
        // Render Features
        for (Feature feature : FeatureManager.INSTANCE.getFeatures()) {
            if (feature instanceof RenderableFeature rf && rf.isEnabled()) {
                rf.renderDummy(textRenderer, itemRenderer, width, height);
                if (rf instanceof DraggableFeature df) {
                    int x1 = df.getXPos(width) - 1;
                    int y1 = df.getYPos(height) - 1;
                    int x2 = df.getXPos(width) + df.getWidth(textRenderer, itemRenderer, width, height) + 1;
                    int y2 = df.getYPos(height) + df.getHeight(textRenderer, itemRenderer, width, height) + 1;
                    fill(poseStack, x1 - 1, y1 - 1, x2 + 1, y1, 0xFFAAAAAA);
                    fill(poseStack, x1 - 1, y2, x2 + 1, y2 + 1, 0xFFAAAAAA);
                    fill(poseStack, x1 - 1, y1 - 1, x1, y2 + 1, 0xFFAAAAAA);
                    fill(poseStack, x2, y1 - 1, x2 + 1, y2 + 1, 0xFFAAAAAA);
                }
            }
        }
        // Render Buttons
        fill(poseStack, 0, 0, width / 4, height, 0x77000000);
        List<EnableableFeature> enableableFeatures = FeatureManager.INSTANCE.getEnableableFeatures();
        for (int i = 0; i < enableableFeatures.size(); i++) {
            EnableableFeature mod = enableableFeatures.get(i);
            int x = i % 3;
            int y = i / 3;
            int wSize = (width / 4 - 8) / 3;
            int wX1 = 2 + (2 + wSize) * x;
            int wY1 = 2 + 2 + (2 + wSize) * y;
            int wX2 = (2 + wSize) * x + wSize;
            int wY2 = 2 + (2 + wSize) * y + wSize;
            fill(poseStack, wX1, scroll + wY1, wX2, scroll + wY2, 0xff555555);
            fill(poseStack, wX1 + wSize / 10, scroll + wY2 - wSize / 10 - wSize / 3 / 2, wX1 + wSize / 10 + wSize / 3, scroll + wY2 - wSize / 10, mod.isEnabled() ? 0xff00ff00 : 0xffff0000);
            if (mod.isEnabled()) {
                fill(poseStack, wX1 + wSize / 10 + wSize / 3 / 2 + wSize / 20, scroll + wY2 - wSize / 10 - wSize / 3 / 2 + wSize / 20, wX1 + wSize / 10 + wSize / 3 - wSize / 20, scroll + wY2 - wSize / 10 - wSize / 20, 0xff000000);
            } else {
                fill(poseStack, wX1 + wSize / 10 + wSize / 20, scroll + wY2 - wSize / 10 - wSize / 3 / 2 + wSize / 20, wX1 + wSize / 10 + wSize / 3 / 2 - wSize / 20, scroll + wY2 - wSize / 10 - wSize / 20, 0xff000000);
            }
            poseStack.pushPose();
            float scaleX = (((float) wSize) * 0.8F) / textRenderer.getWidth(mod.getName().split(" - ")[0]);
            float scaleY = (((float) wSize) * 0.3F) / textRenderer.getHeight();
            float scale = Math.min(scaleX, scaleY);
            poseStack.scale(scale, scale, scale);
            int yOffset = 0;
            for (String str : mod.getName().split(" - ")) {
                textRenderer.render(Text.literal(str), (int) ((wX1 + (wSize - textRenderer.getWidth(str) * scale) / 2) / scale), (int) ((scroll + wY1 + wSize * 0.1F + yOffset) / scale), 0xffffff);
                yOffset += textRenderer.getHeight();
            }
            poseStack.popPose();
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        TextRendererAdapter textRenderer = new TextRendererAdapterImpl(null, font);
        ItemRendererAdapter itemRenderer = new ItemRendererAdapterImpl(null);
        active = getFeature(textRenderer, itemRenderer, mouseX, mouseY);
        if (active != null) {
            offsetX = mouseX - active.getXPos(width);
            offsetY = mouseY - active.getYPos(height);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        if (active != null) {
            active.setXPos((int) (mouseX - offsetX), width);
            active.setYPos((int) (mouseY - offsetY), height);
        }
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (active == null) {
            List<EnableableFeature> enableableFeatures = FeatureManager.INSTANCE.getEnableableFeatures();
            for (int i = 0; i < enableableFeatures.size(); i++) {
                EnableableFeature mod = enableableFeatures.get(i);
                int x = i % 3;
                int y = i / 3;
                int wSize = (width / 4 - 8) / 3;
                int wX1 = 2 + (2 + wSize) * x;
                int wY1 = 2 + 2 + (2 + wSize) * y;
                int wX2 = (2 + wSize) * x + wSize;
                int wY2 = 2 + (2 + wSize) * y + wSize;
                if (wX1 <= mouseX && mouseX <= wX2 &&
                    wY1 + scroll <= mouseY && mouseY <= wY2 + scroll) {
                    mod.setEnabled(!mod.isEnabled());
                }
            }
        } else active = null;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    private DraggableFeature getFeature(TextRendererAdapter textRenderer, ItemRendererAdapter itemRenderer, double mouseX, double mouseY) {
        for (Feature mod : FeatureManager.INSTANCE.getFeatures()) {
            if (mod instanceof DraggableFeature df && df.isEnabled()) {
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
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        scroll += amount * height / 50;
        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }
}