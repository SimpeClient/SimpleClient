package simpleclient.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import simpleclient.adapter.ItemRendererAdapter;
import simpleclient.adapter.ItemRendererAdapterImpl;
import simpleclient.adapter.TextRendererAdapter;
import simpleclient.adapter.TextRendererAdapterImpl;
import simpleclient.item.ItemStack;
import simpleclient.text.Text;

public class SimpleClientToast implements Toast {
    private ItemStack icon;
    private long duration;
    private Text text1;
    private Text text2;
    private boolean playedSound;

    public SimpleClientToast(ItemStack icon, Text text1, Text text2, long duration) {
        this.icon = icon;
        this.text1 = text1;
        this.text2 = text2;
        this.duration = duration;
    }

    public SimpleClientToast(ItemStack icon, Text text1, Text text2) {
        this(icon, text1, text2, 5000);
    }

    public SimpleClientToast(Text text1, Text text2, long duration) {
        this(null, text1, text2, duration);
    }

    public SimpleClientToast(Text text1, Text text2) {
        this(text1, text2, 5000);
    }

    @Override
    public Visibility render(PoseStack poseStack, ToastComponent toastComponent, long time) {
        TextRendererAdapter textRenderer = new TextRendererAdapterImpl(poseStack, toastComponent.getMinecraft().font);
        ItemRendererAdapter itemRenderer = new ItemRendererAdapterImpl(poseStack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        GuiComponent.blit(poseStack, 0, 0, 0, 0, width(), height());
        if (icon != null) itemRenderer.renderItem(icon, 8, 8);
        if (text1 != null) textRenderer.render(text1, icon == null ? 7 : 30, 7);
        if (text2 != null) textRenderer.render(text2, icon == null ? 7 : 30, 18);
        return time > duration ? Visibility.HIDE : Visibility.SHOW;
    }
}