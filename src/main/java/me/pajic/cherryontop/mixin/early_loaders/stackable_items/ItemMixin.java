package me.pajic.cherryontop.mixin.early_loaders.stackable_items;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import me.pajic.cherryontop.config.EarlyLoader;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Item.class)
public class ItemMixin {

    @ModifyReceiver(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/Item$Properties;buildAndValidateComponents()Lnet/minecraft/core/component/DataComponentMap;"
            )
    )
    private Item.Properties modifyMaxStackSize(Item.Properties properties) {
        Item item = (Item) (Object) this;
        if (EarlyLoader.CONFIG.enableStackablePotions && item instanceof PotionItem) {
            properties.stacksTo(EarlyLoader.CONFIG.potionMaxStackSize);
        }
        if (EarlyLoader.CONFIG.enableStackableEnchantedBooks && item instanceof EnchantedBookItem) {
            properties.stacksTo(EarlyLoader.CONFIG.enchantedBookMaxStackSize);
        }
        if (EarlyLoader.CONFIG.enableStackableSaddles && item instanceof SaddleItem) {
            properties.stacksTo(EarlyLoader.CONFIG.saddleMaxStackSize);
        }
        if (EarlyLoader.CONFIG.enableStackableMinecarts && item instanceof MinecartItem) {
            properties.stacksTo(EarlyLoader.CONFIG.minecartMaxStackSize);
        }
        if (EarlyLoader.CONFIG.enableStackableBoats && item instanceof BoatItem) {
            properties.stacksTo(EarlyLoader.CONFIG.boatMaxStackSize);
        }
        if (EarlyLoader.CONFIG.enableStackableBeds && item instanceof BedItem) {
            properties.stacksTo(EarlyLoader.CONFIG.bedMaxStackSize);
        }
        if (EarlyLoader.CONFIG.enableStackableSnowballs && item instanceof SnowballItem) {
            properties.stacksTo(EarlyLoader.CONFIG.snowballMaxStackSize);
        }
        if (EarlyLoader.CONFIG.enableStackableEggs && item instanceof EggItem) {
            properties.stacksTo(EarlyLoader.CONFIG.eggMaxStackSize);
        }
        if (EarlyLoader.CONFIG.enableStackableMusicDiscs) {
            DataComponentMap components = properties.buildAndValidateComponents();
            if (components.has(DataComponents.JUKEBOX_PLAYABLE)) {
                properties.stacksTo(EarlyLoader.CONFIG.musicDiscMaxStackSize);
            }
        }
        if (EarlyLoader.CONFIG.enableStackableHorseArmor && item instanceof AnimalArmorItem) {
            DataComponentMap components = properties.buildAndValidateComponents();
            if (!components.has(DataComponents.MAX_DAMAGE)) {
                properties.stacksTo(EarlyLoader.CONFIG.horseArmorMaxStackSize);
            }
        }
        return properties;
    }
}
