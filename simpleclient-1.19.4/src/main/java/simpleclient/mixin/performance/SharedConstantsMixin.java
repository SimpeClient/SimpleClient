package simpleclient.mixin.performance;

import net.minecraft.SharedConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import simpleclient.feature.PerformanceBoost;

@Mixin(SharedConstants.class)
public class SharedConstantsMixin {
    // Faster starting
    @Inject(at = @At("HEAD"), method = "enableDataFixerOptimizations", cancellable = true)
    private static void enableDataFixerOptimizations(CallbackInfo ci) {
        if (PerformanceBoost.ENABLED) ci.cancel();
    }
}