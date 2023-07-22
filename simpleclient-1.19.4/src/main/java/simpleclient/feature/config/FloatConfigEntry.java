package simpleclient.feature.config;

import com.google.gson.JsonObject;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import simpleclient.adapter.TextAdapter;
import simpleclient.feature.Feature;
import simpleclient.text.Text;

import java.util.function.Function;

public class FloatConfigEntry implements ConfigEntry<Float> {
    private String key;
    private float defaultValue;
    private Text displayText;
    private Function<Float, Text> valueText;

    public FloatConfigEntry(String key, Text displayText, float defaultValue, Function<Float, Text> valueText) {
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
        Function<Float, Component> text = value -> TextAdapter.adapt(displayText).copy().append(": ").append(TextAdapter.adapt(valueText.apply(value)));
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