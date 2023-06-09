package simpleclient.mixin;

import net.minecraft.SharedConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import simpleclient.feature.PerformanceBoost;

@Mixin(SharedConstants.class)
public class SharedConstantsMixin {
    @Inject(at = @At("HEAD"), method = "enableDataFixerOptimizations")
    private static void enableDataFixerOptimizations(CallbackInfo ci) {
        if (PerformanceBoost.INSTANCE.isEnabled()) ci.cancel();
    }
}