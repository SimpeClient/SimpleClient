package simpleclient.mixin.feature.legacypvp;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {
    @Inject(at = @At(value = "HEAD"), method = "getCurrentItemAttackStrengthDelay", cancellable = true)
    public void getCurrentItemAttackStrengthDelay(CallbackInfoReturnable<Float> ci) {
        ci.setReturnValue(0.0F);
    }
}