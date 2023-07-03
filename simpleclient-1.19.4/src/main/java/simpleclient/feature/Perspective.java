package simpleclient.feature;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.CameraType;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class Perspective extends Feature {
    public static boolean ACTIVE = false;
    public static float YAW = 0;
    public static float PITCH = 0;
    private CameraType previousPerspective;

    public Perspective() {
        super(FeatureType.PERSPECTIVE);
        KeyMapping key = KeyBindingHelper.registerKeyBinding(new KeyMapping("simpleclient.perspective", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, "simpleclient.category"));
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (key.isDown() && !ACTIVE) {
                ACTIVE = true;
                previousPerspective = minecraft.options.getCameraType();
                minecraft.options.setCameraType(CameraType.THIRD_PERSON_BACK);
            } else if (!key.isDown() && ACTIVE) {
                ACTIVE = false;
                if (previousPerspective != null) minecraft.options.setCameraType(previousPerspective);
                previousPerspective = null;
            }
        });
    }
}