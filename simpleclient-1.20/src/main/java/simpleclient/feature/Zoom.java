package simpleclient.feature;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import simpleclient.feature.config.EnableConfigEntry;
import simpleclient.feature.config.FloatConfigEntry;
import simpleclient.text.Text;

public class Zoom extends Feature {
    public static boolean ACTIVE = false;
    public static float ZOOM_FACTOR = 0;
    private FloatConfigEntry zoomFactor = new FloatConfigEntry("zoom_factor", Text.translatable("simpleclient.zoom.zoom_factor"), 0.8F, value -> Text.literal("x" + (float) (int) (10 / (1 - value)) / 10));
    private EnableConfigEntry smoothCamera = new EnableConfigEntry("smooth_camera", Text.translatable("simpleclient.zoom.smooth_camera"), true);

    public Zoom() {
        super(FeatureType.ZOOM);
        KeyMapping zoomKey = KeyBindingHelper.registerKeyBinding(new KeyMapping("simpleclient.zoom", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, "simpleclient.category"));
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (zoomKey.isDown() && !ACTIVE) {
                ACTIVE = true;
                minecraft.options.smoothCamera = getConfigValue(smoothCamera);
            } else if (!zoomKey.isDown() && ACTIVE) {
                ACTIVE = false;
                minecraft.options.smoothCamera = false;
            }
        });
        addConfigEntry(zoomFactor);
        addConfigEntry(smoothCamera);
        refresh();
    }

    public void refresh() {
        ZOOM_FACTOR = getConfigValue(zoomFactor);
    }

    @Override
    public void setData(JsonObject data) {
        super.setData(data);
        refresh();
    }
}