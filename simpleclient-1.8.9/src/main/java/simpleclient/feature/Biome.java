package simpleclient.feature;

import com.google.gson.JsonArray;
import net.minecraft.client.MinecraftClient;
import simpleclient.text.Style;

public class Biome extends DraggableTextFeature {
    public Biome() {
        super(FeatureType.BIOME);
    }

    @Override
    public String valueForParameter(String parameter) {
        if (parameter.equals("biome")) {
            MinecraftClient mc = MinecraftClient.getInstance();
            net.minecraft.world.biome.Biome biome = mc.world.getBiome(mc.player.getBlockPos());
            return biome == null ? "--------" : biome.name;
        } else return "";
    }

    @Override
    public String valueForDummyParameter(String parameter) {
        if (parameter.equals("biome")) return "--------";
        else return "";
    }

    @Override
    public JsonArray getDefaultFormat() {
        JsonArray format = new JsonArray();
        format.add(text("Biome: ", new Style()));
        format.add(parameter("biome", new Style()));
        return format;
    }
}
