package simpleclient.render;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import org.lwjgl.opengl.GL11;
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
		GlStateManager.enableBlend();
		GlStateManager.disableTexture();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public void end() {
		GlStateManager.enableTexture();
		GlStateManager.disableBlend();
		this.bufferBuilder.end();
	}

	public static void triangleFan(final Consumer<TriangleFanRenderContext> context) {
		final TriangleFanRenderContext triangleFanRenderContext = new TriangleFanRenderContext(
				Tessellator.getInstance().getBuffer());
		triangleFanRenderContext.begin();
		context.accept(triangleFanRenderContext);
		triangleFanRenderContext.end();
	}
	
	public static void quads(final Consumer<QuadsRenderContext> context) {
		final QuadsRenderContext quadsRenderContext = new QuadsRenderContext(
				Tessellator.getInstance().getBuffer());
		quadsRenderContext.begin();
		context.accept(quadsRenderContext);
		quadsRenderContext.end();
	}
}
