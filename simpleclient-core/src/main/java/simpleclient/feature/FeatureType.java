package simpleclient.feature;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

public enum FeatureType {
    BIOME("biome", "Biome"),
    COORDINATES_X("coords_x", "Coordinates - X"),
    COORDINATES_Y("coords_y", "Coordinates - Y"),
    COORDINATES_Z("coords_z", "Coordinates - Z"),
    FPS("fps", "FPS"),
    FULLBRIGHT("fullbright", "Fullbright"),
    LOWFIRE("lowfire", "Lowfire"),
    MOTIONBLUR("motionblur", "Motionblur"),
    OLD_ANIMATIONS("old_animations", "Old Animations", onlyOn("1.8.9")),
    PERFORMANCE_BOOST("performance_boost", "Performance Boost", notOn("1.8.9")),
    PERSPECTIVE("perspective", "Perspective"),
    PING("ping", "Ping"),
    PVP_IMPROVEMENTS("pvp_improvements", "PvP Improvements"),
    TNT_TIMER("tnt_timer", "TNT Timer"),
    ZOOM("zoom", "Zoom");

    private String id;
    private String name;
    private Function<String, Boolean> availableMinecraftVersions;

    FeatureType(String id, String name) {
        this.id = id;
        this.name = name;
        this.availableMinecraftVersions = version -> true;
    }

    FeatureType(String id, String name, Function<String, Boolean> availableMinecraftVersions) {
        this.id = id;
        this.name = name;
        this.availableMinecraftVersions = availableMinecraftVersions;
    }

    public boolean isAvailableFor(String minecraftVersion) {
        return availableMinecraftVersions.apply(minecraftVersion);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private static Function<String, Boolean> onlyOn(String... minecraftVersions) {
        return version -> Arrays.stream(minecraftVersions).anyMatch(Predicate.isEqual(version));
    }

    private static Function<String, Boolean> notOn(String... minecraftVersions) {
        return version -> !Arrays.stream(minecraftVersions).anyMatch(Predicate.isEqual(version));
    }
}