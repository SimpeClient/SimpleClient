package simpleclient.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;

public class DrawUtil {
    public static void drawCircle(PoseStack poseStack, int x, int y, int radius, float degrees, float rotation, int color) {
        float detail = 1.1F;
        int i = (int) (2 * Math.PI * radius * detail);
        for (int j = 0; j < i; j++) {
            poseStack.pushPose();
            poseStack.translate(x, y, 0);
            poseStack.pushPose();
            poseStack.rotateAround(Axis.ZP.rotationDegrees(rotation + degrees / i * j), 0, 0, 0);
            GuiComponent.vLine(poseStack, 0, -radius - 1, 0, color);
            poseStack.popPose();
            poseStack.popPose();
        }
    }

    public static void drawCircle(PoseStack poseStack, int x, int y, int radius, float degrees, int color) {
        drawCircle(poseStack, x, y, radius, degrees, 0.0F, color);
    }

    public static void drawCircle(PoseStack poseStack, int x, int y, int radius, int color) {
        drawCircle(poseStack, x, y, radius, 360.0F, color);
    }
}