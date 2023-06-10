package simpleclient.mixin.performance;

import net.minecraft.client.CloudStatus;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import simpleclient.feature.PerformanceBoost;

@Mixin(Options.class)
public class OptionsMixin {
    // +20% FPS
    @Inject(at = @At("HEAD"), method = "getCloudsType", cancellable = true)
    private void getCloudsType(CallbackInfoReturnable<CloudStatus> cir) {
        if (PerformanceBoost.DONT_RENDER_CLOUDS) cir.setReturnValue(CloudStatus.OFF);
    }
}