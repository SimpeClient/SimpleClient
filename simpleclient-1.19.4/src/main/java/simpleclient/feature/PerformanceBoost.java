package simpleclient.feature;

public class PerformanceBoost extends EnableableFeature {
    public static PerformanceBoost INSTANCE = new PerformanceBoost();
    public PerformanceBoost() {
        super(FeatureType.FPS);
    }
}
