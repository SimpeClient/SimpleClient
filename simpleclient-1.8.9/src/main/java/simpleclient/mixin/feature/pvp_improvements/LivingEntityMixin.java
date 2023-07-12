package simpleclient.mixin.feature.pvp_improvements;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import simpleclient.feature.PvPImprovements;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
     public LivingEntityMixin(World world) {
        super(world);
    }

    @Inject(at = @At("HEAD"), method = "getRotationVector", cancellable = true)
    public void getRotationVector(float partialTicks, CallbackInfoReturnable<Vec3d> cir) {
        if (PvPImprovements.HIT_DELAY_FIX) cir.setReturnValue(getRotationVector(pitch, yaw));
    }
}