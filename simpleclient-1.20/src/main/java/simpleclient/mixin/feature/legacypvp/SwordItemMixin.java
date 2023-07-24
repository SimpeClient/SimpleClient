package simpleclient.mixin.feature.legacypvp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import simpleclient.feature.LegacyPvP;

@Mixin(SwordItem.class)
public abstract class SwordItemMixin extends TieredItem {
    public SwordItemMixin(Tier tier, Properties properties) {
        super(tier, properties);
    }

    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int i) {
        if (LegacyPvP.ENABLED && level.isClientSide) {
            LegacyPvP.BLOCKING = false;
            ByteBuf buf = Unpooled.buffer();
            buf.writeByte(1);
            ClientPlayNetworking.send(new ResourceLocation("simpleclient", "legacypvp"), new FriendlyByteBuf(buf));
        }
    }

    public @NotNull UseAnim getUseAnimation(ItemStack itemStack) {
        return LegacyPvP.ENABLED ? UseAnim.BLOCK : super.getUseAnimation(itemStack);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (LegacyPvP.ENABLED && level.isClientSide) {
            ItemStack itemStack = player.getItemInHand(interactionHand);
            player.startUsingItem(interactionHand);
            LegacyPvP.BLOCKING = true;
            ByteBuf buf = Unpooled.buffer();
            buf.writeByte(0);
            ClientPlayNetworking.send(new ResourceLocation("simpleclient", "legacypvp"), new FriendlyByteBuf(buf));
            return InteractionResultHolder.consume(itemStack);
        } else return super.use(level, player, interactionHand);
    }
}