package simpleclient.mixin;

import com.mojang.blaze3d.platform.IconSet;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.VanillaPackResources;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import simpleclient.SimpleClient;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow @Final private Window window;

    @Shadow @Final private VanillaPackResources vanillaPackResources;

    @Inject(at = @At("HEAD"), method = "createTitle", cancellable = true)
    public void createTitle(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue("Minecraft " + SharedConstants.getCurrentVersion().getName() + " | SimpleClient " + SimpleClient.VERSION);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;setIcon(Lnet/minecraft/server/packs/PackResources;Lcom/mojang/blaze3d/platform/IconSet;)V"), method = "<init>")
    public void setIcon(Window instance, PackResources packResources, IconSet iconSet) {}

    @Inject(at = @At("TAIL"), method = "<init>")
    public void init(GameConfig gameConfig, CallbackInfo ci) {
        try {
            window.setIcon(vanillaPackResources, SharedConstants.getCurrentVersion().isStable() ? IconSet.RELEASE : IconSet.SNAPSHOT);
        } catch (Exception e) {e.printStackTrace();}
    }
}