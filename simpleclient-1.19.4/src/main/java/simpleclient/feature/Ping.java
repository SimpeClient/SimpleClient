package simpleclient.feature;

import com.google.gson.JsonArray;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import simpleclient.text.Style;

public class Ping extends DraggableTextFeature {
    public Ping() {
        super(FeatureType.PING);
    }

    @Override
    public String valueForParameter(String parameter) {
        if (parameter.equals("ping")) {
            Minecraft minecraft = Minecraft.getInstance();
            PlayerInfo entry = minecraft.getConnection().getPlayerInfo(minecraft.player.getUUID());
            return entry == null ? "-" : (entry.getLatency() + "ms");
        } else return "";
    }

    @Override
    public String valueForDummyParameter(String parameter) {
        if (parameter.equals("ping")) return "-";
        else return "";
    }

    @Override
    public JsonArray getDefaultFormat() {
        JsonArray format = new JsonArray();
        format.add(text("Ping: ", new Style()));
        format.add(parameter("ping", new Style()));
        return format;
    }
}
