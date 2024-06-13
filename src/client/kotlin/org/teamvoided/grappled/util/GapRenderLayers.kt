package org.teamvoided.grappled.util

import com.mojang.blaze3d.vertex.VertexFormat
import com.mojang.blaze3d.vertex.VertexFormats
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.RenderLayer.MultiPhaseParameters
import net.minecraft.client.render.RenderPhase
import net.minecraft.util.Identifier
import net.minecraft.util.Util
import java.util.function.Function

object GapRenderLayers {
    private val GRAPPLING_HOOK_LAYER: Function<Identifier, RenderLayer.MultiPhase> =
        Util.memoize { texture ->
            RenderLayer.of(
                "grappling_hook_layer",
                VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
                VertexFormat.DrawMode.QUADS,
                1536,
                true,
                false,
                MultiPhaseParameters.builder()
                    .shader(RenderPhase.BEACON_BEAM_SHADER) //make custom shader for this also copy `RenderPhase.ENTITY_CUTOUT_NONNULL_SHADER`
                    .texture(RenderPhase.Texture(texture, false, false))
                    .transparency(RenderPhase.NO_TRANSPARENCY)
                    .cull(RenderPhase.DISABLE_CULLING)
                    .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                    .overlay(RenderPhase.ENABLE_OVERLAY_COLOR)
                    .build(true)
            )
        }

    fun getGrapplingHookLayer(texture: Identifier) = GRAPPLING_HOOK_LAYER.apply(texture)
}
