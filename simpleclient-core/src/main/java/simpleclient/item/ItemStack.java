package simpleclient.item;

import simpleclient.text.Text;

public class ItemStack {
    private Item item;
    private int count;
    private int damage;
    private Text name;

    public ItemStack(Item item) {
        this(item, 1);
    }

    public ItemStack(Item item, int count) {
        this.item = item;
        this.count = count;
        this.damage = 0;
        this.name = null;
    }

    public Item getItem() {
        return item;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Text getName() {
        return name;
    }

    public void setName(Text name) {
        this.name = name;
    }
}