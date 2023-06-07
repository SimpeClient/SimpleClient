package simpleclient.adapter;

public abstract class TranslationAdapter {
    public static TranslationAdapter INSTANCE = null;

    public abstract String translate(String key, Object... args);
}