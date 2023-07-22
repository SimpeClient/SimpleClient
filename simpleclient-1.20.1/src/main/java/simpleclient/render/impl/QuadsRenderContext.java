package simpleclient.render.impl;

import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.util.FastColor.ARGB32;
import simpleclient.render.RenderContext;

public class QuadsRenderContext extends RenderContext {

	public QuadsRenderContext(final BufferBuilder bufferBuilder) {
		super(bufferBuilder);
	}

	@Override
	public void begin() {
		super.begin();
		
		this.bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
	}
	
	public void rectangle(final PoseStack poseStack, float left, float top, float right, float bottom,
			final int color) {
		final Matrix4f matrices = poseStack.last().pose();

		final float alpha = ARGB32.alpha(color) / 255.F;
		final float red = ARGB32.red(color) / 255.F;
		final float green = ARGB32.green(color) / 255.F;
		final float blue = ARGB32.blue(color) / 255.F;

		this.bufferBuilder.vertex(matrices, left, top, RenderContext.ZERO_Z_LAYER).color(red, green, blue, alpha)
				.endVertex();
		this.bufferBuilder.vertex(matrices, left, bottom, RenderContext.ZERO_Z_LAYER).color(red, green, blue, alpha)
				.endVertex();
		this.bufferBuilder.vertex(matrices, right, bottom, RenderContext.ZERO_Z_LAYER).color(red, green, blue, alpha)
				.endVertex();
		this.bufferBuilder.vertex(matrices, right, top, RenderContext.ZERO_Z_LAYER).color(red, green, blue, alpha)
				.endVertex();
	}
}
