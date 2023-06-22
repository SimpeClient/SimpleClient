package simpleclient.adapter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import simpleclient.item.ItemStack;

public class ItemRendererAdapterImpl extends ItemRendererAdapter {
    private final GuiGraphics guiGraphics;

    public ItemRendererAdapterImpl(GuiGraphics guiGraphics) {
        this.guiGraphics = guiGraphics;
    }

    @Override
    public void renderItem(ItemStack stack, int x, int y) {
        guiGraphics.renderFakeItem(ItemAdapter.adapt(stack), x, y);
    }
}