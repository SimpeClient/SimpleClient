package simpleclient.feature.config;

import com.google.gson.JsonObject;
import net.minecraft.client.gui.components.AbstractOptionSliderButton;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import simpleclient.feature.Feature;

import java.util.function.Function;

public class FloatConfigEntry implements ConfigEntry<Float> {
    private String key;
    private float defaultValue;
    private Component displayText;
    private Function<Float, Component> valueText;

    public FloatConfigEntry(String key, Component displayText, float defaultValue, Function<Float, Component> valueText) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.displayText = displayText;
        this.valueText = valueText;
    }

    @Override
    public Float load(JsonObject json) {
        if (!json.has(key)) json.addProperty(key, defaultValue);
        return json.get(key).getAsFloat();
    }

    @Override
    public void save(JsonObject json, Float value) {
        json.addProperty(key, value);
    }

    @Override
    public AbstractWidget createWidget(Feature feature) {
        Function<Float, Component> text = value -> displayText.copy().append(": ").append(valueText.apply(value));
        float value = feature.getConfigValue(this);
        return new AbstractSliderButton(0, 0, 200, 20, text.apply(value), value) {
            @Override
            protected void updateMessage() {
                setMessage(text.apply((float) value));
            }

            @Override
            protected void applyValue() {
                feature.setConfigValue(FloatConfigEntry.this, (float) value);
            }
        };
    }
}