package simpleclient.mixin.performance;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.level.material.FogType;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import simpleclient.feature.PerformanceBoost;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    // +10% FPS under water
    @Inject(at = @At("HEAD"), method = "renderSky", cancellable = true)
    private void renderSky(PoseStack poseStack, Matrix4f matrix4f, float tickDelta, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci) {
        if (PerformanceBoost.DONT_RENDER_SKY_UNDER_WATER && camera.getFluidInCamera() != FogType.NONE) ci.cancel();
    }
}