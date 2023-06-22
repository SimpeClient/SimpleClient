package simpleclient.feature.config;

import net.minecraft.network.chat.Component;

import java.util.function.Function;

public class PercentConfigEntry extends FloatConfigEntry {
    public PercentConfigEntry(String key, Component displayText, float defaultValue, int digits) {
        super(key, displayText, defaultValue, v -> Component.literal((float) (int) (v * Math.pow(10, digits + 2)) / Math.pow(10, digits) + "%"));
    }
}