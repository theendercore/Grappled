package org.teamvoided.grappled.item

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.hit.HitResult
import net.minecraft.world.World
import org.teamvoided.grappled.entities.GrapplingHookHeadEntity

class GrapplingHookItem(settings: Settings) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)
        var msg = ""
        val result = user.raycast(20.0, 0f, false)
        val pos = result.pos
        when (result.type) {
            HitResult.Type.MISS -> {}
            HitResult.Type.BLOCK -> {
                val head = GrapplingHookHeadEntity(world, user)
                head.setPosition(pos.x, pos.y, pos.z)
                world.spawnEntity(head)
            }

            HitResult.Type.ENTITY -> {}
        }
        user.sendMessage(Text.of(msg), true)
        if (msg.isNotEmpty()) println(msg)
        return TypedActionResult.success(stack, world.isClient)

        return super.use(world, user, hand)
    }
}