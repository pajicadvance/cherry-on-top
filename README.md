# Cherry On Top

A collection of features and tweaks for Minecraft 1.21 Fabric.

## Features

Every feature is disabled by default, and can be toggled on or off at any time. Changing some options will require a restart, and you will be prompted to do so if a restart is required.

### Enchantment Upgrading

Adds a new smithing template found in End Cities by default that can upgrade a single enchantment stored in a book by one. The selected enchantment depends on the amount of lapis added to the recipe. By default, upgrading costs experience based on the upgraded book's repair cost. The template can be duplicated once found.

Configuration options:
- Add or remove locations where the smithing template can be found and define the loot chance and the amount of templates, in the format of `location;chance;count`
- Add an XP cost to upgrading books, based on the repair cost of the book being upgraded, and set an optional base XP cost added on top of the repair cost
- Remove the XP cost entirely
- Ignore "Too Expensive!" (XP cost limit) when upgrading
- Only allow upgrading books which contain one enchantment only

### Phantom Spawning Rework

Replaces vanilla phantom spawning rules with spawning rules based on altitude. 

Instead of having to not sleep for 3 in-game days in order to trigger phantom spawning at night, phantoms now start spawning at night above Y level of 128 by default, with the spawn chance increasing the higher the player is above the defined Y level, regardless of whether the player has slept recently or not.

Additionally, phantoms can be repelled by holding a repellent item (phantom membrane by default), either in the main hand or offhand.

Configuration options:
- Change the starting height for phantom spawning
- Modify the frequency at which the spawn check will run, in seconds. `rand(x)` can be used in the expression to pick a random number from 1 to x. The vanilla frequency is `59 + rand(60)`
- Enable or disable phantom repelling
- Add or remove items to be used as phantom repellent items

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

Optionally, make defined enchantments exclusive to defined structures, modify the maximum enchantment level for each enchantment, and specify the chances for each enchantment level to appear on looted enchanted books.

Configuration options:
- Enable or disable adding enchanted books as loot to more chests in the world
- Add or remove additional locations where enchanted books can be found and define the loot chance and amount, in the format of `location;chance;count`
- Enable or disable making enchantments exclusive to defined structures
- Add or remove rules for structure-specific enchantments, in the format of `enchantment,location,...,location;chance;count` (an example entry is provided in the default config)
- Enable or disable modifying the maximum enchantment level for each enchantment
- Add or remove maximum enchantment level overrides, in the format of `enchantment/maxLevel` (an example entry is provided in the default config)
- Enable or disable weighted enchantment levels in looted enchanted books
- Modify weights for each enchantment level

### Music Disc Loot

Adds a chance to find any music disc you can usually get from getting a skeleton to kill a creeper to loot chests in the world. Additionally, removes the vanilla 13 and Cat music disc loot in regular dungeons to avoid getting many copies of those two discs.

Configuration options:
- Change the chance that a disc will spawn in any of the defined locations
- Add or remove locations where the discs can be found
- Enable or disable removing the vanilla 13 and Cat music disc loot from regular dungeons

### Enchantment Disabler

Specify enchantments to disable from the game. Those enchantments won't appear anywhere in the game, making them unobtainable in survival gameplay, and only obtainable by commands.

If EMI is installed, the disabled enchantments won't appear in the EMI index, and any recipe related to the enchantment will be removed.

If Structure Specific Enchantments is enabled in Enchanted Book Loot Improvements, the enchantments specified in that list will also be disabled.

Disabled by default. Useful for balancing a modpack, or just disabling enchantments you don't like.

## Tweaks

Every tweak is disabled by default, and can be toggled on or off at any time. Toggling some tweaks will require a restart, and you will be prompted to do so if a restart is required.

### Craft tipped arrows with regular potions

Changes the tipped arrow recipe to use regular potions instead of lingering potions. If EMI is installed, the recipe will change accordingly.

### Infinity compatible with Mending

Makes it possible to apply Mending to a bow enchanted with Infinity and vice versa.

### Play bow drawing sounds

Makes the bow play a bow drawing sound when being drawn by players or mobs. Makes dealing with skeletons easier since you can now hear them drawing the bow before they shoot.

### Riptide works in water only

Makes tridents enchanted with Riptide work only in water, and not in rain.

### Disable enchanting table

Entirely disables the functionality of the enchanting table. It can still be crafted and used as a decorative item.

### Reduce debug info in survival

Turns on reduced debug info in survival mode, hiding essential info (such as coordinates) from the F3 debug screen.

### Disable double-tap to sprint

Disables double-tapping the forward key to start sprinting.