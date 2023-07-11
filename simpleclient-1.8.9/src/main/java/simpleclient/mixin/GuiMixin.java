package simpleclient.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
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
import simpleclient.text.Text;

@Mixin(InGameHud.class)
public abstract class GuiMixin {
    @Shadow public abstract TextRenderer getFontRenderer();
    @Shadow @Final private MinecraftClient client;

    @Inject(at = @At("TAIL"), method = "render")
    private void render(float partialTicks, CallbackInfo ci) {
        TextRendererAdapter textRenderer = new TextRendererAdapterImpl(getFontRenderer());
        ItemRendererAdapter itemRenderer = new ItemRendererAdapterImpl();
        // Watermark
        String watermark = "SimpleClient " + SimpleClient.VERSION;
        textRenderer.renderWithShadow(Text.literal(watermark), client.width - textRenderer.getWidth(watermark) - 1, 1, 0xFFAAAAAA);
        // Features
        if (!(client.currentScreen instanceof EditFeaturesScreen)) {
            int width = client.width / 2;
            int height = client.height / 2;
            for (Feature feature : FeatureManager.INSTANCE.getFeatures()) {
                if (feature instanceof RenderableFeature rf && rf.isEnabled()) {
                    rf.render(textRenderer, itemRenderer, width, height);
                }
            }
        }
    }
}
