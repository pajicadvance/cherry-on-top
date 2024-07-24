package me.pajic.cherryontop.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.List;

public class CoTItems {

    public static final Item ENCHANTMENT_UPGRADE_SMITHING_TEMPLATE = new SmithingTemplateFoilItem(
            Component.translatable(Util.makeDescriptionId("item", ResourceLocation.parse("cherry-on-top.smithing_template.enchantment_upgrade.applies_to"))).withStyle(ChatFormatting.BLUE),
            Component.translatable(Util.makeDescriptionId("item", ResourceLocation.parse("cherry-on-top.smithing_template.enchantment_upgrade.ingredients"))).withStyle(ChatFormatting.BLUE),
            Component.translatable(Util.makeDescriptionId("upgrade", ResourceLocation.parse("cherry-on-top.enchantment_upgrade"))).withStyle(ChatFormatting.GRAY),
            Component.translatable(Util.makeDescriptionId("item", ResourceLocation.parse("cherry-on-top.smithing_template.enchantment_upgrade.base_slot_description"))),
            Component.translatable(Util.makeDescriptionId("item", ResourceLocation.parse("cherry-on-top.smithing_template.enchantment_upgrade.additions_slot_description"))),
            List.of(ResourceLocation.parse("cherry-on-top:item/empty_slot_enchanted_book")),
            List.of(ResourceLocation.parse("item/empty_slot_lapis_lazuli"))
    );

    public static final Item WHETSTONE = new WhetstoneItem(new Item.Properties().durability(6));

    public static void initModItems() {
        Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.parse("cherry-on-top:enchantment_upgrade"),
                CoTItems.ENCHANTMENT_UPGRADE_SMITHING_TEMPLATE
        );
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(contents -> contents.addAfter(
                Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                CoTItems.ENCHANTMENT_UPGRADE_SMITHING_TEMPLATE)
        );

        Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.parse("cherry-on-top:whetstone"),
                CoTItems.WHETSTONE
        );
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(contents -> contents.addAfter(
                Items.NETHERITE_HOE,
                CoTItems.WHETSTONE)
        );
    }
}
