package org.teamvoided.grappled.data.gen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.minecraft.registry.RegistrySetBuilder
import org.teamvoided.grappled.Grappled.log
import org.teamvoided.grappled.data.gen.providers.EnglishLangProvider
import org.teamvoided.grappled.data.gen.providers.ModelProvider

@Suppress("unused")
class GrappledData : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(gen: FabricDataGenerator) {
        log.info("Hello from DataGen")
        val pack = gen.createPack()

        pack.addProvider(::ModelProvider)
        pack.addProvider(::EnglishLangProvider)
    }

    override fun buildRegistry(gen: RegistrySetBuilder) {
//        gen.add(RegistryKeys.BIOME, TemplateBiomes::boostrap)
    }
}
