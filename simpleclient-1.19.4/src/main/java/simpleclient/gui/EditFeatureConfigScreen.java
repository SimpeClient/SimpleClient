package simpleclient.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import simpleclient.feature.Feature;

import java.util.ArrayList;
import java.util.List;

public class EditFeatureConfigScreen extends Screen {
    private final Feature feature;
    private final Screen parent;

    public EditFeatureConfigScreen(Feature feature, Screen parent) {
        super(Component.translatable("simpleclient.edit_feature_config"));
        this.feature = feature;
        this.parent = parent;
    }

    @Override
    protected void init() {
        List<AbstractWidget> widgets = new ArrayList<>();
        int y = 20 + font.lineHeight;
        widgets.add(Button.builder(CommonComponents.GUI_DONE, button -> minecraft.setScreen(parent)).size(200, 20).build());
        feature.getConfig().forEach(e -> widgets.add((AbstractWidget) e.createWidget(feature)));
        for (int i = 0; i < widgets.size(); i++) {
            AbstractWidget widget = widgets.get(i);
            widget.setX((width - widget.getWidth()) / 2);
            widget.setY(y);
            y += widget.getHeight() + 2;
            addRenderableWidget(widget);
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        renderBackground(poseStack);
        Component text = Component.translatable("simpleclient.edit_config_of", feature.getName());
        GuiComponent.drawCenteredString(poseStack, font, text, width / 2, 10, 0xFFFFFFFF);
        super.render(poseStack, mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        minecraft.setScreen(parent);
    }
}