package simpleclient.adapter;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import simpleclient.item.ItemStack;

public class ItemRendererAdapterImpl extends ItemRendererAdapter {
    private PoseStack poseStack;

    public ItemRendererAdapterImpl(PoseStack poseStack) {
        this.poseStack = poseStack;
    }

    @Override
    public void renderItem(ItemStack stack, int x, int y) {
        Minecraft.getInstance().getItemRenderer().renderAndDecorateFakeItem(poseStack, ItemAdapter.adapt(stack), x, y);
    }
}