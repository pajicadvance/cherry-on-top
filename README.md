# Cherry On Top

A collection of features and tweaks for Minecraft 1.21 Fabric.

## Features

### Enchantment Upgrading

Adds a new smithing template found in End Cities by default that can upgrade a single enchantment stored in a book by one. The selected enchantment depends on the amount of lapis added to the recipe. By default, upgrading costs experience based on the upgraded book's repair cost. The template can be duplicated once found.

Configuration options:
- Add or remove locations where the smithing template can be found and define the loot chance and the amount of templates, in the format of `location;chance;count`
- Add an XP cost to upgrading books, based on the repair cost of the book being upgraded, and set an optional base XP cost added on top of the repair cost
- Remove the XP cost entirely
- Ignore "Too Expensive!" (XP cost limit) when upgrading
- Only allow upgrading books which contain one enchantment only

### Portable Item Repair

Adds Whetstones, a new item crafted with quartz that can be used to repair items on the go.
- Combine a Whetstone, an item to repair, and the repair material in the crafting grid to repair the item. The Whetstone loses durability after the repair
- Regular Whetstones only repair unenchanted items
- Enchantments can be added to the Whetstone at an anvil. An enchanted Whetstone can only repair items whose enchantments match the enchantments on the Whetstone
- Whetstones can be repaired at an anvil

The whetstone repair recipe will show up in [EMI](https://modrinth.com/mod/emi) if it's installed.

### Ender Backpack

Adds the Ender Backpack, a new item that functions like a portable ender chest, crafted by surrounding an ender chest with leather, and can be opened by either right-clicking while holding it, or pressing the hotkey (default: B) while it's anywhere in the inventory.

If [Trinkets](https://modrinth.com/mod/trinkets) is installed, the Ender Backpack will be equippable in the Back slot, and can still be opened with the hotkey.

### Teleportation Potions

Adds two new potions inspired by Terraria.

Potion of Wormhole
- Teleports you to other players
- Brewed with an awkward potion and ender eye
- Drinking opens up a screen where you can select a player to teleport to

Potion of Teleportation
- Teleports you to a random location in a configurable radius
- Brewed with an awkward potion and ender pearl
- Configurable max teleport height

Recipes for these potions will show up in [EMI](https://modrinth.com/mod/emi) if it's installed.

### Compass and Clock Overlays

Shows useful information in the top left when carrying a compass or a clock.

Compass info:
- Coordinates
- Direction
- Biome

Clock info:
- Day and time
- Weather
- Season (if [Serene Seasons](https://modrinth.com/mod/serene-seasons) is installed)

Configuration options:
- Enable or disable each info overlay
- Color the weather line according to the current weather conditions
- Color the season line according to the current season

### Phantom Spawning Rework

Replaces vanilla phantom spawning rules with spawning rules based on altitude. 

Instead of having to not sleep for 3 in-game days in order to trigger phantom spawning at night, phantoms now start spawning at night above Y level of 128 by default, with the spawn chance increasing the higher the player is above the defined Y level, regardless of whether the player has slept recently or not.

Additionally, phantoms can be repelled by holding a repellent item (phantom membrane by default), either in the main hand or offhand.

Configuration options:
- Change the starting height for phantom spawning
- Modify the frequency at which the spawn check will run, in seconds. `rand(x)` can be used in the expression to pick a random number from 1 to x. The vanilla frequency is `59 + rand(60)`
- Enable or disable phantom repelling
- Add or remove items to be used as phantom repellent items

### Anvil Improvements

Changes the amount of materials required to repair items in the anvil from a fixed amount of 4 to an amount based on the amount of material units required to craft the item.

For example, to fully repair an iron chestplate, you now need 8 iron ingots up from 4, and to fully repair an iron sword, you need 2 iron ingots down from 4.

Additionally:
- Change the chance that the anvil will be damaged on use
- Remove the XP cost for repairing unenchanted items
- Remove the XP cost for renaming items
- Remove the combine XP cost limit ("Too Expensive!" prompt)
- Remove prior work penalty (combine XP cost increasing the more you repair and upgrade the item)

Configuration options:
- Change the amount of material units required to fully repair an item for each item type
- Toggle each option listed above on or off

### Bottle O' Enchanting Improvements

- Increases the amount of experience given by the bottles
- Renames "Bottle O' Enchanting" to "Experience Bottle"
- Adds bottles as loot to more chests in the world

Configuration options:
- Change the amount of experience given by the bottles. `rand(x)` can be used in the expression to pick a random number from 1 to x. The vanilla experience amount is `1 + rand(5) + rand(5)`
- Enable or disable renaming to "Experience Bottle"
- Enable or disable adding bottles as loot to more chests in the world
- Add or remove additional locations where the bottles can be found and define the loot chance and amount, in the format of `location;chance;count`

### Enchanted Book Loot Improvements

Adds additional enchanted book loot to chests in the world.

Optionally, make defined enchantments exclusive to defined structures, and specify the chances for each enchantment level to appear on looted enchanted books.

Configuration options:
- Add or remove additional locations where enchanted books can be found and define the loot chance and amount, in the format of `location;chance;count`
- Enable or disable making enchantments exclusive to defined structures
- Add or remove rules for structure-specific enchantments, in the format of `enchantment,location;chance;count, ... ,location;chance;count` (an example entry is provided in the default config)
- Enable or disable weighted enchantment levels in looted enchanted books
- Modify weights for each enchantment level

### Music Disc Loot

Adds a chance to find any music disc you can usually get from getting a skeleton to kill a creeper to loot chests in the world. Additionally, removes the vanilla 13 and Cat music disc loot in regular dungeons to avoid getting many copies of those two discs.

Configuration options:
- Change the chance that a disc will spawn in any of the defined locations
- Add or remove locations where the discs can be found
- Enable or disable removing the vanilla 13 and Cat music disc loot from regular dungeons

## Tweaks

### Craft tipped arrows with regular potions

Changes the tipped arrow recipe to use regular potions instead of lingering potions. If EMI is installed, the recipe will change accordingly.

### Glowstone dust recipe

Enables crafting glowstone dust by combining redstone dust with blaze powder.

### Crying obsidian recipe

Enables crafting crying obsidian by combining obsidian with a ghast tear.

### Infinity compatible with Mending

Makes it possible to apply Mending to a bow enchanted with Infinity and vice versa.

### Soul Speed doesn't damage boots

Removes the durability damage taken from using Soul Speed.

### Play bow drawing sounds

Makes the bow play a bow drawing sound when being drawn by players or mobs. Makes dealing with skeletons easier since you can now hear them drawing the bow before they shoot.

### Disable shulker duplication

Disables the game mechanic that allows shulkers to duplicate by hitting each other with their bullets.

### Disable pillager patrols

Prevents pillager patrols from spawning, regardless of what the gamerule is set to.

### Disable night vision

Makes Night Vision unobtainable in survival with the following changes:
- The brewing recipe for Night Vision potions now makes Invisibility potions instead
- The crafting recipe for Suspicious Stew with a Night Vision effect now gives a Suspicious Stew with Invisibility instead
- Looted Suspicious Stew that would contain Night Vision now contains Invisibility instead
- Advancements which require Night Vision in any form now don't

### Riptide works in water only

Makes tridents enchanted with Riptide work only in water, and not in rain.

### Reduce debug info in survival

Turns on reduced debug info in survival mode, hiding essential info (such as coordinates) from the F3 debug screen.

### Disable double-tap to sprint

Disables double-tapping the forward key to start sprinting.