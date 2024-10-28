package org.teamvoided.grappled

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import org.teamvoided.grappled.Grappled.log
import org.teamvoided.grappled.entities.renderer.GrapplingHookHeadEntityRenderer
import org.teamvoided.grappled.init.GapEntityTypes

@Suppress("unused")
object GrappledClient {
    fun init() {
        log.info("Hello from Client")
        EntityRendererRegistry.register(GapEntityTypes.GRAPPLING_HOOK_HEAD, ::GrapplingHookHeadEntityRenderer)
    }
}
