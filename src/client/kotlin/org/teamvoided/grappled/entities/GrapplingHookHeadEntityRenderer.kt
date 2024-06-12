package org.teamvoided.grappled.entities

import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.model.json.ModelTransformationMode
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.Items
import net.minecraft.util.Identifier

class GrapplingHookHeadEntityRenderer(ctx: EntityRendererFactory.Context) :
    EntityRenderer<GrapplingHookHeadEntity>(ctx) {


    override fun render(
        entity: GrapplingHookHeadEntity,
        yaw: Float, tickDelta: Float, matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider, light: Int
    ) {
        MinecraftClient.getInstance()
            .itemRenderer.renderItem(
                Items.BLAZE_ROD.defaultStack,
                ModelTransformationMode.GROUND, light, OverlayTexture.DEFAULT_UV,
                matrices, vertexConsumers, entity.world, 0
            )
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light)
    }

    override fun getTexture(entity: GrapplingHookHeadEntity): Identifier? = null
}