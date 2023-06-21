package simpleclient.feature;

import com.google.gson.JsonObject;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import simpleclient.feature.config.FloatConfigEntry;
import simpleclient.feature.config.PercentConfigEntry;

public class Lowfire extends EnableableFeature {
    public static boolean ENABLED = false;
    public static float HEIGHT = 0;
    private final FloatConfigEntry height = new FloatConfigEntry("height", Component.translatable("simpleclient.lowfire.height"), 0.25F, value -> Component.literal(String.valueOf((float) (int) (value * 200 - 50) / 100)));

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