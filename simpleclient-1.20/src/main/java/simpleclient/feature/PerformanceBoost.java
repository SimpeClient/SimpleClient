package simpleclient.feature;

import com.google.gson.JsonObject;
import simpleclient.feature.config.EnableConfigEntry;
import simpleclient.text.Text;

public class PerformanceBoost extends EnableableFeature {
    public static boolean ENABLED = false;
    public static boolean FASTBOOT = false;
    public static boolean DONT_RENDER_CLOUDS = false;
    public static boolean DONT_RENDER_SKY_UNDER_WATER = false;
    private final EnableConfigEntry fastBoot = new EnableConfigEntry("fastboot", Text.translatable("simpleclient.performance.fastboot"), true);
    private final EnableConfigEntry dontRenderCouds = new EnableConfigEntry("dont_render_clouds", Text.translatable("simpleclient.performance.dont_render_clouds"), true);
    private final EnableConfigEntry dontRenderSkyUnderWater = new EnableConfigEntry("dont_render_sky_under_water", Text.translatable("simpleclient.performance.dont_render_sky_under_water"), true);

    public PerformanceBoost() {
        super(FeatureType.PERFORMANCE_BOOST);
        addConfigEntry(fastBoot);
        addConfigEntry(dontRenderCouds);
        addConfigEntry(dontRenderSkyUnderWater);
        refresh();
    }

    public void refresh() {
        ENABLED = isEnabled();
        FASTBOOT = ENABLED && getConfigValue(fastBoot);
        DONT_RENDER_CLOUDS = ENABLED && getConfigValue(dontRenderCouds);
        DONT_RENDER_SKY_UNDER_WATER = ENABLED && getConfigValue(dontRenderSkyUnderWater);
    }

    @Override
    public void setData(JsonObject data) {
        super.setData(data);
        refresh();
    }
}
