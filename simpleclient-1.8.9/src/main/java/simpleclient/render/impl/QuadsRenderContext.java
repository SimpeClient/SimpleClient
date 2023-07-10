package simpleclient.render.impl;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormats;
import org.lwjgl.opengl.GL11;
import simpleclient.render.RenderContext;

public class QuadsRenderContext extends RenderContext {

	public QuadsRenderContext(final BufferBuilder bufferBuilder) {
		super(bufferBuilder);
	}

	@Override
	public void begin() {
		super.begin();
		this.bufferBuilder.begin(GL11.GL_QUADS, VertexFormats.POSITION_COLOR);
	}
	
	public void rectangle(float left, float top, float right, float bottom,
			final int color) {
		final float alpha = ((color >> 24) & 0xFF) / 255.F;
		final float red = ((color >> 16) & 0xFF) / 255.F;
		final float green = ((color >> 8) & 0xFF) / 255.F;
		final float blue = (color & 0xFF) / 255.F;

		this.bufferBuilder.vertex(left, top, RenderContext.ZERO_Z_LAYER).color(red, green, blue, alpha).next();
		this.bufferBuilder.vertex(left, bottom, RenderContext.ZERO_Z_LAYER).color(red, green, blue, alpha).next();
		this.bufferBuilder.vertex(right, bottom, RenderContext.ZERO_Z_LAYER).color(red, green, blue, alpha).next();
		this.bufferBuilder.vertex(right, top, RenderContext.ZERO_Z_LAYER).color(red, green, blue, alpha).next();
	}
}
