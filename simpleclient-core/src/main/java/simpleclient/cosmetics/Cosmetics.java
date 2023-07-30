package simpleclient.cosmetics;

import simpleclient.cosmetics.builtins.HatCosmetic;

import java.util.ArrayList;
import java.util.List;

public class Cosmetics {
    private static final List<Cosmetic> cosmetics = new ArrayList<>();

    public static void load() {
        cosmetics.add(new HatCosmetic());
    }

    public static List<Cosmetic> getCosmetics() {
        return cosmetics;
    }
}