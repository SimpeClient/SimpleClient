package simpleclient.feature.config;

import simpleclient.text.Text;

public class PercentConfigEntry extends FloatConfigEntry {
    public PercentConfigEntry(String key, Text displayText, float defaultValue, int digits) {
        super(key, displayText, defaultValue, v -> Text.literal((float) (int) (v * Math.pow(10, digits + 2)) / Math.pow(10, digits) + "%"));
    }
}