package simpleclient.feature;

import com.google.gson.JsonObject;
import net.minecraft.client.MinecraftClient;

public class Fullbright extends EnableableFeature {
    public static Fullbright INSTANCE = null;

    public Fullbright() {
        super(FeatureType.FULLBRIGHT);
        INSTANCE = this;
    }

    public void refresh() {
        MinecraftClient.getInstance().options.gamma = isEnabled() ? 100.0F : 0.5F;
    }

    @Override
    public void setData(JsonObject data) {
        super.setData(data);
        refresh();
    }
}