package simpleclient.mixin.feature.old_animations;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemRenderer;
import net.minecraft.client.render.entity.model.BiPedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import simpleclient.feature.OldAnimations;

@Mixin(HeldItemRenderer.class)
public class ItemFeatureRendererMixin {
    @Shadow @Final private LivingEntityRenderer<?> entityRenderer;

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void render(LivingEntity entity, float handSwing, float handSwingAmount, float tickDelta, float age, float headYaw, float headPitch, float scale, CallbackInfo ci) {
        ItemStack itemStack = entity.getStackInHand();
        if (OldAnimations.ENABLED && itemStack != null && itemStack.getItem() instanceof SwordItem) ci.cancel(); else return;
        GlStateManager.pushMatrix();
        if (entityRenderer.getModel().child) {
            float f = 0.5F;
            GlStateManager.translate(0.0F, 0.625F, 0.0F);
            GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
            GlStateManager.scale(f, f, f);
        }
        Label_0327:
        if (entity instanceof PlayerEntity) {
            if (((PlayerEntity) entity).isUsingItem()) {
                if (entity.isSneaking()) {
                    ((BiPedModel) entityRenderer.getModel()).setArmAngle(0.0325f);
                    GlStateManager.scale(1.05f, 1.05f, 1.05f);
                    GlStateManager.translate(-0.58f, 0.32f, -0.07f);
                    GlStateManager.rotate(-24405.0f, 137290.0f, -2009900.0f, -2654900.0f);
                } else {
                    ((BiPedModel) entityRenderer.getModel()).setArmAngle(0.0325f);
                    GlStateManager.scale(1.05f, 1.05f, 1.05f);
                    GlStateManager.translate(-0.45f, 0.25f, -0.07f);
                    GlStateManager.rotate(-24405.0f, 137290.0f, -2009900.0f, -2654900.0f);
                }
            } else ((BiPedModel) entityRenderer.getModel()).setArmAngle(0.0625f);
            if (!((PlayerEntity) entity).isUsingItem()) {
                GlStateManager.translate(-0.0855f, 0.4775f, 0.1585f);
                GlStateManager.rotate(-19.0f, 20.0f, 0.0f, -6.0f);
                break Label_0327;
            }
            if (((PlayerEntity) entity).isUsingItem()) GlStateManager.translate(-0.0625f, 0.4375f, 0.0625f);
        } else {
            ((BiPedModel) entityRenderer.getModel()).setArmAngle(0.0625f);
            GlStateManager.translate(-0.0625f, 0.4375f, 0.0625f);
        }
        if (entity instanceof PlayerEntity && ((PlayerEntity) entity).fishHook != null) itemStack = new ItemStack(Items.FISHING_ROD, 0);
        Item item = itemStack.getItem();
        MinecraftClient minecraft = MinecraftClient.getInstance();
        if (item instanceof BlockItem && Block.getBlockFromItem(item).getBlockType() == 2) {
            GlStateManager.translate(0.0f, 0.1875f, -0.3125f);
            GlStateManager.rotate(20.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
            float f2 = 0.375f;
            GlStateManager.scale(-f2, -f2, f2);
        }
        if (entity.isSneaking()) GlStateManager.translate(0.0f, 0.203125f, 0.0f);
        minecraft.getHeldItemRenderer().renderItem(entity, itemStack, ModelTransformation.Mode.THIRD_PERSON);
        GlStateManager.popMatrix();
    }
}