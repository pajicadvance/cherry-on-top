package me.pajic.cherryontop.mixin.early_loaders.stackable_items;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.cherryontop.config.EarlyLoader;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Items.class)
public class ItemsMixin {

    @Definition(id = "PotionItem", type = PotionItem.class)
    @Expression("new PotionItem(new ?().?(@(?)).?(?, ?))")
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private static int modifyPotionMaxStackSize(int original) {
        if (EarlyLoader.CONFIG.enableStackablePotions) {
            return EarlyLoader.CONFIG.potionMaxStackSize;
        }
        return original;
    }

    @Definition(id = "SplashPotionItem", type = SplashPotionItem.class)
    @Expression("new SplashPotionItem(new ?().?(@(?)).?(?, ?))")
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private static int modifySplashPotionMaxStackSize(int original) {
        if (EarlyLoader.CONFIG.enableStackablePotions) {
            return EarlyLoader.CONFIG.potionMaxStackSize;
        }
        return original;
    }

    @Definition(id = "LingeringPotionItem", type = LingeringPotionItem.class)
    @Expression("new LingeringPotionItem(new ?().?(@(?)).?(?, ?))")
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private static int modifyLingeringPotionMaxStackSize(int original) {
        if (EarlyLoader.CONFIG.enableStackablePotions) {
            return EarlyLoader.CONFIG.potionMaxStackSize;
        }
        return original;
    }

    @Definition(id = "EnchantedBookItem", type = EnchantedBookItem.class)
    @Expression("new EnchantedBookItem(new ?().?(@(?)).?(?).?(?, ?).?(?, ?))")
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private static int modifyEnchantedBookMaxStackSize(int original) {
        if (EarlyLoader.CONFIG.enableStackableEnchantedBooks) {
            return EarlyLoader.CONFIG.enchantedBookMaxStackSize;
        }
        return original;
    }

    @Definition(id = "AnimalArmorItem", type = AnimalArmorItem.class)
    @Expression("new AnimalArmorItem(?, ?, ?, new ?().?(@(?)))")
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private static int modifyHorseArmorMaxStackSize(int original) {
        if (EarlyLoader.CONFIG.enableStackableHorseArmor) {
            return EarlyLoader.CONFIG.horseArmorMaxStackSize;
        }
        return original;
    }

    @Definition(id = "RABBIT_STEW", field = "Lnet/minecraft/world/food/Foods;RABBIT_STEW:Lnet/minecraft/world/food/FoodProperties;")
    @Expression("new ?().?(@(?)).?(RABBIT_STEW)")
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private static int modifyRabbitStewMaxStackSize(int original) {
        if (EarlyLoader.CONFIG.enableStackableStews) {
            return EarlyLoader.CONFIG.stewMaxStackSize;
        }
        return original;
    }

    @Definition(id = "MUSHROOM_STEW", field = "Lnet/minecraft/world/food/Foods;MUSHROOM_STEW:Lnet/minecraft/world/food/FoodProperties;")
    @Expression("new ?().?(@(?)).?(MUSHROOM_STEW)")
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private static int modifyMushroomStewMaxStackSize(int original) {
        if (EarlyLoader.CONFIG.enableStackableStews) {
            return EarlyLoader.CONFIG.stewMaxStackSize;
        }
        return original;
    }

    @Definition(id = "BEETROOT_SOUP", field = "Lnet/minecraft/world/food/Foods;BEETROOT_SOUP:Lnet/minecraft/world/food/FoodProperties;")
    @Expression("new ?().?(@(?)).?(BEETROOT_SOUP)")
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private static int modifyBeetrootSoupMaxStackSize(int original) {
        if (EarlyLoader.CONFIG.enableStackableStews) {
            return EarlyLoader.CONFIG.stewMaxStackSize;
        }
        return original;
    }

    @Definition(id = "jukeboxPlayable", method = "Lnet/minecraft/world/item/Item$Properties;jukeboxPlayable(Lnet/minecraft/resources/ResourceKey;)Lnet/minecraft/world/item/Item$Properties;")
    @Expression("new ?().?(@(?)).?(?).jukeboxPlayable(?)")
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private static int modifyMusicDiscMaxStackSize(int original) {
        if (EarlyLoader.CONFIG.enableStackableMusicDiscs) {
            return EarlyLoader.CONFIG.musicDiscMaxStackSize;
        }
        return original;
    }

    @Definition(id = "SaddleItem", type = SaddleItem.class)
    @Expression("new SaddleItem(new ?().?(@(?)))")
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private static int modifySaddleMaxStackSize(int original) {
        if (EarlyLoader.CONFIG.enableStackableSaddles) {
            return EarlyLoader.CONFIG.saddleMaxStackSize;
        }
        return original;
    }

    @Definition(id = "MinecartItem", type = MinecartItem.class)
    @Expression("new MinecartItem(?, new ?().?(@(?)))")
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private static int modifyMinecartMaxStackSize(int original) {
        if (EarlyLoader.CONFIG.enableStackableMinecarts) {
            return EarlyLoader.CONFIG.minecartMaxStackSize;
        }
        return original;
    }

    @Definition(id = "BoatItem", type = BoatItem.class)
    @Expression("new BoatItem(?, ?, new ?().?(@(?)))")
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private static int modifyBoatMaxStackSize(int original) {
        if (EarlyLoader.CONFIG.enableStackableBoats) {
            return EarlyLoader.CONFIG.boatMaxStackSize;
        }
        return original;
    }

    @Definition(id = "CAKE", field = "Lnet/minecraft/world/level/block/Blocks;CAKE:Lnet/minecraft/world/level/block/Block;")
    @Expression("new ?(CAKE, new ?().?(@(?)))")
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private static int modifyCakeMaxStackSize(int original) {
        if (EarlyLoader.CONFIG.enableStackableCakes) {
            return EarlyLoader.CONFIG.cakeMaxStackSize;
        }
        return original;
    }

    @Definition(id = "BedItem", type = BedItem.class)
    @Expression("new BedItem(?, new ?().?(@(?)))")
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private static int modifyBedMaxStackSize(int original) {
        if (EarlyLoader.CONFIG.enableStackableBeds) {
            return EarlyLoader.CONFIG.bedMaxStackSize;
        }
        return original;
    }
}
