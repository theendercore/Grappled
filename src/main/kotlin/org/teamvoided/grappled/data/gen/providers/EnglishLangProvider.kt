package org.teamvoided.grappled.data.gen.providers

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.item.Item
import net.minecraft.registry.HolderLookup
import net.minecraft.registry.Registries
import org.teamvoided.grappled.init.GapEntityTypes
import org.teamvoided.grappled.init.GapItems.ALL_ITEMS
import java.util.concurrent.CompletableFuture

class EnglishLangProvider(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>) :
    FabricLanguageProvider(o, r) {
    override fun generateTranslations(reg: HolderLookup.Provider, gen: TranslationBuilder) {
        ALL_ITEMS.forEach { gen.add(it, getId(it).titleCase()) }
        gen.add(GapEntityTypes.GRAPPLING_HOOK_HEAD, "Grappling Hook Head")

    }

    fun String.titleCase(delimiter: String = "_"): String {
        return split(delimiter).joinToString(" ") { word ->
            word.replaceFirstChar(Char::titlecase)
        }
    }

    fun getId(item: Item): String = Registries.ITEM.getId(item).path
}