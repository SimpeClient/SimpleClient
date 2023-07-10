package simpleclient.adapter;

import net.minecraft.client.resource.language.I18n;

public class TranslationAdapterImpl extends TranslationAdapter {
    @Override
    public String translate(String key, Object... args) {
        return I18n.translate(key, args);
    }
}