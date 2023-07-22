package simpleclient.mixin.feature.motionblur;

import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import simpleclient.resource.MotionBlurResource;

@Mixin(ReloadableResourceManagerImpl.class)
public class ReloadableResourceManagerImplMixin {
    @Inject(at = @At("HEAD"), method = "getResource", cancellable = true)
    public void getResource(Identifier identifier, CallbackInfoReturnable<Resource> cir) {
        if (identifier.getNamespace().equals("simpleclient") && identifier.getPath().equals("motionblur")) {
            cir.setReturnValue(new MotionBlurResource());
        }
    }
}