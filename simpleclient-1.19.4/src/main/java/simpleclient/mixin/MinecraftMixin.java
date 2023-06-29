package simpleclient.mixin;

import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.IoSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import simpleclient.SimpleClient;

import java.io.InputStream;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Inject(at = @At("HEAD"), method = "createTitle", cancellable = true)
    public void createTitle(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue("Minecraft " + SharedConstants.getCurrentVersion().getName() + " | SimpleClient " + SimpleClient.VERSION);
    }

    @Inject(at = @At("HEAD"), method = "getIconFile", cancellable = true)
    public void getIconFile(String[] strings, CallbackInfoReturnable<IoSupplier<InputStream>> cir) {
        cir.setReturnValue(() -> Minecraft.getInstance().getResourceManager().open(new ResourceLocation("simpleclient", "textures/" + String.join("/", strings))));
    }
}