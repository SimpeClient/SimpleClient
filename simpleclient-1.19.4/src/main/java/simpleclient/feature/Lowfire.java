package simpleclient.feature;

import com.google.gson.JsonObject;
import simpleclient.feature.config.FloatConfigEntry;
import simpleclient.text.Text;

public class Lowfire extends EnableableFeature {
    public static boolean ENABLED = false;
    public static float HEIGHT = 0;
    private final FloatConfigEntry height = new FloatConfigEntry("height", Text.translatable("simpleclient.lowfire.height"), 0.25F, value -> Text.literal(String.valueOf((float) (int) (value * 200 - 50) / 100)));

    public Lowfire() {
        super(FeatureType.LOWFIRE);
        addConfigEntry(height);
        refresh();
    }

    public void refresh() {
        ENABLED = isEnabled();
        HEIGHT = getConfigValue(height);
    }

    @Override
    public void setData(JsonObject data) {
        super.setData(data);
        refresh();
    }
}