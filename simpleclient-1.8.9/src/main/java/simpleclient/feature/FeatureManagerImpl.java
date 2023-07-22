package simpleclient.feature;

import net.legacyfabric.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.legacyfabric.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.input.Keyboard;
import simpleclient.gui.EditFeaturesScreen;

public class FeatureManagerImpl extends FeatureManager {
    @Override
    public void init() {
        KeyBinding editFeaturesKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("simpleclient.edit_features", Keyboard.KEY_RSHIFT, "simpleclient.category"));
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (editFeaturesKey.wasPressed() && minecraft.currentScreen == null && minecraft.world != null) {
                minecraft.setScreen(new EditFeaturesScreen());
            }
        });
        addFeature(new Biome());
        addFeature(new Coordinates(FeatureType.COORDINATES_X, "x", "X", () -> MinecraftClient.getInstance().player.x));
        addFeature(new Coordinates(FeatureType.COORDINATES_Y, "y", "Y", () -> MinecraftClient.getInstance().player.y));
        addFeature(new Coordinates(FeatureType.COORDINATES_Z, "z", "Z", () -> MinecraftClient.getInstance().player.z));
        addFeature(new FPS());
        addFeature(new Fullbright());
        addFeature(new Lowfire());
        addFeature(new Motionblur());
        addFeature(new OldAnimations());
        addFeature(new Perspective());
        addFeature(new Ping());
        addFeature(new PvPImprovements());
        addFeature(new TNTTimer());
        addFeature(new Zoom());
        super.init();
    }
}