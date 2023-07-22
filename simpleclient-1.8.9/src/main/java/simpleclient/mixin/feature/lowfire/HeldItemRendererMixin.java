package simpleclient.mixin.feature.lowfire;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.render.item.HeldItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import simpleclient.feature.Lowfire;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;translate(FFF)V"), method = "renderFireOverlay")
    public void renderFireOverlay(float partialTicks, CallbackInfo ci) {
        if (Lowfire.ENABLED) GlStateManager.translate(0.0, Lowfire.HEIGHT * 1.75 - 0.75, 0.0);
    }
}