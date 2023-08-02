package simpleclient.mixin.feature.legacypvp;

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import simpleclient.feature.LegacyPvP;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(at = @At("HEAD"), method = "getPickRadius", cancellable = true)
    public void getPickRadius(CallbackInfoReturnable<Float> cir) {
        if (LegacyPvP.ENABLED) cir.setReturnValue(0.1F);
    }
}