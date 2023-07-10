package simpleclient.gui;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.resource.language.I18n;
import simpleclient.adapter.TextAdapter;
import simpleclient.feature.Feature;
import simpleclient.text.Text;

import java.util.ArrayList;
import java.util.List;

public class EditFeatureConfigScreen extends Screen {
    private final Feature feature;
    private final Screen parent;

    public EditFeatureConfigScreen(Feature feature, Screen parent) {
        this.feature = feature;
        this.parent = parent;
    }

    @Override
    public void init() {
        List<ButtonWidget> widgets = new ArrayList<>();
        int y = 20 + textRenderer.fontHeight;
        widgets.add(new ButtonWidget(-1, 0, 0, 200, 20, I18n.translate("gui.done")));
        feature.getConfig().forEach(e -> widgets.add((ButtonWidget) e.createWidget(feature)));
        for (int i = 0; i < widgets.size(); i++) {
            ButtonWidget widget = widgets.get(i);
            widget.x = (width - widget.getWidth()) / 2;
            widget.y = y;
            y += widget.height + 2;
            buttons.add(widget);
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        renderBackground();
        String text = TextAdapter.adapt(Text.translatable("simpleclient.edit_config_of", feature.getName())).asUnformattedString();
        textRenderer.draw(text, (width - textRenderer.getStringWidth(text)) / 2, 10, 0xFFFFFFFF);
        super.render(mouseX, mouseY, delta);
    }

    @Override
    public void removed() {
        client.setScreen(parent);
    }
}