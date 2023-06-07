package simpleclient.feature;

public class Feature {
    private final FeatureType type;

    public Feature(FeatureType type) {
        this.type = type;
    }

    public String getId() {
        return type.getId();
    }

    public String getName() {
        return type.getName();
    }

    public FeatureType getType() {
        return type;
    }
}
