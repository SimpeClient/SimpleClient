package simpleclient.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.legacyfabric.fabric.impl.resource.loader.ModNioResourcePack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import simpleclient.SimpleClient;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow @Final private static Logger LOGGER;

    @Shadow protected abstract ByteBuffer readInputStreamAsImage(InputStream inputStream) throws IOException;

    @ModifyArg(at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V"), method = "setPixelFormat")
    public String setTitle(String newTitle) {
        return "Minecraft 1.8.9 | SimpleClient " + SimpleClient.VERSION;
    }

    /*@Inject(at = @At(value = "HEAD"), method = "setDefaultIcon", cancellable = true)
    public void setDefaultIcon(CallbackInfo ci) {
        ci.cancel();
        Util.OperatingSystem operatingSystem = Util.getOperatingSystem();
        if (operatingSystem != Util.OperatingSystem.MACOS) {
            InputStream inputStream = null;
            InputStream inputStream2 = null;
            try {
                ModContainer container = FabricLoader.getInstance().getModContainer("simpleclient").get();
                ModNioResourcePack resourcePack = new ModNioResourcePack(container, container.getRootPath(), null);
                inputStream = resourcePack.open(new Identifier("simpleclient", "textures/icons/icon_32x32.png"));
                inputStream2 = resourcePack.open(new Identifier("simpleclient", "textures/icons/icon_32x32.png"));
                if (inputStream != null && inputStream2 != null) {
                    Display.setIcon(new ByteBuffer[] {readInputStreamAsImage(inputStream), readInputStreamAsImage(inputStream2)});
                }
                resourcePack.close();
            } catch (IOException var8) {
                LOGGER.error("Couldn't set icon", var8);
            } finally {
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly(inputStream2);
            }
        }
    }*/
}