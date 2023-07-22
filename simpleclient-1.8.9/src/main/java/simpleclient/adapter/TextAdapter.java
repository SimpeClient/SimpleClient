package simpleclient.adapter;

import simpleclient.text.EmptyText;
import simpleclient.text.LiteralText;
import simpleclient.text.Text;
import simpleclient.text.TranslatableText;

public class TextAdapter {
    public static net.minecraft.text.Text adapt(Text text) {
        net.minecraft.text.Text adapted = null;
        if (text instanceof EmptyText) {
            adapted = new net.minecraft.text.LiteralText("");
        } else if (text instanceof LiteralText) {
            LiteralText literal = (LiteralText) text;
            adapted = new net.minecraft.text.LiteralText(literal.getContent());
        } else if (text instanceof TranslatableText) {
            TranslatableText translatable = (TranslatableText) text;
            adapted = new net.minecraft.text.TranslatableText(translatable.getKey(), translatable.getArgs());
        } else adapted = new net.minecraft.text.LiteralText("");
        if (text.getChildren() != null) {
            for (Text child : text.getChildren()) {
                adapted.getSiblings().add(adapt(child));
            }
        }
        return adapted;
    }
}