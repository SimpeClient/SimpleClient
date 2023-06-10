package simpleclient.feature;

public enum FeatureType {
    FPS("fps", "FPS"),
    FULLBRIGHT("fullbright", "Fullbright"),
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