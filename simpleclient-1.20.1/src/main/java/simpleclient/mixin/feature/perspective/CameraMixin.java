package simpleclient.mixin.feature.perspective;

import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import simpleclient.feature.Perspective;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @Shadow private float yRot;
    @Shadow private float xRot;

    @Inject(at = @At(value = "INVOKE", target = "net/minecraft/client/Camera.move(DDD)V", ordinal = 0), method = "setup")
    public void setRotation1(BlockGetter blockGetter, Entity entity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo info) {
        if (Perspective.ACTIVE) {
            xRot = Perspective.PITCH;
            yRot = Perspective.YAW;
        }
    }

    @ModifyArgs(at = @At(value = "INVOKE", target = "net/minecraft/client/Camera.setRotation(FF)V", ordinal = 0), method = "setup")
    public void setRotation(Args args) {
        if (Perspective.ACTIVE) {
            args.set(0, Perspective.YAW);
            args.set(1, Perspective.PITCH);
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "net/minecraft/client/Camera.setRotation(FF)V", ordinal = 0, shift = At.Shift.AFTER), method = "setup")
    public void setRotation2(BlockGetter blockGetter, Entity entity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo info) {
        if (!Perspective.ACTIVE) {
            Perspective.YAW = entity.getYRot();
            Perspective.PITCH = entity.getXRot();
        }
    }
}