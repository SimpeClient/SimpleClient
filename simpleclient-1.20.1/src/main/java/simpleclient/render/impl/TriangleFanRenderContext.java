package simpleclient.render.impl;

import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.util.FastColor.ARGB32;
import simpleclient.render.RenderContext;
import net.minecraft.util.Mth;

public class TriangleFanRenderContext extends RenderContext {

	public TriangleFanRenderContext(final BufferBuilder bufferBuilder) {
		super(bufferBuilder);
	}

	@Override
	public void begin() {
		super.begin();
		this.bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
	}

	public void circle(final PoseStack poseStack, final float x, final float y, final float radius,
			final float startAngle, final float endAngle, final float step, final int color) {
		final Matrix4f matrices = poseStack.last().pose();

		final float alpha = ARGB32.alpha(color) / 255.F;
		final float red = ARGB32.red(color) / 255.F;
		final float green = ARGB32.green(color) / 255.F;
		final float blue = ARGB32.blue(color) / 255.F;

		this.bufferBuilder.vertex(matrices, x, y, RenderContext.ZERO_Z_LAYER).color(red, green, blue, alpha)
				.endVertex();
		for (float i = startAngle; i <= endAngle; i += step) {
			this.bufferBuilder
					.vertex(matrices, x + (Mth.sin(i * (float) Math.PI / 180.F) * radius),
							y + (Mth.cos(i * (float) Math.PI / 180.F) * radius), RenderContext.ZERO_Z_LAYER)
					.color(red, green, blue, alpha).endVertex();
		}
	}
}
