package simpleclient.feature.config;

import com.google.gson.JsonObject;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PagedEntryListWidget;
import net.minecraft.client.gui.widget.SliderWidget;
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
    public ButtonWidget createWidget(Feature feature) {
        Function<Float, String> text = value -> TextAdapter.adapt(displayText).asUnformattedString() + ": " + TextAdapter.adapt(valueText.apply(value)).asUnformattedString();
        return new SliderWidget(
                new PagedEntryListWidget.Listener() {
                    @Override
                    public void setBooleanValue(int id, boolean value) {}

                    @Override
                    public void setFloatValue(int id, float value) {
                        feature.setConfigValue(FloatConfigEntry.this, value);
                    }

                    @Override
                    public void setStringValue(int id, String text) {}
                },
                -1,
                0, 0,
                text.apply(feature.getConfigValue(this)),
                0, 1, defaultValue,
                (int id, String label, float sliderValue) -> text.apply(sliderValue)
        );
    }
}