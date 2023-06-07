package simpleclient.adapter;

import simpleclient.text.Text;

public abstract class TextRendererAdapter {
    public abstract void render(Text text, int x, int y);
    public abstract void render(Text text, int x, int y, int color);
    public abstract void renderWithShadow(Text text, int x, int y);
    public abstract void renderWithShadow(Text text, int x, int y, int color);
    public abstract int getWidth(Text text);
    public abstract int getWidth(String text);
    public abstract int getHeight();
}