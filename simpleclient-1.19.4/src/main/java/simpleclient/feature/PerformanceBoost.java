package simpleclient.feature;

public class PerformanceBoost extends EnableableFeature {
    public static PerformanceBoost INSTANCE = new PerformanceBoost();
    public static boolean ENABLED = INSTANCE.isEnabled();
    public PerformanceBoost() {
        super(FeatureType.PERFORMANCE_BOOST);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        ENABLED = enabled;
    }
}
