package simpleclient.util;

import net.minecraft.client.OptionInstance;
import net.minecraft.network.chat.Component;
import simpleclient.feature.Fullbright;

public class GammaOptionInstance extends OptionInstance<Double> {
    public GammaOptionInstance() {
        super(
                "options.gamma",
                OptionInstance.noTooltip(),
                (component, value) -> {
                    if (Fullbright.ENABLED) return component.copy().append(": Fullbright");
                    else if (value == 0.0) return component.copy().append(": ").append(Component.translatable("options.gamma.min"));
                    else if (value == 0.5) return component.copy().append(": ").append(Component.translatable("options.gamma.default"));
                    else if (value == 1.0) return component.copy().append(": ").append(Component.translatable("options.gamma.max"));
                    else return component.copy().append(": " + (int) (value * 100));
                },
                OptionInstance.UnitDouble.INSTANCE,
                0.5, value -> {}
        );
    }

    @Override
    public Double get() {
        return Fullbright.ENABLED ? 100.0 : super.get();
    }
}