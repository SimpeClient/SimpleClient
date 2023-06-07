package simpleclient.adapter;

import net.minecraft.util.Language;

public class TranslationAdapterImpl extends TranslationAdapter {
    @Override
    public String translate(String key, Object... args) {
        String value = Language.getInstance().get(key);
        return value.formatted(args);
    }
}