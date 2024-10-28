package org.teamvoided.grappled.entities.renderer

import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.render.Frustum
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.model.json.ModelTransformationMode
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.Entity
import net.minecraft.item.Items
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.math.Axis
import net.minecraft.util.math.MathHelper.lerp
import net.minecraft.util.math.Vec3d
import org.teamvoided.grappled.entities.GrapplingHookHeadEntity
import org.teamvoided.grappled.util.sign
import kotlin.math.acos
import kotlin.math.atan2


class GrapplingHookHeadEntityRenderer(ctx: EntityRendererFactory.Context) :
    EntityRenderer<GrapplingHookHeadEntity>(ctx) {


    override fun render(
        entity: GrapplingHookHeadEntity,
        yaw: Float,
        tDelta: Float,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        entityLight: Int
    ) {
        super.render(entity, yaw, tDelta, matrices, vertexConsumers, 0xffffff)
        val world = entity.world
        val owner = (world as ClientWorld).entities.firstOrNull { it.uuid == entity.getOwner() }

        MinecraftClient.getInstance().itemRenderer.renderItem(
            Items.BREEZE_ROD.defaultStack, ModelTransformationMode.GROUND,
            entityLight, OverlayTexture.DEFAULT_UV,
            matrices, vertexConsumers, entity.world, 0
        )
        var msg = ""
        if (owner != null) {
//            msg += "$entityLight | $entityLight"
            msg += "pos: ${entity.pos.subtract(owner.pos).sign().multiply(0.1)}"

            matrices.push()
            matrices.translate(0.21, 0.35, 0.015)
            val hookPos = fromLerpedPosition(entity, 1.0, tDelta.toDouble())
            val ownerPos = Vec3d(
                lerp(tDelta.toDouble(), owner.lastRenderX, owner.x),
                lerp(tDelta.toDouble(), owner.lastRenderY, owner.y) + 1.6,
                lerp(tDelta.toDouble(), owner.lastRenderZ, owner.z)
            )

            fromLerpedPosition(owner, 1.6, tDelta.toDouble())

            val subtracted = ownerPos.subtract(hookPos)
            val length = subtracted.length().toFloat()
            val normalized = subtracted.normalize()

            val yAngle = ((1.5707964F - atan2(normalized.z, normalized.x).toFloat()) * 57.295776F) + 180f
//            msg += " | yAngle: $yAngle"

            matrices.rotate(Axis.Y_POSITIVE.rotationDegrees(yAngle))
            val xAngle = acos(normalized.y).toFloat() * -57.295776F
            matrices.rotate(Axis.X_POSITIVE.rotationDegrees(xAngle))
            msg += " | yAngle: $xAngle"

            val vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(CHAIN_TEXTURE))
            val entry = matrices.peek()

            vertexConsumer.vertex(entry, 0.093f, 0.0f, 0f, entityLight, 0.0f, 0f)
            vertexConsumer.vertex(entry, 0.093f, length, 0f, entityLight, 0.0f, length)
            vertexConsumer.vertex(entry, -0.093f, length, 0f, entityLight, 0.187f, length)
            vertexConsumer.vertex(entry, -0.093f, 0.0f, 0f, entityLight, 0.187f, 0f)

            vertexConsumer.vertex(entry, 0.0f, 0.0f, -0.093f, entityLight, 0.187f, 0f)
            vertexConsumer.vertex(entry, 0.0f, length, -0.093f, entityLight, 0.187f, length)
            vertexConsumer.vertex(entry, 0f, length, 0.093f, entityLight, 0.375f, length)
            vertexConsumer.vertex(entry, 0f, 0.0f, 0.093f, entityLight, 0.375f, 0f)
            matrices.pop()

            if (owner is ClientPlayerEntity) owner.sendMessage(Text.of("$msg | angle: $yAngle"), true)
        }
    }


    fun fromLerpedPosition(entity: Entity, yOffset: Double, delta: Double): Vec3d {
        return Vec3d(
            lerp(delta, entity.lastRenderX, entity.x),
            lerp(delta, entity.lastRenderY, entity.y) + yOffset,
            lerp(delta, entity.lastRenderZ, entity.z)
        )
    }

    private fun VertexConsumer.vertex(
        entry: MatrixStack.Entry, x: Float, y: Float, z: Float, light: Int, u: Float, v: Float
    ) {
        this.xyz(entry, x, y, z)
            .color(255, 255, 255, 255)
            .uv0(u, v)
            .uv1(OverlayTexture.DEFAULT_UV)
            .uv2(light)
            .normal(entry, 1.0f, 1.0f, 1.0f)

    }

    override fun getTexture(entity: GrapplingHookHeadEntity): Identifier? = null
    override fun shouldRender(e: GrapplingHookHeadEntity, f: Frustum, x: Double, y: Double, z: Double): Boolean = true

    companion object {
        val CHAIN_TEXTURE: Identifier = Identifier.ofDefault("textures/block/chain.png")
    }
}
