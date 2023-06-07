package simpleclient.text;

import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;

public class Style {
    private boolean bold;
    private boolean italic;
    private boolean underlined;
    private boolean strikethrough;
    private boolean obfuscated;
    private TextColor color;

    public Style() {
        this(false, false, false, false, false, TextColor.WHITE);
    }

    public Style(boolean bold, boolean italic, boolean underlined, boolean strikethrough, boolean obfuscated, TextColor color) {
        this.bold = bold;
        this.italic = italic;
        this.underlined = underlined;
        this.strikethrough = strikethrough;
        this.obfuscated = obfuscated;
        this.color = color;
    }

    public boolean isBold() {
        return bold;
    }

    public Style setBold(boolean bold) {
        this.bold = bold;
        return this;
    }

    public boolean isItalic() {
        return italic;
    }

    public Style setItalic(boolean italic) {
        this.italic = italic;
        return this;
    }

    public boolean isUnderlined() {
        return underlined;
    }

    public Style setUnderlined(boolean underlined) {
        this.underlined = underlined;
        return this;
    }

    public boolean isStrikethrough() {
        return strikethrough;
    }

    public Style setStrikethrough(boolean strikethrough) {
        this.strikethrough = strikethrough;
        return this;
    }

    public boolean isObfuscated() {
        return obfuscated;
    }

    public Style setObfuscated(boolean obfuscated) {
        this.obfuscated = obfuscated;
        return this;
    }

    public TextColor getColor() {
        return color;
    }

    public Style setColor(TextColor color) {
        this.color = color;
        return this;
    }

    public JsonObject serializeJson() {
        JsonObject json = new JsonObject();
        json.addProperty("bold", bold);
        json.addProperty("italic", italic);
        json.addProperty("underlined", underlined);
        json.addProperty("strikethrough", strikethrough);
        json.addProperty("obfuscated", obfuscated);
        json.addProperty("color", color.serialize());
        return json;
    }

    public String serialize() {
        return serializeJson().toString();
    }

    public static Style deserializeJson(JsonObject json) {
        Style style = new Style();
        style.bold = json.has("bold") ? json.get("bold").getAsBoolean() : false;
        style.italic = json.has("italic") ? json.get("italic").getAsBoolean() : false;
        style.underlined = json.has("underlined") ? json.get("underlined").getAsBoolean() : false;
        style.strikethrough = json.has("strikethrough") ? json.get("strikethrough").getAsBoolean() : false;
        style.obfuscated = json.has("obfuscated") ? json.get("obfuscated").getAsBoolean() : false;
        style.color = json.has("color") ? TextColor.deserialize(json.get("color").getAsString()) : TextColor.WHITE;
        return style;
    }

    public static Style deserialize(String serialized) {
        return deserializeJson(new JsonStreamParser(serialized).next().getAsJsonObject());
    }
}