package simpleclient.cosmetics.builtins;

import simpleclient.cosmetics.Cosmetic;
import simpleclient.cosmetics.model.CosmeticModel;
import simpleclient.cosmetics.model.Cube;

public class HatCosmetic extends Cosmetic {
    public HatCosmetic() {
        super("Cylinder Hat", "cylinder_hat", new CosmeticModel());
    }

    public class HatModel extends CosmeticModel {
        public HatModel() {
            getCubes().add(new Cube("cube1", "%head%").size(4, 4, 10));
        }
    }
}