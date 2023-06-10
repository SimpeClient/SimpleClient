package simpleclient.mixin;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import simpleclient.feature.Zoom;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(at = @At(value = "RETURN"), method = "getFov", cancellable = true)
    public void getFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> cir) {
        if (Zoom.ACTIVE) cir.setReturnValue(cir.getReturnValue() * (1 - Zoom.ZOOM_FACTOR));
    }
}