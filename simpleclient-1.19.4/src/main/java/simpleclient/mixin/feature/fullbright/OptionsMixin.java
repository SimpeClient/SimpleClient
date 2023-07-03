package simpleclient.mixin.feature.fullbright;

import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import simpleclient.util.GammaOptionInstance;

import java.io.File;

@Mixin(Options.class)
public abstract class OptionsMixin {
    @Shadow private OptionInstance<Double> gamma;

    @Inject(at = @At("TAIL"), method = "<init>")
    private void init(Minecraft minecraft, File file, CallbackInfo ci) {
        gamma = new GammaOptionInstance();
    }
}