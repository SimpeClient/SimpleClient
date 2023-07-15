package simpleclient.feature;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minecraft.resources.ResourceLocation;
import simpleclient.feature.config.PercentConfigEntry;
import simpleclient.text.Text;

public class Motionblur extends EnableableFeature {
    private final ManagedShaderEffect shader = ShaderEffectManager.getInstance().manage(new ResourceLocation("simpleclient", "shaders/post/motionblur.json"), shader -> {});
    private final PercentConfigEntry strength = new PercentConfigEntry("strength", Text.translatable("simpleclient.motionblur.strength"), 0.25F, 0);

    public Motionblur() {
        super(FeatureType.MOTIONBLUR);
        addConfigEntry(strength);
        ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
            if (isEnabled()) {
                float strengthValue = log(Math.pow(100.0F, 0.01F), getConfigValue(strength) * 100) / 100;
                shader.setUniformValue("strength", Math.max(0, Math.min(strengthValue, 1)));
                shader.render(tickDelta);
            }
        });
    }

    private float log(double base, double number) {
        return (float) (Math.log(number) / Math.log(base));
    }
}