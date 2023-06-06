package simpleclient.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Shadow;
import simpleclient.util.SharedVaribles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(Gui.class)
public abstract class InGameHudMixin {
    @Shadow public abstract Font getFont();

    @Inject(at = @At("TAIL"), method = "render")
    private void renderHud(PoseStack matrices, float tickdelta, CallbackInfo ci) {
        if (!SharedVaribles.hud_enabled || SharedVaribles.buildStatus == SharedVaribles.BUILD_STATUS.RELEASE) return;
        getFont().drawShadow(matrices, SharedVaribles.buildStatus.name + " Build", 1, 1, Color.RED.darker().getRGB());
    }
}
