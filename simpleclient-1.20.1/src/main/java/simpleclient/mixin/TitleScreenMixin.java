package simpleclient.mixin;

import net.minecraft.SharedConstants;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import simpleclient.SimpleClient;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {
    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)I"), method = "render", index = 1)
    public String render(String str) {
        return "Minecraft " + SharedConstants.getCurrentVersion().getName() + " | SimpleClient " + SimpleClient.VERSION;
    }
}