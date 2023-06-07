package simpleclient.adapter;

import simpleclient.item.ItemStack;

public abstract class ItemRendererAdapter {
    public static ItemRendererAdapter INSTANCE = null;

    public abstract void renderItem(ItemStack stack, int x, int y);
}