package simpleclient.feature;

public enum FeatureType {
    BIOME("biome", "Biome"),
    COORDINATES_X("coords_x", "Coordinates - X"),
    COORDINATES_Y("coords_y", "Coordinates - Y"),
    COORDINATES_Z("coords_z", "Coordinates - Z"),
    FPS("fps", "FPS"),
    FULLBRIGHT("fullbright", "Fullbright"),
    LOWFIRE("lowfire", "Lowfire"),
    PERFORMANCE_BOOST("performance_boost", "Performance Boost"),
    PING("ping", "Ping"),
    MOTIONBLUR("motionblur", "Motionblur"),
    ZOOM("zoom", "Zoom");

    private String id;
    private String name;

    FeatureType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}