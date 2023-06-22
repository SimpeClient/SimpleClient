package simpleclient.text;

import java.util.List;

public abstract class Text {
    private Style style;
    private List<Text> children;

    public Style getStyle() {
        return style;
    }

    public Text setStyle(Style style) {
        this.style = style;
        return this;
    }

    public List<Text> getChildren() {
        return children;
    }

    public Text append(Text child) {
        children.add(child);
        return this;
    }

    public Text append(String child) {
        children.add(literal(child));
        return this;
    }

    public Text remove(Text child) {
        children.remove(child);
        return this;
    }

    public Text remove(int index) {
        children.remove(index);
        return this;
    }

    public static EmptyText empty() {
        return new EmptyText();
    }

    public static LiteralText literal(String content) {
        return new LiteralText(content);
    }

    public static TranslatableText translatable(String key, Object... args) {
        return new TranslatableText(key, args);
    }
}