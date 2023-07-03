package simpleclient.mixin;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.server.packs.resources.IoSupplier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import simpleclient.SimpleClient;

import java.io.IOException;
import java.io.InputStream;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow @Final private Window window;

    @Shadow protected abstract IoSupplier<InputStream> getIconFile(String... par1) throws IOException;

    @Inject(at = @At("HEAD"), method = "createTitle", cancellable = true)
    public void createTitle(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue("Minecraft " + SharedConstants.getCurrentVersion().getName() + " | SimpleClient " + SimpleClient.VERSION);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;setIcon(Lnet/minecraft/server/packs/resources/IoSupplier;Lnet/minecraft/server/packs/resources/IoSupplier;)V"), method = "<init>")
    public void setIcon(Window instance, IoSupplier ioSupplier, IoSupplier ioSupplier2) {}

    @Inject(at = @At("TAIL"), method = "<init>")
    public void init(GameConfig gameConfig, CallbackInfo ci) {
        try {
            window.setIcon(getIconFile("icons", "icon_16x16.png"), getIconFile("icons", "icon_32x32.png"));
        } catch (Exception e) {e.printStackTrace();}
    }

    @Inject(at = @At("HEAD"), method = "getIconFile", cancellable = true)
    public void getIconFile(String[] strings, CallbackInfoReturnable<IoSupplier<InputStream>> cir) {
        cir.setReturnValue(() -> Minecraft.getInstance().getResourceManager().open(new ResourceLocation("simpleclient", "textures/" + String.join("/", strings))));
    }
}