package de.simpleclient.mixin;

import de.simpleclient.util.SharedVaribles;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(InGameHud.class)
public class InGameHudMixins {
    private MinecraftClient mc = MinecraftClient.getInstance();

    @Inject(method = "render", at = @At("RETURN"), cancellable = true)
    private void renderHud(MatrixStack matrices, float tickdelta, CallbackInfo ci) {
        if (!SharedVaribles.hud_enabled || SharedVaribles.buildStatus == SharedVaribles.BUILD_STATUS.RELEASE) return;

        mc.textRenderer.drawWithShadow(matrices, SharedVaribles.buildStatus.name + " Build", 1, 1, Color.RED.darker().getRGB());
    }
}
