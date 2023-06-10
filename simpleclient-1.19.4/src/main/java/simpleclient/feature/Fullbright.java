package simpleclient.feature;

import com.google.gson.JsonObject;

public class Fullbright extends EnableableFeature {
    public static boolean ENABLED = false;

    public Fullbright() {
        super(FeatureType.FULLBRIGHT);
        refresh();
    }

    public void refresh() {
        ENABLED = isEnabled();
    }

    @Override
    public void setData(JsonObject data) {
        super.setData(data);
        refresh();
    }
}