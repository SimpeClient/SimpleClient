package simpleclient.util;

import com.mojang.math.Axis;
import net.minecraft.client.gui.GuiGraphics;

public class DrawUtil {
    public static void drawCircle(GuiGraphics guiGraphics, int x, int y, int radius, float degrees, float rotation, int color) {
        float detail = 1.1F;
        int i = (int) (2 * Math.PI * radius * detail);
        for (int j = 0; j < i; j++) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(x, y, 0);
            guiGraphics.pose().pushPose();
            guiGraphics.pose().rotateAround(Axis.ZP.rotationDegrees(rotation + degrees / i * j), 0, 0, 0);
            guiGraphics.vLine(0, -radius - 1, 0, color);
            guiGraphics.pose().popPose();
            guiGraphics.pose().popPose();
        }
    }

    public static void drawCircle(GuiGraphics guiGraphics, int x, int y, int radius, float degrees, int color) {
        drawCircle(guiGraphics, x, y, radius, degrees, 0.0F, color);
    }

    public static void drawCircle(GuiGraphics guiGraphics, int x, int y, int radius, int color) {
        drawCircle(guiGraphics, x, y, radius, 360.0F, color);
    }
}