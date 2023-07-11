package simpleclient.mixin.feature.lowfire;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import simpleclient.feature.Lowfire;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;translate(FFF)V"), method = "renderFire")
    public void onRenderFireOverlay(Entity entity, double x, double y, double z, float idk, CallbackInfo ci) {
        if (Lowfire.ENABLED) GlStateManager.translate(0.0, Lowfire.HEIGHT * 1.75 - 0.5, 0.0);
    }
}