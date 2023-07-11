package simpleclient.mixin.feature.lowfire;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import simpleclient.feature.Lowfire;

@Mixin(ScreenEffectRenderer.class)
public class ScreenEffectRendererMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"), method = "renderFire")
    private static void renderFire(Minecraft minecraft, PoseStack poseStack, CallbackInfo ci) {
        if (Lowfire.ENABLED) poseStack.translate(0.0, Lowfire.HEIGHT * 1.75 - 0.5, 0.0);
    }
}