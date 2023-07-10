package simpleclient.mixin;

import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import simpleclient.feature.Fullbright;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
	@Inject(at = @At("TAIL"), method = "load")
	public void load(CallbackInfo info) {
		Fullbright.INSTANCE.refresh();
	}
}