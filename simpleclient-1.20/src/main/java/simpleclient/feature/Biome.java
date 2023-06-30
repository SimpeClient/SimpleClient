package simpleclient.feature;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import simpleclient.text.Style;

public class Biome extends DraggableTextFeature {
    public Biome() {
        super(FeatureType.BIOME);
    }

    @Override
    public String valueForParameter(String parameter) {
        if (parameter.equals("biome")) {
            Minecraft mc = Minecraft.getInstance();
            Holder<net.minecraft.world.level.biome.Biome> biome = mc.level.getBiome(mc.player.getOnPos());
            if (biome.isBound() && biome.unwrapKey().isPresent()) {
                String id = biome.unwrapKey().get().location().toString().split(":")[1];
                String[] name = id.split("_");
                for (int i = 0; i < name.length; i++) name[i] = name[i].substring(0, 1).toUpperCase() + name[i].substring(1);
                return String.join(" ", Lists.newArrayList(name));
            } else return "--------";
        } else return "";
    }

    @Override
    public String valueForDummyParameter(String parameter) {
        if (parameter.equals("biome")) return "????????";
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
