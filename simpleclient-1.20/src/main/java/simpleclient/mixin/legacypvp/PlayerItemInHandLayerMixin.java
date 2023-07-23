package simpleclient.mixin.legacypvp;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import simpleclient.feature.LegacyPvP;

@Mixin(PlayerItemInHandLayer.class)
public class PlayerItemInHandLayerMixin<T extends Player, M extends EntityModel<T> & ArmedModel & HeadedModel> extends ItemInHandLayer<T, M> {
    @Shadow @Final private ItemInHandRenderer itemInHandRenderer;

    public PlayerItemInHandLayerMixin(RenderLayerParent<T, M> renderLayerParent, ItemInHandRenderer itemInHandRenderer) {
        super(renderLayerParent, itemInHandRenderer);
    }

    @Inject(at = @At("HEAD"), method = "renderArmWithItem", cancellable = true)
    protected void renderArmWithItem(LivingEntity entity, ItemStack itemStack, ItemDisplayContext context, HumanoidArm arm, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLight, CallbackInfo ci) {
        if (!LegacyPvP.ENABLED) return;
        if (itemStack.isEmpty()) return;
        if (entity.getUseItem() != itemStack || !LegacyPvP.isBlocking((Player) entity)) return;
        ci.cancel();
        poseStack.pushPose();
        getParentModel().translateToHand(arm, poseStack);
        boolean leftHand = arm == HumanoidArm.LEFT;
        transformBlocking(poseStack, leftHand);
        transformInverse(entity, itemStack, context, poseStack, leftHand);
        itemInHandRenderer.renderItem(entity, itemStack, context, leftHand, poseStack, multiBufferSource, combinedLight);
        poseStack.popPose();
    }

    @Unique
    private void transformBlocking(PoseStack poseStack, boolean leftHand) {
        poseStack.translate((leftHand ? 1.0F : -1.0F) / 16.0F, 0.4375F, 0.0625F);
        poseStack.translate(leftHand ? -0.035F : 0.05F, leftHand ? 0.045F : 0.0F, leftHand ? -0.135F : -0.1F);
        poseStack.mulPose(Axis.YP.rotationDegrees((leftHand ? -1.0F : 1.0F) * -50.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(-10.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees((leftHand ? -1.0F : 1.0F) * -60.0F));
        poseStack.translate(0.0F, 0.1875F, 0.0F);
        poseStack.scale(0.625F, 0.625F, 0.625F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        poseStack.mulPose(Axis.XN.rotationDegrees(-100.0F));
        poseStack.mulPose(Axis.YN.rotationDegrees(leftHand ? 35.0F : 45.0F));
        poseStack.translate(0.0F, -0.3F, 0.0F);
        poseStack.scale(1.5F, 1.5F, 1.5F);
        poseStack.mulPose(Axis.YN.rotationDegrees(50.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(335.0F));
        poseStack.translate(-0.9375F, -0.0625F, 0.0F);
        poseStack.translate(0.5F, 0.5F, 0.25F);
        poseStack.mulPose(Axis.YN.rotationDegrees(180.0F));
        poseStack.translate(0.0F, 0.0F, 0.28125F);
    }

    @Unique
    private void transformInverse(LivingEntity entity, ItemStack itemStack, ItemDisplayContext context, PoseStack poseStack, boolean leftHand) {
        BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(itemStack, entity.level(), entity, 0);
        transformInverse(model.getTransforms().getTransform(context), leftHand, poseStack);
    }

    @Unique
    private void transformInverse(ItemTransform transform, boolean leftHand, PoseStack poseStack) {
        if (transform != ItemTransform.NO_TRANSFORM) {
            float angleX = transform.rotation.x();
            float angleY = leftHand ? -transform.rotation.y() : transform.rotation.y();
            float angleZ = leftHand ? -transform.rotation.z() : transform.rotation.z();
            Quaternionf quaternion = new Quaternionf().rotationXYZ(angleX * 0.017453292F, angleY * 0.017453292F, angleZ * 0.017453292F);
            quaternion.conjugate();
            poseStack.scale(1.0F / transform.scale.x(), 1.0F / transform.scale.y(), 1.0F / transform.scale.z());
            poseStack.mulPose(quaternion);
            poseStack.translate((leftHand ? -1.0F : 1.0F) * -transform.translation.x(), -transform.translation.y(), -transform.translation.z());
        }
    }
}