package simpleclient.mixin.feature.legacypvp;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import simpleclient.feature.LegacyPvP;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {
    @Shadow @Final private Minecraft minecraft;

    @Shadow protected abstract void applyItemArmTransform(PoseStack poseStack, HumanoidArm humanoidArm, float f);

    @Shadow protected abstract void applyItemArmAttackTransform(PoseStack poseStack, HumanoidArm humanoidArm, float f);

    @Shadow protected abstract void applyEatTransform(PoseStack poseStack, float f, HumanoidArm humanoidArm, ItemStack itemStack);

    /*@Inject(at = @At("HEAD"), method = "itemUsed", cancellable = true)
    public void itemUsed(InteractionHand interactionHand, CallbackInfo ci) {
        if (LegacyPvP.ENABLED) ci.cancel();
    }*/

    @Inject(at = @At(value = "HEAD"), method = "renderArmWithItem", cancellable = true)
    public void renderArmWithItem(AbstractClientPlayer player, float partialTicks, float pitch, InteractionHand hand, float equipProgress, ItemStack itemStack, float swingProgress, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, CallbackInfo ci) {
        if (LegacyPvP.ENABLED && LegacyPvP.BLOCKING && player.getUsedItemHand() == hand) {
            ItemInHandRenderer itemRenderer = minecraft.gameRenderer.itemInHandRenderer;
            poseStack.pushPose();
            boolean mainHand = hand == InteractionHand.MAIN_HAND;
            HumanoidArm arm = mainHand ? player.getMainArm() : player.getMainArm().getOpposite();
            boolean rightArm = arm == HumanoidArm.RIGHT;
            applyItemArmTransform(poseStack, arm, equipProgress);
            applyItemArmAttackTransform(poseStack, arm, swingProgress);
            transformBlockFirstPerson(poseStack, arm);
            itemRenderer.renderItem(
                    player,
                    itemStack,
                    rightArm ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
                    !rightArm,
                    poseStack,
                    multiBufferSource,
                    packedLight
            );
            poseStack.popPose();
            ci.cancel();
        }
    }

    @Unique
    private void transformBlockFirstPerson(PoseStack poseStack, HumanoidArm hand) {
        int signum = hand == HumanoidArm.RIGHT ? 1 : -1;
        poseStack.translate(signum * -0.14142136F, 0.08F, 0.14142136F);
        poseStack.mulPose(Axis.XP.rotationDegrees(-102.25F));
        poseStack.mulPose(Axis.YP.rotationDegrees(signum * 13.365F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(signum * 78.05F));
    }

    @Inject(at = @At(value = "HEAD"), method = "renderArmWithItem", cancellable = true)
    public void renderArmWithItem2(AbstractClientPlayer player, float partialTicks, float pitch, InteractionHand hand, float equipProgress, ItemStack itemStack, float swingProgress, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, CallbackInfo ci) {
        if (LegacyPvP.ENABLED && LegacyPvP.BLOCKING && player.getUseItemRemainingTicks() > 0 && player.getUsedItemHand() == hand) {
            if (itemStack.isEmpty() || itemStack.is(Items.FILLED_MAP)) return;
            ci.cancel();
            boolean mainHand = hand == InteractionHand.MAIN_HAND;
            HumanoidArm arm = mainHand ? player.getMainArm() : player.getMainArm().getOpposite();
            boolean rightArm = arm == HumanoidArm.RIGHT;
            poseStack.pushPose();
            switch (itemStack.getUseAnimation()) {
                case NONE, BLOCK -> {
                    applyItemArmTransform(poseStack, arm, equipProgress);
                    applyItemArmAttackTransform(poseStack, arm, swingProgress);
                }
                case EAT, DRINK -> {
                    applyEatTransform(poseStack, partialTicks, arm, itemStack);
                    applyItemArmTransform(poseStack, arm, equipProgress);
                    applyItemArmAttackTransform(poseStack, arm, swingProgress);
                }
                case BOW -> {
                    applyItemArmTransform(poseStack, arm, equipProgress);
                    applyItemArmAttackTransform(poseStack, arm, swingProgress);
                    applyBowTransform(poseStack, partialTicks, arm, itemStack, minecraft);
                }
                case SPEAR -> {
                    applyItemArmTransform(poseStack, arm, equipProgress);
                    applyItemArmAttackTransform(poseStack, arm, swingProgress);
                    applyTridentTransform(poseStack, partialTicks, arm, itemStack, minecraft);
                }
                case CROSSBOW -> {
                    applyItemArmTransform(poseStack, arm, equipProgress);
                    applyItemArmAttackTransform(poseStack, arm, swingProgress);
                    applyCrossbowTransform(poseStack, partialTicks, arm, itemStack, minecraft);
                }
            }
            minecraft.gameRenderer.itemInHandRenderer.renderItem(
                    player,
                    itemStack,
                    rightArm ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
                    !rightArm,
                    poseStack,
                    multiBufferSource,
                    packedLight
            );
            poseStack.popPose();
        }
    }

    @Unique
    private void applyBowTransform(PoseStack poseStack, float partialTicks, HumanoidArm arm, ItemStack itemStack, Minecraft minecraft) {
        int signum = arm == HumanoidArm.RIGHT ? 1 : -1;
        poseStack.translate(signum * -0.2785682F, 0.18344387F, 0.15731531F);
        poseStack.mulPose(Axis.XP.rotationDegrees(-13.935F));
        poseStack.mulPose(Axis.YP.rotationDegrees(signum * 35.3F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(signum * -9.785F));
        float f8 = itemStack.getUseDuration() - (minecraft.player != null ? minecraft.player.getUseItemRemainingTicks() : 0.0F) - partialTicks + 1.0F;
        float f12 = f8 / 20.0F;
        f12 = (f12 * f12 + f12 * 2.0F) / 3.0F;
        if (f12 > 1.0F) f12 = 1.0F;
        if (f12 > 0.1F) {
            float f15 = Mth.sin((f8 - 0.1F) * 1.3F);
            float f18 = f12 - 0.1F;
            float f20 = f15 * f18;
            poseStack.translate(f20 * 0.0F, f20 * 0.004F, f20 * 0.0F);
        }
        poseStack.translate(f12 * 0.0F, f12 * 0.0F, f12 * 0.04F);
        poseStack.scale(1.0F, 1.0F, 1.0F + f12 * 0.2F);
        poseStack.mulPose(Axis.YN.rotationDegrees(signum * 45.0F));
    }

    @Unique
    private void applyTridentTransform(PoseStack poseStack, float partialTicks, HumanoidArm arm, ItemStack itemStack, Minecraft minecraft) {
        int signum = arm == HumanoidArm.RIGHT ? 1 : -1;
        poseStack.translate(signum * -0.5F, 0.7F, 0.1F);
        poseStack.mulPose(Axis.XP.rotationDegrees(-55.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(signum * 35.3F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(signum * -9.785F));
        float f7 = itemStack.getUseDuration() - (minecraft.player != null ? minecraft.player.getUseItemRemainingTicks() : 0.0F) - partialTicks + 1.0F;
        float f11 = f7 / 10.0F;
        if (f11 > 1.0F) f11 = 1.0F;
        if (f11 > 0.1F) {
            float f14 = Mth.sin((f7 - 0.1F) * 1.3F);
            float f17 = f11 - 0.1F;
            float f19 = f14 * f17;
            poseStack.translate(f19 * 0.0F, f19 * 0.004F, f19 * 0.0F);
        }
        poseStack.translate(0.0D, 0.0D, f11 * 0.2F);
        poseStack.scale(1.0F, 1.0F, 1.0F + f11 * 0.2F);
        poseStack.mulPose(Axis.YN.rotationDegrees(signum * 45.0F));
    }

    @Unique
    private void applyCrossbowTransform(PoseStack poseStack, float partialTicks, HumanoidArm arm, ItemStack itemStack, Minecraft minecraft) {
        int signum = arm == HumanoidArm.RIGHT ? 1 : -1;
        poseStack.translate(signum * -0.4785682F, -0.094387F, 0.05731531F);
        poseStack.mulPose(Axis.XP.rotationDegrees(-11.935F));
        poseStack.mulPose(Axis.YP.rotationDegrees(signum * 65.3F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(signum * -9.785F));
        float f9 = itemStack.getUseDuration() - minecraft.player.getUseItemRemainingTicks() - partialTicks + 1.0F;
        float f13 = f9 / CrossbowItem.getChargeDuration(itemStack);
        if (f13 > 1.0F) f13 = 1.0F;
        if (f13 > 0.1F) {
            float f16 = Mth.sin((f9 - 0.1F) * 1.3F);
            float f3 = f13 - 0.1F;
            float f4 = f16 * f3;
            poseStack.translate(f4 * 0.0F, f4 * 0.004F, f4 * 0.0F);
        }
        poseStack.translate(f13 * 0.0F, f13 * 0.0F, f13 * 0.04F);
        poseStack.scale(1.0F, 1.0F, 1.0F + f13 * 0.2F);
        poseStack.mulPose(Axis.YN.rotationDegrees(signum * 45.0F));
    }
}