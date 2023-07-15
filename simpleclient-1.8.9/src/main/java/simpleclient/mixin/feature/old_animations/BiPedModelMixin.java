package simpleclient.mixin.feature.old_animations;

import net.minecraft.client.render.entity.model.BiPedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import simpleclient.feature.OldAnimations;

@Mixin(BiPedModel.class)
public class BiPedModelMixin {
    @ModifyConstant(method = "setAngles", constant = @Constant(floatValue = -0.5235988F))
    private float cancelRotation(float value) {
        return OldAnimations.ENABLED ? 0.0F : value;
    }
}