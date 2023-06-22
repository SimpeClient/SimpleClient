package simpleclient.feature;

import com.google.gson.JsonArray;
import net.minecraft.client.Minecraft;
import simpleclient.text.Style;

public class FPS extends DraggableTextFeature {
    public FPS() {
        super(FeatureType.FPS);
    }

    @Override
    public String valueForParameter(String parameter) {
        if (parameter.equals("fps")) return "" + Minecraft.getInstance().getFps();
        else return "";
    }

    @Override
    public String valueForDummyParameter(String parameter) {
        if (parameter.equals("fps")) return "" + Minecraft.getInstance().getFps();
        else return "";
    }

    @Override
    public JsonArray getDefaultFormat() {
        JsonArray format = new JsonArray();
        format.add(text("FPS: ", new Style()));
        format.add(parameter("fps", new Style()));
        return format;
    }
}
