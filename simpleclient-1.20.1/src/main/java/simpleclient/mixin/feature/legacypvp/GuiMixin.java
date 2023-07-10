package simpleclient.mixin.feature.legacypvp;

import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import simpleclient.feature.LegacyPvP;

@Mixin(Gui.class)
abstract class GuiMixin {
    @Shadow @Final private Minecraft minecraft;
    @Unique
    private AttackIndicatorStatus attackIndicator;

    @Inject(at = @At(value = "HEAD"), method = "render")
    public void render1(GuiGraphics guiGraphics, float patrialTicks, CallbackInfo ci) {
        if (LegacyPvP.ENABLED) {
            attackIndicator = minecraft.options.attackIndicator().get();
            minecraft.options.attackIndicator().set(AttackIndicatorStatus.OFF);
        }
    }

    @Inject(at = @At(value = "TAIL"), method = "render")
    public void render2(GuiGraphics guiGraphics, float partialTicks, CallbackInfo ci) {
        if (LegacyPvP.ENABLED) minecraft.options.attackIndicator().set(attackIndicator);
    }
}