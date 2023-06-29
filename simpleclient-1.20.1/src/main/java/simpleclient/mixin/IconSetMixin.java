package simpleclient.mixin;

import com.mojang.blaze3d.platform.IconSet;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.resources.IoSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.InputStream;

@Mixin(IconSet.class)
public abstract class IconSetMixin {
    @Inject(at = @At("HEAD"), method = "getFile", cancellable = true)
    public void getFile(PackResources packResources, String string, CallbackInfoReturnable<IoSupplier<InputStream>> cir) {
        cir.setReturnValue(() -> Minecraft.getInstance().getResourceManager().open(new ResourceLocation("simpleclient", "textures/icons/" + string)));
    }
}