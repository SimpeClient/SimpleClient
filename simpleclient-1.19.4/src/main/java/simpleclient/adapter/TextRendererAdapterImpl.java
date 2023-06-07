package simpleclient.adapter;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import simpleclient.text.Text;

public class TextRendererAdapterImpl extends TextRendererAdapter {
    private PoseStack poseStack;
    
    public TextRendererAdapterImpl(PoseStack poseStack) {
        this.poseStack = poseStack;
    }

    @Override
    public void render(Text text, int x, int y) {
        Minecraft.getInstance().font.draw(poseStack, TextAdapter.adapt(text), x, y, text.getStyle().getColor().getARGB());
    }

    @Override
    public void render(Text text, int x, int y, int color) {
        Minecraft.getInstance().font.draw(poseStack, TextAdapter.adapt(text), x, y, color);
    }

    @Override
    public void renderWithShadow(Text text, int x, int y) {
        Minecraft.getInstance().font.drawShadow(poseStack, TextAdapter.adapt(text), x, y, text.getStyle().getColor().getARGB());
    }

    @Override
    public void renderWithShadow(Text text, int x, int y, int color) {
        Minecraft.getInstance().font.drawShadow(poseStack, TextAdapter.adapt(text), x, y, color);
    }

    @Override
    public int getWidth(Text text) {
        return Minecraft.getInstance().font.width(TextAdapter.adapt(text));
    }

    @Override
    public int getWidth(String text) {
        return Minecraft.getInstance().font.width(text);
    }

    @Override
    public int getHeight() {
        return Minecraft.getInstance().font.lineHeight;
    }
}
