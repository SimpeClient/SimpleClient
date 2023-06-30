package simpleclient.feature;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;
import simpleclient.gui.EditFeaturesScreen;

public class FeatureManagerImpl extends FeatureManager {
    @Override
    public void init() {
        KeyMapping editFeaturesKey = KeyBindingHelper.registerKeyBinding(new KeyMapping("simpleclient.edit_features", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "simpleclient.category"));
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (editFeaturesKey.consumeClick() && minecraft.screen == null && minecraft.level != null) {
                minecraft.setScreen(new EditFeaturesScreen());
            }
        });
        addFeature(new Biome());
        addFeature(new Coordinates(FeatureType.COORDINATES_X, "x", "X", () -> Minecraft.getInstance().player.getX()));
        addFeature(new Coordinates(FeatureType.COORDINATES_Y, "y", "Y", () -> Minecraft.getInstance().player.getY()));
        addFeature(new Coordinates(FeatureType.COORDINATES_Z, "z", "Z", () -> Minecraft.getInstance().player.getZ()));
        addFeature(new FPS());
        addFeature(new Fullbright());
        addFeature(new Lowfire());
        addFeature(new Motionblur());
        addFeature(new PerformanceBoost());
        addFeature(new Ping());
        addFeature(new Zoom());
        super.init();
    }
}