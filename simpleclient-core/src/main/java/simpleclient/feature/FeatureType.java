package simpleclient.feature;

public enum FeatureType {
    FPS("fps", "FPS"),
    PERFORMANCE_BOOST("performance_boost", "Performance Boost");

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