package simpleclient.mixin.feature.perspective;

import net.minecraft.client.render.Camera;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import simpleclient.feature.Perspective;

@Mixin(Camera.class)
public class CameraMixin {
    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;yaw:F"), method = "update")
    private static float yaw(PlayerEntity entity) {
        return Perspective.ACTIVE ? Perspective.YAW : entity.yaw;
    }

    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;pitch:F"), method = "update")
    private static float pitch(PlayerEntity entity) {
        return Perspective.ACTIVE ? Perspective.PITCH : entity.pitch;
    }
}