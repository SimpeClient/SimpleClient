package simpleclient.feature;

import com.google.gson.JsonObject;

public class OldAnimations extends EnableableFeature {
    public static boolean ENABLED = false;

    public OldAnimations() {
        super(FeatureType.OLD_ANIMATIONS);
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