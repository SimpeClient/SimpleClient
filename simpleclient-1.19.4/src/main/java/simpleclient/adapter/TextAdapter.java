package simpleclient.adapter;

import net.minecraft.network.chat.Component;
import simpleclient.text.EmptyText;
import simpleclient.text.LiteralText;
import simpleclient.text.Text;
import simpleclient.text.TranslatableText;

public class TextAdapter {
    public static Component adapt(Text text) {
        Component adapted = null;
        if (text instanceof EmptyText) {
            adapted = Component.empty();
        } else if (text instanceof LiteralText) {
            LiteralText literal = (LiteralText) text;
            adapted = Component.literal(literal.getContent());
        } else if (text instanceof TranslatableText) {
            TranslatableText translatable = (TranslatableText) text;
            adapted = Component.translatable(translatable.getKey(), translatable.getArgs());
        } else adapted = Component.empty();
        if (text.getChildren() != null) {
            for (Text child : text.getChildren()) {
                adapted.getSiblings().add(adapt(child));
            }
        }
        return adapted;
    }
}