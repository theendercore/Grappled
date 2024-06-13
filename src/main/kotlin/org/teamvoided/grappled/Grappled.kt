package org.teamvoided.grappled

import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.teamvoided.grappled.init.GapItems

@Suppress("unused")
object Grappled {
    const val MODID = "grappled"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(Grappled::class.simpleName)

    fun init() {
        log.info("Hello from Common")
        GapItems.init()
    }

    fun id(path: String) = Identifier.of(MODID, path)
}
