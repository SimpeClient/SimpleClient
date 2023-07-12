package simpleclient.feature.config;

import com.google.gson.JsonObject;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
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
    public AbstractWidget createWidget(Feature feature) {
        Function<Boolean, Component> text = value -> TextAdapter.adapt(displayText).copy().append(": ").append(value ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF);
        return Button.builder(text.apply(feature.getConfigValue(this)), button -> {
            boolean value = feature.getConfigValue(this);
            feature.setConfigValue(this, !value);
            button.setMessage(text.apply(!value));
        }).size(200, 20).build();
    }
}