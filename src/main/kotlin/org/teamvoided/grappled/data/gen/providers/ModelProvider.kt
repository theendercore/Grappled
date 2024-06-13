package org.teamvoided.grappled.data.gen.providers

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.model.BlockStateModelGenerator
import net.minecraft.data.client.model.Models
import net.minecraft.item.Items
import org.teamvoided.grappled.init.GapItems

class ModelProvider(o: FabricDataOutput) : FabricModelProvider(o) {
    override fun generateBlockStateModels(gen: BlockStateModelGenerator) {
    }

    override fun generateItemModels(gen: ItemModelGenerator) {
        gen.register(GapItems.COPPER_GRAPPLING_HOOK, Items.BREEZE_ROD, Models.SINGLE_LAYER_ITEM)
    }
}