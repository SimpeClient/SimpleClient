package simpleclient.mixin.feature.pvp_improvements;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import simpleclient.feature.PvPImprovements;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow public int missTime;

    @Inject(at = @At("HEAD"), method = "startAttack")
    private void startAttack(CallbackInfoReturnable<Boolean> cir) {
        if (PvPImprovements.LEFTCLICK_DELAY_FIX) missTime = 0;
    }
}