package org.teamvoided.grappled.entities

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.world.World
import org.teamvoided.grappled.init.GapEntityTypes
import org.teamvoided.grappled.util.sign
import java.util.*
import kotlin.jvm.optionals.getOrNull

class GrapplingHookHeadEntity(entityType: EntityType<GrapplingHookHeadEntity>, world: World) :
    Entity(entityType, world) {
    var ownerId: UUID? = null
    var serverOwner: LivingEntity? = null

    constructor(world: World, owner: LivingEntity) : this(GapEntityTypes.GRAPPLING_HOOK_HEAD, world) {
        this.serverOwner = owner
        this.ownerId = owner.uuid
        this.setOwner(owner)
    }

    constructor(world: World, x: Double, y: Double, z: Double) : this(GapEntityTypes.GRAPPLING_HOOK_HEAD, world) {
        setPosition(x, y, z)
    }

    override fun initDataTracker(builder: DataTracker.Builder) {
        builder.add(HOOK_DATA, Optional.ofNullable(ownerId))
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        if (nbt.containsUuid("Owner")) ownerId = nbt.getUuid("Owner")
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        if (ownerId != null) nbt.putUuid("Owner", ownerId)
    }

    fun setOwner(owner: LivingEntity) {
        dataTracker.set(HOOK_DATA, Optional.ofNullable(owner.uuid))
    }

    fun getOwner(): UUID? {
        return dataTracker.get(HOOK_DATA).getOrNull()
    }

    fun fetchOwner(): LivingEntity? {
        return if (serverOwner == null && ownerId != null && world is ServerWorld)
            (world as ServerWorld).getEntity(ownerId) as? LivingEntity
        else serverOwner
    }

    var printOnce = 0
    override fun tick() {
        super.tick()
        if (!world.isClient) {
            if (printOnce % 200 == 0) {
//                println("owner: $serverOwner")
            }
            printOnce++
            val owner = fetchOwner()
            if (owner != null) {
                owner.addVelocity(this.pos.subtract(owner.pos).sign().multiply(0.1))
                owner.velocityModified = true
            }
        }
    }

    override fun interact(player: PlayerEntity, hand: Hand): ActionResult {
        if(!player.world.isClient) {
            if (player.uuid == ownerId) return ActionResult.PASS
            this.discard()
        }
        return super.interact(player, hand)
    }

    /* fun World.addParticle(effect: ParticleEffect, pos: Vec3d) =
            this.addParticle(
                effect,
               pos.x,
                pos.y,
                pos.z,
                0.0, 0.0, 0.0
            )*/
//    data class GrapplingHookHeadEntityData(var owner: Entity? = null)
    companion object {
        val HOOK_DATA =
            DataTracker.registerData(GrapplingHookHeadEntity::class.java, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    }
}