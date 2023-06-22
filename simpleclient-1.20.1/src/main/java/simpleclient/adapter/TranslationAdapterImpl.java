package simpleclient.adapter;

import net.minecraft.locale.Language;

public class TranslationAdapterImpl extends TranslationAdapter {
    @Override
    public String translate(String key, Object... args) {
        String value = Language.getInstance().getOrDefault(key);
        return value.formatted(args);
    }
}