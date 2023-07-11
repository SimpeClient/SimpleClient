package simpleclient.mixin;

import com.mojang.blaze3d.platform.IconSet;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.resources.IoSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.InputStream;

@Mixin(IconSet.class)
public abstract class IconSetMixin {
    @ModifyArg(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/IconSet;getFile(Lnet/minecraft/server/packs/PackResources;Ljava/lang/String;)Lnet/minecraft/server/packs/resources/IoSupplier;", ordinal = 0), method = "getStandardIcons", index = 1)
    public String getFile(String string) {
        return "icon_32x32.png";
    }

    @Inject(at = @At("HEAD"), method = "getFile", cancellable = true)
    public void getFile(PackResources packResources, String string, CallbackInfoReturnable<IoSupplier<InputStream>> cir) {
        cir.setReturnValue(() -> Minecraft.getInstance().getResourceManager().open(new ResourceLocation("simpleclient", "textures/icons/" + string)));
    }
}