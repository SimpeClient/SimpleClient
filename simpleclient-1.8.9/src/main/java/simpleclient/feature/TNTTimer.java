package simpleclient.feature;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import simpleclient.text.Style;

public class TNTTimer extends EnableableFeature implements TextFeature {
    public static TNTTimer INSTANCE = null;
    public static boolean ENABLED = false;
    public static int FUSE = -1;

    public TNTTimer() {
        super(FeatureType.TNT_TIMER);
        INSTANCE = this;
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

    @Override
    public String valueForParameter(String parameter) {
        if (parameter.equals("ticks")) return String.valueOf(FUSE);
        if (parameter.equals("seconds")) return String.valueOf((float) (FUSE / 2) / 10);
        return "";
    }

    @Override
    public JsonArray getDefaultFormat() {
        JsonArray format = new JsonArray();
        format.add(parameter("seconds", new Style()));
        format.add(text("s", new Style()));
        return format;
    }
}