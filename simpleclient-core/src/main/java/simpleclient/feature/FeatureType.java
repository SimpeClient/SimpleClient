package simpleclient.feature;

import java.util.List;

public enum FeatureType {
    BIOME("biome", "Biome", "1.8.9", "1.19.4", "1.20", "1.20.1"),
    COORDINATES_X("coords_x", "Coordinates - X", "1.8.9", "1.19.4", "1.20", "1.20.1"),
    COORDINATES_Y("coords_y", "Coordinates - Y", "1.8.9", "1.19.4", "1.20", "1.20.1"),
    COORDINATES_Z("coords_z", "Coordinates - Z", "1.8.9", "1.19.4", "1.20", "1.20.1"),
    FPS("fps", "FPS", "1.8.9", "1.19.4", "1.20", "1.20.1"),
    FULLBRIGHT("fullbright", "Fullbright", "1.8.9", "1.19.4", "1.20", "1.20.1"),
    LOWFIRE("lowfire", "Lowfire", "1.8.9", "1.19.4", "1.20", "1.20.1"),
    PERFORMANCE_BOOST("performance_boost", "Performance Boost", "1.19.4", "1.20", "1.20.1"),
    PERSPECTIVE("perspective", "Perspective", "1.8.9", "1.19.4", "1.20", "1.20.1"),
    PING("ping", "Ping", "1.8.9", "1.19.4", "1.20", "1.20.1"),
    MOTIONBLUR("motionblur", "Motionblur", "1.8.9", "1.19.4", "1.20", "1.20.1"),
    ZOOM("zoom", "Zoom", "1.8.9", "1.19.4", "1.20", "1.20.1");

    private String id;
    private String name;
    private String[] availableMinecraftVersions;

    FeatureType(String id, String name, String... availableMinecraftVersions) {
        this.id = id;
        this.name = name;
        this.availableMinecraftVersions = availableMinecraftVersions;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String[] getAvailableMinecraftVersions() {
        return availableMinecraftVersions;
    }
}