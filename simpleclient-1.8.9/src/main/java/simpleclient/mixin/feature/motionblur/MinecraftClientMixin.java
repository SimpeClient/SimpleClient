package simpleclient.mixin.feature.motionblur;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import simpleclient.feature.Fullbright;
import simpleclient.feature.Motionblur;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	@Inject(at = @At(value = "TAIL"), method = "initializeGame")
	public void initializeGame(CallbackInfo info) {
		Motionblur.INSTANCE.refresh();
	}
}