package simpleclient.feature;

import com.google.gson.JsonObject;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minecraft.resources.ResourceLocation;

public class Motionblur extends EnableableFeature {
    private final ManagedShaderEffect shader = ShaderEffectManager.getInstance().manage(new ResourceLocation("simpleclient", "shaders/post/motionblur.json"), shader -> {});

    public Motionblur() {
        super(FeatureType.MOTIONBLUR);
        ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
            if (isEnabled()) {
                JsonObject data = getData();
                if (!data.has("strength")) data.addProperty("strength", 0.2F);
                float strength = log(Math.pow(100.0F, 0.01F), data.get("strength").getAsFloat() * 100) / 100;
                shader.setUniformValue("strength", strength);
                shader.render(tickDelta);
            }
        });
    }

    private float log(double base, double number) {
        return (float) (Math.log(number) / Math.log(base));
    }
}