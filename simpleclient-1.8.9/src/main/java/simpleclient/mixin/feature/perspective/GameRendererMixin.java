package simpleclient.mixin.feature.perspective;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import simpleclient.feature.Perspective;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow private MinecraftClient client;

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/ClientPlayerEntity;increaseTransforms(FF)V"), method = "render")
    private void increaseTransforms(ClientPlayerEntity entity, float yaw, float pitch) {
        if (Perspective.ACTIVE) {
            if (Float.isNaN(Perspective.YAW)) {
                Perspective.YAW = entity.yaw;
                Perspective.PITCH = entity.pitch;
            }
            Perspective.YAW = (float) (Perspective.YAW + yaw / 8.0D);
            Perspective.PITCH = (float) Math.max(-90, Math.min(Perspective.PITCH + pitch / 8.0D, 90));
        } else {
            if (!Float.isNaN(Perspective.YAW)) Perspective.YAW = Float.NaN;
            client.player.increaseTransforms(yaw, pitch);
        }
    }

    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;yaw:F"), method = "transformCamera")
    public float yaw(Entity entity) {
        return Perspective.ACTIVE ? Perspective.YAW : entity.yaw;
    }

    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevYaw:F"), method = "transformCamera")
    public float prevYaw(Entity entity) {
        return Perspective.ACTIVE ? Perspective.YAW : entity.prevYaw;
    }

    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;pitch:F"), method = "transformCamera")
    public float pitch(Entity entity) {
        return Perspective.ACTIVE ? Perspective.PITCH : entity.pitch;
    }

    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevPitch:F"), method = "transformCamera")
    public float prevPitch(Entity entity) {
        return Perspective.ACTIVE ? Perspective.PITCH : entity.prevPitch;
    }
}