package simpleclient.cosmetics;

import simpleclient.cosmetics.model.CosmeticModel;

public class Cosmetic {
    private final String name;
    private final String id;
    private final CosmeticModel model;

    public Cosmetic(String name, String id, CosmeticModel model) {
        this.name = name;
        this.id = id;
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public CosmeticModel getModel() {
        return model;
    }
}