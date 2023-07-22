package simpleclient.mixin.feature.tnt_timer;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.TntEntityRenderer;
import net.minecraft.entity.TntEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import simpleclient.adapter.TextAdapter;
import simpleclient.feature.TNTTimer;

@Mixin(TntEntityRenderer.class)
public abstract class TntEntityRendererMixin extends EntityRenderer<TntEntity> {
    protected TntEntityRendererMixin(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Inject(at = @At("TAIL"), method = "render(Lnet/minecraft/entity/TntEntity;DDDFF)V")
    public void render(TntEntity entity, double d, double e, double f, float g, float h, CallbackInfo ci) {
        if (TNTTimer.ENABLED) {
            TNTTimer.FUSE = entity.fuseTimer;
            renderLabelIfPresent(entity, TextAdapter.adapt(TNTTimer.INSTANCE.toText()).asUnformattedString(), d, e, f, 64);
        }
    }
}