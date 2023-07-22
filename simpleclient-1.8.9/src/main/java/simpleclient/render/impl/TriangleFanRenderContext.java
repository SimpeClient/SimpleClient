package simpleclient.render.impl;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import simpleclient.render.RenderContext;

public class TriangleFanRenderContext extends RenderContext {
	public TriangleFanRenderContext(final BufferBuilder bufferBuilder) {
		super(bufferBuilder);
	}

	@Override
	public void begin() {
		super.begin();
		this.bufferBuilder.begin(GL11.GL_TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
	}

	public void circle(final float x, final float y, final float radius,
			final float startAngle, final float endAngle, final float step, final int color) {
		final float alpha = ((color >> 24) & 0xFF) / 255.F;
		final float red = ((color >> 16) & 0xFF) / 255.F;
		final float green = ((color >> 8) & 0xFF) / 255.F;
		final float blue = (color & 0xFF) / 255.F;

		this.bufferBuilder.vertex(x, y, RenderContext.ZERO_Z_LAYER).color(red, green, blue, alpha).next();
		for (float i = startAngle; i <= endAngle; i += step) {
			this.bufferBuilder
					.vertex(x + (MathHelper.sin(i * (float) Math.PI / 180.F) * radius),
							y + (MathHelper.cos(i * (float) Math.PI / 180.F) * radius), RenderContext.ZERO_Z_LAYER)
					.color(red, green, blue, alpha).next();
		}
	}
}
