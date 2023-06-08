package simpleclient.adapter;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import simpleclient.text.Text;

public class TextRendererAdapterImpl extends TextRendererAdapter {
    private final PoseStack poseStack;
    private final Font font;
    
    public TextRendererAdapterImpl(PoseStack poseStack, Font font) {
        this.poseStack = poseStack;
        this.font = font;
    }

    @Override
    public void render(Text text, int x, int y) {
        font.draw(poseStack, TextAdapter.adapt(text), x, y, text.getStyle().getColor().getARGB());
        //font.draw(poseStack, TextAdapter.adapt(text), 50, 50, 0xFFFFFFFF);
    }

    @Override
    public void render(Text text, int x, int y, int color) {
        font.draw(poseStack, TextAdapter.adapt(text), x, y, color);
    }

    @Override
    public void renderWithShadow(Text text, int x, int y) {
        font.drawShadow(poseStack, TextAdapter.adapt(text), x, y, text.getStyle().getColor().getARGB());
    }

    @Override
    public void renderWithShadow(Text text, int x, int y, int color) {
        font.drawShadow(poseStack, TextAdapter.adapt(text), x, y, color);
    }

    @Override
    public int getWidth(Text text) {
        return font.width(TextAdapter.adapt(text));
    }

    @Override
    public int getWidth(String text) {
        return font.width(text);
    }

    @Override
    public int getHeight() {
        return font.lineHeight;
    }
}
