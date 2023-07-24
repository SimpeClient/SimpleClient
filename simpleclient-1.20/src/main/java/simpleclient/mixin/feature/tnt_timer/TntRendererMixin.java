package simpleclient.mixin.feature.tnt_timer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntRenderer;
import net.minecraft.world.entity.item.PrimedTnt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import simpleclient.adapter.TextAdapter;
import simpleclient.feature.TNTTimer;

@Mixin(TntRenderer.class)
public abstract class TntRendererMixin extends EntityRenderer<PrimedTnt> {
    protected TntRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @Inject(at = @At("TAIL"), method = "render(Lnet/minecraft/world/entity/item/PrimedTnt;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    public void render(PrimedTnt entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
        if (TNTTimer.ENABLED) {
            TNTTimer.FUSE = entity.getFuse();
            renderNameTag(entity, TextAdapter.adapt(TNTTimer.INSTANCE.toText()), poseStack, multiBufferSource, i);
        }
    }
}