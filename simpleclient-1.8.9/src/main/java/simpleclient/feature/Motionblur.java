package simpleclient.feature;

import simpleclient.feature.config.PercentConfigEntry;
import simpleclient.text.Text;

public class Motionblur extends EnableableFeature {
    private final PercentConfigEntry strength = new PercentConfigEntry("strength", Text.translatable("simpleclient.motionblur.strength"), 0.25F, 0);

    public Motionblur() {
        super(FeatureType.MOTIONBLUR);
        addConfigEntry(strength);
    }

    private float log(double base, double number) {
        return (float) (Math.log(number) / Math.log(base));
    }
}