package simpleclient.feature;

import net.legacyfabric.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.legacyfabric.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.input.Keyboard;

public class Perspective extends Feature {
    public static boolean ACTIVE = false;
    public static float YAW = 0;
    public static float PITCH = 0;
    private int previousPerspective;

    public Perspective() {
        super(FeatureType.PERSPECTIVE);
        KeyBinding key = KeyBindingHelper.registerKeyBinding(new KeyBinding("simpleclient.perspective", Keyboard.KEY_LMENU, "simpleclient.category"));
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (key.isPressed() && !ACTIVE) {
                ACTIVE = true;
                previousPerspective = minecraft.options.perspective;
                minecraft.options.perspective = 1;
            } else if (!key.isPressed() && ACTIVE) {
                ACTIVE = false;
                if (previousPerspective != -1) minecraft.options.perspective = previousPerspective;
                previousPerspective = -1;
            }
        });
    }
}