package simpleclient.mixin.feature.pvp_improvements;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import simpleclient.feature.PvPImprovements;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow private int attackCooldown;

    @Inject(at = @At("HEAD"), method = "doAttack")
    private void doAttack(CallbackInfo ci) {
        if (PvPImprovements.LEFTCLICK_DELAY_FIX) attackCooldown = 0;
    }
}