package simpleclient.mixin.performance;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import simpleclient.feature.PerformanceBoost;
import simpleclient.math.VertexUtils;

@Mixin(ModelPart.Cube.class)
public class CubeMixin {
    @Shadow @Final private ModelPart.Polygon[] polygons;

    // +5 FPS with many Entities
    @Inject(at = @At("HEAD"), method = "compile", cancellable = true)
    public void compile(PoseStack.Pose pose, VertexConsumer vertexConsumer, int light, int overlay, float r, float g, float b, float a, CallbackInfo ci) {
        if (PerformanceBoost.INSTANCE.isEnabled()) ci.cancel();
        else return;
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        for (ModelPart.Polygon quad : polygons) {
            ModelPart.Vertex[] vertices = quad.vertices;
            if (VertexUtils.isTriangleInvisible(
                    VertexUtils.applyTransformation(vertices[0].pos, matrix4f),
                    VertexUtils.applyTransformation(vertices[1].pos, matrix4f),
                    VertexUtils.applyTransformation(vertices[2].pos, matrix4f)
            )) continue;
            Vector3f vector3f = matrix3f.transform(new Vector3f(quad.normal));
            float nx = vector3f.x(), ny = vector3f.y(), nz = vector3f.z();
            for (ModelPart.Vertex vertex : vertices) {
                Vector4f vector4f = matrix4f.transform(new Vector4f(
                        vertex.pos.x() * VertexUtils.FAST_NORMAL,
                        vertex.pos.y() * VertexUtils.FAST_NORMAL,
                        vertex.pos.z() * VertexUtils.FAST_NORMAL,
                        1.0F
                ));
                vertexConsumer.vertex(vector4f.x(), vector4f.y(), vector4f.z(), r, g, b, a, vertex.u, vertex.v, overlay, light, nx, ny, nz);
            }
        }
    }
}