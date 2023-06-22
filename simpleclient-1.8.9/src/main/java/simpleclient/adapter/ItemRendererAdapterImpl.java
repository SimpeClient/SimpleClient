package simpleclient.adapter;

import net.minecraft.client.MinecraftClient;
import simpleclient.item.ItemStack;

public class ItemRendererAdapterImpl extends ItemRendererAdapter {
    @Override
    public void renderItem(ItemStack stack, int x, int y) {
        MinecraftClient.getInstance().getItemRenderer().renderGuiItemOverlay(MinecraftClient.getInstance().textRenderer, ItemAdapter.adapt(stack), x, y);
    }
}