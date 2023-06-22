package simpleclient.adapter;

import net.minecraft.client.font.TextRenderer;
import simpleclient.text.Text;

public class TextRendererAdapterImpl extends TextRendererAdapter {
    private final TextRenderer font;
    
    public TextRendererAdapterImpl(TextRenderer font) {
        this.font = font;
    }

    @Override
    public void render(Text text, int x, int y) {
        font.draw(TextAdapter.adapt(text).asFormattedString(), x, y, text.getStyle().getColor().getARGB());
    }

    @Override
    public void render(Text text, int x, int y, int color) {
        font.draw(TextAdapter.adapt(text).asFormattedString(), x, y, color);
    }

    @Override
    public void renderWithShadow(Text text, int x, int y) {
        font.drawWithShadow(TextAdapter.adapt(text).asFormattedString(), x, y, text.getStyle().getColor().getARGB());
    }

    @Override
    public void renderWithShadow(Text text, int x, int y, int color) {
        font.drawWithShadow(TextAdapter.adapt(text).asFormattedString(), x, y, color);
    }

    @Override
    public int getWidth(Text text) {
        return font.getStringWidth(TextAdapter.adapt(text).asUnformattedString());
    }

    @Override
    public int getWidth(String text) {
        return font.getStringWidth(text);
    }

    @Override
    public int getHeight() {
        return font.fontHeight;
    }
}
