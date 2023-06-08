package simpleclient.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import simpleclient.SimpleClient;
import simpleclient.adapter.ItemRendererAdapter;
import simpleclient.adapter.ItemRendererAdapterImpl;
import simpleclient.adapter.TextRendererAdapter;
import simpleclient.adapter.TextRendererAdapterImpl;
import simpleclient.feature.Feature;
import simpleclient.feature.FeatureManager;
import simpleclient.feature.RenderableFeature;
import simpleclient.gui.EditFeaturesScreen;

@Mixin(Gui.class)
public abstract class InGameHudMixin {
    @Inject(at = @At("TAIL"), method = "render")
    private void renderHud(PoseStack poseStack, float tickDelta, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        TextRendererAdapter textRenderer = new TextRendererAdapterImpl(poseStack, mc.font);
        ItemRendererAdapter itemRenderer = new ItemRendererAdapterImpl(poseStack);
        // Watermark
        mc.font.drawShadow(poseStack, "SimpleClient " + SimpleClient.VERSION, mc.getWindow().getGuiScaledWidth() - mc.font.width("SimpleClient " + SimpleClient.VERSION) - 1, 1, 0xFFAAAAAA);
        // Features
        if (!(mc.screen instanceof EditFeaturesScreen)) {
            int width = mc.getWindow().getGuiScaledWidth();
            int height = mc.getWindow().getGuiScaledHeight();
            for (Feature feature : FeatureManager.INSTANCE.getFeatures()) {
                if (feature instanceof RenderableFeature rf && rf.isEnabled()) {
                    rf.render(textRenderer, itemRenderer, width, height);
                }
            }
        }
    }
}
