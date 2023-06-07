package simpleclient.item;

public enum EquipmentSlot {
    MAINHAND("mainhand"),
    OFFHAND("offhand"),
    FEET("feet"),
    LEGS("legs"),
    CHEST("chest"),
    HEAD("head");

    private String name;

    private EquipmentSlot(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}