package simpleclient.feature;

import com.google.gson.JsonObject;
import net.legacyfabric.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.legacyfabric.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.input.Keyboard;
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
        KeyBinding zoomKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("simpleclient.zoom", Keyboard.KEY_C, "simpleclient.category"));
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (zoomKey.isPressed() && !ACTIVE) {
                ACTIVE = true;
                minecraft.options.smoothCameraEnabled = getConfigValue(smoothCamera);
            } else if (!zoomKey.isPressed() && ACTIVE) {
                ACTIVE = false;
                minecraft.options.smoothCameraEnabled = false;
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