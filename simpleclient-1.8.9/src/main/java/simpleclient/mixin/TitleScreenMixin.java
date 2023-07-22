package simpleclient.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import simpleclient.SimpleClient;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {
    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V", ordinal = 0), method = "render", index = 1)
    public String render(String string) {
        return "Minecraft 1.8.9 | SimpleClient " + SimpleClient.VERSION;
    }
}