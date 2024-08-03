package me.pajic.cherryontop.item;

import me.emafire003.dev.custombrewrecipes.CustomBrewRecipeRegister;
import me.pajic.cherryontop.Main;
import me.pajic.cherryontop.compat.CoTTrinketsCompat;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.List;

public class CoTItems {

    public static final Item ENCHANTMENT_UPGRADE_SMITHING_TEMPLATE = new SmithingTemplateFoilItem(
            Component.translatable(Util.makeDescriptionId(
                    "item",
                    ResourceLocation.parse("cherry-on-top.smithing_template.enchantment_upgrade.applies_to")
            )).withStyle(ChatFormatting.BLUE),
            Component.translatable(Util.makeDescriptionId(
                    "item",
                    ResourceLocation.parse("cherry-on-top.smithing_template.enchantment_upgrade.ingredients")
            )).withStyle(ChatFormatting.BLUE),
            Component.translatable(Util.makeDescriptionId(
                    "upgrade",
                    ResourceLocation.parse("cherry-on-top.enchantment_upgrade")
            )).withStyle(ChatFormatting.GRAY),
            Component.translatable(Util.makeDescriptionId(
                    "item",
                    ResourceLocation.parse("cherry-on-top.smithing_template.enchantment_upgrade.base_slot_description")
            )),
            Component.translatable(Util.makeDescriptionId(
                    "item",
                    ResourceLocation.parse("cherry-on-top.smithing_template.enchantment_upgrade.additions_slot_description")
            )),
            List.of(ResourceLocation.parse("cherry-on-top:item/empty_slot_enchanted_book")),
            List.of(ResourceLocation.parse("item/empty_slot_lapis_lazuli"))
    );

    public static final Item WHETSTONE = new WhetstoneItem(
            new Item.Properties()
                    .durability(12)
                    .component(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY)
    );

    public static final Item POTION_OF_TELEPORTATION = new PotionOfTeleportationItem(
            new Item.Properties().stacksTo(1),
            Component.translatable("item.cherry-on-top.potion_of_teleportation.tooltip").withStyle(ChatFormatting.BLUE),
            Main.CONFIG.teleportationPotions.enablePotionOfTeleportation()
    );

    public static final Item POTION_OF_WORMHOLE = new PotionOfWormholeItem(
            new Item.Properties().stacksTo(1),
            Component.translatable("item.cherry-on-top.potion_of_wormhole.tooltip").withStyle(ChatFormatting.BLUE),
            Main.CONFIG.teleportationPotions.enablePotionOfWormhole()
    );

    public static void initItems() {
        Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.parse("cherry-on-top:enchantment_upgrade"),
                ENCHANTMENT_UPGRADE_SMITHING_TEMPLATE
        );
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(contents -> contents.addAfter(
                Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                ENCHANTMENT_UPGRADE_SMITHING_TEMPLATE)
        );

        Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.parse("cherry-on-top:whetstone"),
                WHETSTONE
        );
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(contents -> contents.addAfter(
                Items.NETHERITE_HOE,
                WHETSTONE
        ));

        if (FabricLoader.getInstance().isModLoaded("trinkets")) {
            CoTTrinketsCompat.register();
        }
        else {
            EnderBackpackItem item = new EnderBackpackItem(new Item.Properties().stacksTo(1));
            Registry.register(
                    BuiltInRegistries.ITEM,
                    ResourceLocation.parse("cherry-on-top:ender_backpack"),
                    item
            );
            ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(contents -> contents.addAfter(
                    Items.LEAD,
                    item
            ));
        }

        Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.parse("cherry-on-top:potion_of_teleportation"),
                POTION_OF_TELEPORTATION
        );
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(contents -> contents.addAfter(
                Items.POTION,
                POTION_OF_TELEPORTATION
        ));
        CustomBrewRecipeRegister.registerCustomRecipeWithComponents(
                Items.POTION,
                Items.ENDER_PEARL,
                POTION_OF_TELEPORTATION,
                DataComponentMap.builder().set(
                        DataComponents.POTION_CONTENTS,
                        new PotionContents(Potions.AWKWARD)
                ).build(),
                null,
                null
        );

        Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.parse("cherry-on-top:potion_of_wormhole"),
                POTION_OF_WORMHOLE
        );
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(contents -> contents.addAfter(
                POTION_OF_TELEPORTATION,
                POTION_OF_WORMHOLE
        ));
        CustomBrewRecipeRegister.registerCustomRecipeWithComponents(
                Items.POTION,
                Items.ENDER_EYE,
                POTION_OF_WORMHOLE,
                DataComponentMap.builder().set(
                        DataComponents.POTION_CONTENTS,
                        new PotionContents(Potions.AWKWARD)
                ).build(),
                null,
                null
        );
    }
}
