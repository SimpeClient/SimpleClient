package simpleclient.mixin.feature.legacypvp;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import simpleclient.feature.LegacyPvP;

@Mixin(HumanoidModel.class)
abstract class HumanoidModelMixin<T extends LivingEntity> {
    @Shadow @Final public ModelPart leftArm;
    @Shadow @Final public ModelPart rightArm;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;setupAttackAnimation(Lnet/minecraft/world/entity/LivingEntity;F)V"), method = "setupAnim")
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (LegacyPvP.ENABLED && entity instanceof Player player && LegacyPvP.isBlocking(player)) {
            if (player.getUsedItemHand() == InteractionHand.OFF_HAND) {
                leftArm.xRot = leftArm.xRot - ((float) Math.PI * 2.0F) / 10.0F;
            } else rightArm.xRot = rightArm.xRot - ((float) Math.PI * 2.0F) / 10.0F;
        }
    }
}