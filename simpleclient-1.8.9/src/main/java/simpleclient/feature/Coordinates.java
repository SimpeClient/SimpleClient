package simpleclient.feature;

import com.google.gson.JsonArray;
import simpleclient.text.Style;

import java.util.function.Supplier;

public class Coordinates extends DraggableTextFeature {
    private String parameter;
    private String letter;
    private Supplier<Double> coordinateSupplier;

    public Coordinates(FeatureType type, String parameter, String letter, Supplier<Double> coordinateSupplier) {
        super(type);
        this.parameter = parameter;
        this.letter = letter;
        this.coordinateSupplier = coordinateSupplier;
    }

    @Override
    public String valueForParameter(String parameter) {
        if (parameter.equals(this.parameter)) return String.valueOf((float) (int) (coordinateSupplier.get() * 100) / 100);
        else return "";
    }

    @Override
    public String valueForDummyParameter(String parameter) {
        if (parameter.equals(this.parameter)) return "???.??";
        else return "";
    }

    @Override
    public JsonArray getDefaultFormat() {
        JsonArray format = new JsonArray();
        format.add(text(letter + ": ", new Style()));
        format.add(parameter(parameter, new Style()));
        return format;
    }
}
