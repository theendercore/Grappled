package org.teamvoided.grappled.init

import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import org.teamvoided.grappled.item.GrapplingHookItem

object GapItems {
    val ALL_ITEMS = mutableListOf<Item>()

    fun init() {}
    val COPPER_GRAPPLING_HOOK = register("copper_grappling_hook", GrapplingHookItem(Item.Settings()))

    fun <T : Item> register(name: String, item: T): T {
        val item = Registry.register(Registries.ITEM, name, item)
        ALL_ITEMS.add(item)
        return item
    }

}