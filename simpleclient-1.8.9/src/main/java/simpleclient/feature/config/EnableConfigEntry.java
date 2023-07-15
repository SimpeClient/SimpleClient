package simpleclient.feature.config;

import com.google.gson.JsonObject;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import simpleclient.adapter.TextAdapter;
import simpleclient.feature.Feature;
import simpleclient.text.Text;

import java.util.function.Function;

public class EnableConfigEntry implements ConfigEntry<Boolean> {
    private String key;
    private boolean defaultValue;
    private Text displayText;

    public EnableConfigEntry(String key, Text displayText, boolean defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.displayText = displayText;
    }

    @Override
    public Boolean load(JsonObject json) {
        if (!json.has(key)) json.addProperty(key, defaultValue);
        return json.get(key).getAsBoolean();
    }

    @Override
    public void save(JsonObject json, Boolean value) {
        json.addProperty(key, value);
    }

    @Override
    public ButtonWidget createWidget(Feature feature) {
        Function<Boolean, String> text = value -> TextAdapter.adapt(displayText).asUnformattedString() + ": " + TextAdapter.adapt(Text.translatable(value ? "options.on" : "options.off")).asUnformattedString();
        return new ButtonWidget(-1, 0, 0, 200, 20, text.apply(feature.getConfigValue(this))) {
            @Override
            public void mouseReleased(int mouseX, int mouseY) {
                boolean value = feature.getConfigValue(EnableConfigEntry.this);
                feature.setConfigValue(EnableConfigEntry.this, !value);
                System.out.println(text.apply(!value));
                message = text.apply(!value);
            }
        };
    }
}