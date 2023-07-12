package simpleclient.feature;

import com.google.gson.JsonObject;
import simpleclient.feature.config.EnableConfigEntry;
import simpleclient.text.Text;

public class PvPImprovements extends EnableableFeature {
    public static boolean ENABLED = false;
    public static boolean HIT_DELAY_FIX = false;
    public static boolean LEFTCLICK_DELAY_FIX = false;
    private final EnableConfigEntry hitDelayFix = new EnableConfigEntry("hit_delay_fix", Text.translatable("simpleclient.pvp_improvements.hit_delay_fix"), true);
    private final EnableConfigEntry leftclickDelayFix = new EnableConfigEntry("leftclick_delay_fix", Text.translatable("simpleclient.pvp_improvements.leftclick_delay_fix"), true);

    public PvPImprovements() {
        super(FeatureType.PVP_IMPROVEMENTS);
        addConfigEntry(hitDelayFix);
        addConfigEntry(leftclickDelayFix);
        refresh();
    }

    public void refresh() {
        ENABLED = isEnabled();
        HIT_DELAY_FIX = ENABLED && getConfigValue(hitDelayFix);
        LEFTCLICK_DELAY_FIX = ENABLED && getConfigValue(leftclickDelayFix);
    }

    @Override
    public void setData(JsonObject data) {
        super.setData(data);
        refresh();
    }
}
