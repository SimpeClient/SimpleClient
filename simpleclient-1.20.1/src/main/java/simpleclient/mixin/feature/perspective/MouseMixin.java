package simpleclient.mixin.feature.perspective;

import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import simpleclient.feature.Perspective;

@Mixin(MouseHandler.class)
public class MouseMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/tutorial/Tutorial;onMouse(DD)V"), method = "turnPlayer", locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
    private void onMouse(CallbackInfo ci, double d, double e, double k, double l, double f, double g, double h, int m) {
        if (Perspective.ACTIVE) {
            Perspective.YAW = (float) (Perspective.YAW + k / 8.0D);
            Perspective.PITCH = (float) Math.max(-90, Math.min(Perspective.PITCH + l * m / 8.0D, 90));
            System.out.println(Perspective.YAW + " " + Perspective.PITCH);
            ci.cancel();
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"), method = "turnPlayer", cancellable = true)
    private void turn(CallbackInfo ci) {
        if (Perspective.ACTIVE) ci.cancel();
    }
}