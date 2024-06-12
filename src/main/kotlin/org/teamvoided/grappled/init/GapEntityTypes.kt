package org.teamvoided.grappled.init

import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import org.teamvoided.grappled.Grappled.id
import org.teamvoided.grappled.entities.GrapplingHookHeadEntity

object GapEntityTypes {
    val GRAPPLING_HOOK_HEAD = register(
        "grappling_hook_head",
        EntityType.Builder.create(::GrapplingHookHeadEntity, SpawnGroup.MISC)
            .setDimensions(0.25f, 0.25f)
            .build()
    )


    fun <E, T : EntityType<E>> register(id: String, entityType: T): T {
        return Registry.register(Registries.ENTITY_TYPE, id(id), entityType)
    }
}