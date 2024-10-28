package org.teamvoided.grappled.util

import net.minecraft.util.math.Vec3d
import kotlin.math.sign

fun Vec3d.sign(): Vec3d = Vec3d(sign(x), sign(y), sign(z))