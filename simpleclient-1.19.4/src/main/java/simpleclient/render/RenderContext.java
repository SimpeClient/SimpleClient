package simpleclient.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.renderer.GameRenderer;
import simpleclient.render.impl.QuadsRenderContext;
import simpleclient.render.impl.TriangleFanRenderContext;

import java.util.function.Consumer;

public abstract class RenderContext {

	public static final float ZERO_Z_LAYER = 0.f;

	protected final BufferBuilder bufferBuilder;

	public RenderContext(final BufferBuilder bufferBuilder) {
		this.bufferBuilder = bufferBuilder;
	}
	
	public void begin() {
		RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
	}
	
	public void end() {
		BufferUploader.drawWithShader(this.bufferBuilder.end());
	}

	public static void triangleFan(final Consumer<TriangleFanRenderContext> context) {
		final TriangleFanRenderContext triangleFanRenderContext = new TriangleFanRenderContext(
				Tesselator.getInstance().getBuilder());
		triangleFanRenderContext.begin();
		context.accept(triangleFanRenderContext);
		triangleFanRenderContext.end();
	}
	
	public static void quads(final Consumer<QuadsRenderContext> context) {
		final QuadsRenderContext quadsRenderContext = new QuadsRenderContext(
				Tesselator.getInstance().getBuilder());
		quadsRenderContext.begin();
		context.accept(quadsRenderContext);
		quadsRenderContext.end();
	}
}
