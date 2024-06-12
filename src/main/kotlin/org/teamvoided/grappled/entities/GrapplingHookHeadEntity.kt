package org.teamvoided.grappled.entities

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.data.DataTracker
import net.minecraft.nbt.NbtCompound
import net.minecraft.particle.ParticleTypes
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.MathHelper.lerp
import net.minecraft.world.World
import org.teamvoided.grappled.init.GapEntityTypes
import java.util.UUID

class GrapplingHookHeadEntity(entityType: EntityType<GrapplingHookHeadEntity>, world: World) :
    Entity(entityType, world) {
    var ownerId: UUID? = null
    var owner: LivingEntity? = null

    constructor(world: World, owner: LivingEntity) : this(GapEntityTypes.GRAPPLING_HOOK_HEAD, world) {
        this.ownerId = owner.uuid
        this.owner = owner
    }

    constructor(world: World, x: Double, y: Double, z: Double) : this(GapEntityTypes.GRAPPLING_HOOK_HEAD, world) {
        setPosition(x, y, z)
    }

    override fun initDataTracker(builder: DataTracker.Builder) {
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        if (nbt.containsUuid("Owner")) ownerId = nbt.getUuid("Owner")
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        if (ownerId != null) nbt.putUuid("Owner", ownerId)
    }

    fun fetchOwner(): LivingEntity? {
        return if (owner == null && ownerId != null && world is ServerWorld)
            (world as ServerWorld).getEntity(ownerId) as? LivingEntity
        else owner
    }

    override fun tick() {
        super.tick()

        if (world.isClient) {
            val owner = fetchOwner()
            if (owner != null) {
                for (i in 0..35) {
                    world.addParticle(
                        ParticleTypes.ELDER_GUARDIAN,
                        lerp(i / 100.0, pos.x, owner.x),
                        lerp(i / 100.0, pos.y, owner.y + 1),
                        lerp(i / 100.0, pos.z, owner.z),
                        0.0, 0.0, 0.0
                    )
                }
            }
        }
    }
//    data class GrapplingHookHeadEntityData(var owner: Entity? = null)
}