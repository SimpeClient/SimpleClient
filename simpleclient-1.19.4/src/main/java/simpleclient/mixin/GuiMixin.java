package simpleclient.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Final;
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
public abstract class GuiMixin {
    @Shadow @Final private Minecraft minecraft;

    @Shadow public abstract Font getFont();

    @Inject(at = @At("TAIL"), method = "render")
    private void render(PoseStack poseStack, float tickDelta, CallbackInfo ci) {
        TextRendererAdapter textRenderer = new TextRendererAdapterImpl(poseStack, getFont());
        ItemRendererAdapter itemRenderer = new ItemRendererAdapterImpl(poseStack);
        // Watermark
        getFont().drawShadow(poseStack, "SimpleClient " + SimpleClient.VERSION, minecraft.getWindow().getGuiScaledWidth() - getFont().width("SimpleClient " + SimpleClient.VERSION) - 1, 1, 0xFFAAAAAA);
        // Features
        if (!(minecraft.screen instanceof EditFeaturesScreen)) {
            int width = minecraft.getWindow().getGuiScaledWidth();
            int height = minecraft.getWindow().getGuiScaledHeight();
            for (Feature feature : FeatureManager.INSTANCE.getFeatures()) {
                if (feature instanceof RenderableFeature rf && rf.isEnabled()) {
                    rf.render(textRenderer, itemRenderer, width, height);
                }
            }
        }
    }
}
