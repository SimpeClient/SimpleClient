package simpleclient.mixin.feature.perspective;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import simpleclient.feature.Perspective;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {
    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;yaw:F"), method = "updateCamera(Lnet/minecraft/world/World;Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;Lnet/minecraft/client/option/GameOptions;F)V")
    public float yaw(Entity entity) {
        return Perspective.ACTIVE ? Perspective.YAW : entity.yaw;
    }

    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevYaw:F"), method = "updateCamera(Lnet/minecraft/world/World;Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;Lnet/minecraft/client/option/GameOptions;F)V")
    public float prevYaw(Entity entity) {
        return Perspective.ACTIVE ? Perspective.YAW : entity.prevYaw;
    }

    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;pitch:F"), method = "updateCamera(Lnet/minecraft/world/World;Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;Lnet/minecraft/client/option/GameOptions;F)V")
    public float pitch(Entity entity) {
        return Perspective.ACTIVE ? Perspective.PITCH : entity.pitch;
    }

    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevPitch:F"), method = "updateCamera(Lnet/minecraft/world/World;Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;Lnet/minecraft/client/option/GameOptions;F)V")
    public float prevPitch(Entity entity) {
        return Perspective.ACTIVE ? Perspective.PITCH : entity.prevPitch;
    }
}