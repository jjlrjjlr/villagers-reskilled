package jjlr.villagers_reskilled.registries;

import jjlr.villagers_reskilled.VillagersReskilled;
import jjlr.villagers_reskilled.items.ProfessionManualItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.function.Supplier;

public enum Items {
    BLAZING_INK("blazing_ink", () -> new Item(new FabricItemSettings().maxCount(16).rarity(Rarity.UNCOMMON).fireproof())),
    EXPERIENCED_INK("experienced_ink", () -> new Item(new FabricItemSettings().maxCount(16).rarity(Rarity.UNCOMMON))),
    PROFESSION_MANUAL("profession_manual", () -> new ProfessionManualItem(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON)));

    private final String identifier;
    private final Supplier<Item> itemSupplier;
    private Item item;

    Items(String argIdentifier, Supplier<Item> argItemSupplier) {
        this.identifier = argIdentifier;
        this.itemSupplier = argItemSupplier;
    }

    public static void register() {
        for (Items currentItem : values()) {
            Registry.register(Registries.ITEM, new Identifier(VillagersReskilled.MODID, currentItem.identifier), currentItem.getItem());
        }
    }

    public Item getItem() {
        if (item == null) {
            item = itemSupplier.get();
        }

        return item;
    }
}
