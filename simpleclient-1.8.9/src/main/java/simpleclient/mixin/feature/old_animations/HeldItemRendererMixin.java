package simpleclient.mixin.feature.old_animations;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import simpleclient.feature.OldAnimations;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Shadow private float equipProgress;
    @Shadow @Final private MinecraftClient client;
    @Shadow private ItemStack mainHand;

    @Unique
    private float swingProgress;

    @Inject(method = "renderArmHoldingItem", at = @At("HEAD"))
    private void renderArmHoldingItem1(float partialTicks, CallbackInfo ci) {
        if (OldAnimations.ENABLED) swingProgress = client.player.getHandSwingProgress(partialTicks);
    }

    @Inject(method = "applyEquipAndSwingOffset", at = @At(value = "HEAD"))
    private void applyEquipAndSwingOffset1(CallbackInfo ci) {
        if (OldAnimations.ENABLED) {
            if (client.options.attackKey.isPressed() && client.options.useKey.isPressed() && client.result.type == BlockHitResult.Type.BLOCK) {
                ClientPlayerEntity player = client.player;
                int swingAnimationEnd = player.hasStatusEffect(StatusEffect.HASTE) ? (6 - (1 +
                        player.getEffectInstance(StatusEffect.HASTE).getAmplifier())) : (player.hasStatusEffect(StatusEffect.MINING_FATIGUE) ? (6 + (1 +
                        player.getEffectInstance(StatusEffect.MINING_FATIGUE).getAmplifier()) * 2) : 6);
                if (!player.handSwinging || player.handSwingTicks >= swingAnimationEnd / 2 || player.handSwingTicks < 0) {
                    player.handSwingTicks = -1;
                    player.handSwinging = true;
                }
            }
        }
    }

    // Fishing Rod
    @Inject(method = "renderArmHoldingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"))
    public void renderArmHoldingItem2(float partialTicks, CallbackInfo ci) {
        if (OldAnimations.ENABLED && mainHand.getItem() == Items.FISHING_ROD) {
            GlStateManager.translate(0.08F, -0.027F, -0.33F);
            GlStateManager.scale(0.93F, 1.0F, 1.0F);
        }
    }

    // Eating, Drinking
    @Inject(at = @At(value = "HEAD"), method = "applyEatOrDrinkTransformation", cancellable = true)
    private void applyEatOrDrinkTransformation(AbstractClientPlayerEntity abstractClientPlayerEntity, float partialTicks, CallbackInfo ci) {
        if (OldAnimations.ENABLED) {
            ci.cancel();
            float useAmount = (float) abstractClientPlayerEntity.getItemUseTicks() - partialTicks + 1.0F;
            float f1 = 1.0F - useAmount / (float) mainHand.getMaxUseTime();
            float f2 = 1.0F - f1;
            f2 = f2 * f2 * f2;
            f2 = f2 * f2 * f2;
            f2 = f2 * f2 * f2;
            float f3 = 1.0F - f2;
            GlStateManager.translate(0.0F, MathHelper.abs(MathHelper.cos(useAmount / 4.0F * 3.1415927F) * 0.1F) * (float) ((double) f1 > 0.2D ? 1 : 0), 0.0F);
            GlStateManager.translate(f3 * 0.6F, -f3 * 0.5F, 0.0F);
            GlStateManager.rotate(f3 * 90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(f3 * 10.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(f3 * 30.0F, 0.0F, 0.0F, 1.0F);
        }
    }

    // Blocking
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;applySwordBlockTransformation()V", shift = At.Shift.AFTER), method = "renderArmHoldingItem")
    private void applySwordBlockTransformation(CallbackInfo ci) {
        if (OldAnimations.ENABLED) {
            GlStateManager.scale(0.83F, 0.88F, 0.85F);
            GlStateManager.translate(-0.3F, 0.1F, 0.0F);
        }
    }

    // Bow
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;applyBowTransformation(FLnet/minecraft/client/network/AbstractClientPlayerEntity;)V", shift = At.Shift.AFTER), method = "renderArmHoldingItem")
    private void transformBow(float f, CallbackInfo ci) {
        if (OldAnimations.ENABLED) GlStateManager.translate(0.0F, 0.1F, -0.15F);
    }

    @ModifyConstant(method = "renderArmHoldingItem", constant = @Constant(floatValue = 0.0F))
    private float enableBlockHits(float constant) {
        return OldAnimations.ENABLED ? swingProgress : constant;
    }
}