package org.teamvoided.grappled.item

import java.util.*

data class HookComponent(val maxHooks: Int, private val activeHooks: Queue<UUID>) {
    fun add(hook: UUID): Boolean {
        if (activeHooks.size >= maxHooks) return false
        if (activeHooks.contains(hook)) return false
        activeHooks.add(hook)
        return true
    }

    fun remove(hook: UUID): Boolean = activeHooks.remove(hook)
    fun remove(): UUID = activeHooks.poll()
    fun hooks() = activeHooks.toSet()
    fun canMakeNewHook(): Boolean = activeHooks.size < maxHooks

}
