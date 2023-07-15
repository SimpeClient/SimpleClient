package simpleclient.feature;

import com.google.gson.JsonObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import simpleclient.feature.config.PercentConfigEntry;
import simpleclient.text.Text;

public class Motionblur extends EnableableFeature {
    public static Motionblur INSTANCE;
    public static float STRENGTH = 0.0F;
    private final PercentConfigEntry strength = new PercentConfigEntry("strength", Text.translatable("simpleclient.motionblur.strength"), 0.25F, 0);

    public Motionblur() {
        super(FeatureType.MOTIONBLUR);
        addConfigEntry(strength);
        INSTANCE = this;
    }

    public void refresh() {
        STRENGTH = log(Math.pow(100.0F, 0.01F), getConfigValue(strength) * 100) / 100;
        MinecraftClient.getInstance().gameRenderer.disableShader();
        MinecraftClient.getInstance().gameRenderer.loadShader(new Identifier("simpleclient", "motionblur"));
    }

    @Override
    public void setData(JsonObject data) {
        super.setData(data);
        refresh();
    }

    private float log(double base, double number) {
        return (float) (Math.log(number) / Math.log(base));
    }
}