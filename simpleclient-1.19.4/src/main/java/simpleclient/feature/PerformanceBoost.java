package simpleclient.feature;

import com.google.gson.JsonObject;
import net.minecraft.network.chat.Component;
import simpleclient.feature.config.EnableConfigEntry;

public class PerformanceBoost extends EnableableFeature {
    public static boolean ENABLED = false;
    public static boolean FASTBOOT = false;
    public static boolean DONT_RENDER_CLOUDS = false;
    public static boolean DONT_RENDER_SKY_UNDER_WATER = false;
    private final EnableConfigEntry fastBoot = new EnableConfigEntry("fastboot", Component.translatable("simpleclient.performance.fastboot"), true);
    private final EnableConfigEntry dontRenderCouds = new EnableConfigEntry("dont_render_clouds", Component.translatable("simpleclient.performance.dont_render_clouds"), true);
    private final EnableConfigEntry dontRenderSkyUnderWater = new EnableConfigEntry("dont_render_sky_under_water", Component.translatable("simpleclient.performance.dont_render_sky_under_water"), true);

    public PerformanceBoost() {
        super(FeatureType.PERFORMANCE_BOOST);
        addConfigEntry(fastBoot);
        addConfigEntry(dontRenderCouds);
        addConfigEntry(dontRenderSkyUnderWater);
        refresh();
    }

    public void refresh() {
        ENABLED = isEnabled();
        JsonObject data = getData();
        FASTBOOT = ENABLED && fastBoot.load(data);
        DONT_RENDER_CLOUDS = ENABLED && dontRenderCouds.load(data);
        DONT_RENDER_SKY_UNDER_WATER = ENABLED && dontRenderSkyUnderWater.load(data);
    }

    @Override
    public void setData(JsonObject data) {
        super.setData(data);
        refresh();
    }
}
