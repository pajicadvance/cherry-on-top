package me.pajic.cherryontop.item;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.List;

public class CoTItems {

    // Enchantment upgrade smithing template
    public static final Item ENCHANTMENT_UPGRADE_SMITHING_TEMPLATE = new SmithingTemplateFoilItem(
            Component.translatable(Util.makeDescriptionId("item", ResourceLocation.parse("cherry-on-top.smithing_template.enchantment_upgrade.applies_to"))).withStyle(ChatFormatting.BLUE),
            Component.translatable(Util.makeDescriptionId("item", ResourceLocation.parse("cherry-on-top.smithing_template.enchantment_upgrade.ingredients"))).withStyle(ChatFormatting.BLUE),
            Component.translatable(Util.makeDescriptionId("upgrade", ResourceLocation.parse("cherry-on-top.enchantment_upgrade"))).withStyle(ChatFormatting.GRAY),
            Component.translatable(Util.makeDescriptionId("item", ResourceLocation.parse("cherry-on-top.smithing_template.enchantment_upgrade.base_slot_description"))),
            Component.translatable(Util.makeDescriptionId("item", ResourceLocation.parse("cherry-on-top.smithing_template.enchantment_upgrade.additions_slot_description"))),
            List.of(ResourceLocation.parse("cherry-on-top:item/empty_slot_enchanted_book")),
            List.of(ResourceLocation.parse("item/empty_slot_lapis_lazuli"))
    );
}
