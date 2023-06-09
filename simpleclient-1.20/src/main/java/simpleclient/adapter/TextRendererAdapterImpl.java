package simpleclient.adapter;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import simpleclient.text.Text;

public class TextRendererAdapterImpl extends TextRendererAdapter {
    private final GuiGraphics guiGraphics;
    private final Font font;
    
    public TextRendererAdapterImpl(GuiGraphics guiGraphics, Font font) {
        this.guiGraphics = guiGraphics;
        this.font = font;
    }

    @Override
    public void render(Text text, int x, int y) {
        guiGraphics.drawString(font, TextAdapter.adapt(text), x, y, text.getStyle().getColor().getARGB(), false);
    }

    @Override
    public void render(Text text, int x, int y, int color) {
        guiGraphics.drawString(font, TextAdapter.adapt(text), x, y, color, false);
    }

    @Override
    public void renderWithShadow(Text text, int x, int y) {
        guiGraphics.drawString(font, TextAdapter.adapt(text), x, y, text.getStyle().getColor().getARGB(), true);
    }

    @Override
    public void renderWithShadow(Text text, int x, int y, int color) {
        guiGraphics.drawString(font, TextAdapter.adapt(text), x, y, color, true);
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
