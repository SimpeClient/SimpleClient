package simpleclient.mixin.feature.legacypvp;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(at = @At("TAIL"), method = "getAttackAnim", cancellable = true)
    public void getAttackAnim(float tickDelta, CallbackInfoReturnable<Float> cir) {
        /*if (LegacyPvP.ENABLED) {
            float swingProgress = cir.getReturnValue();
            System.out.println(swingProgress);
            if (swingProgress > 0.4F && swingProgress < 0.95F) {
                cir.setReturnValue(0.4F + 0.6F * (float) Math.pow((swingProgress - 0.4F) / 0.6F, 4.0));
            }
        }*/
    }
}