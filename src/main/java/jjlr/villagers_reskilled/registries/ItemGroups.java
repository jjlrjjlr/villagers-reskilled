package jjlr.villagers_reskilled.registries;

import jjlr.villagers_reskilled.VillagersReskilled;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemGroups {
    private static final ItemGroup VILLAGERS_RESKILLED_ITEMGROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Items.PROFESSION_MANUAL.getItem()))
            .entries((displayContext, entries) -> {
                entries.add(Items.BLAZING_INK.getItem());
                entries.add(Items.EXPERIENCED_INK.getItem());
                entries.add(Items.PROFESSION_MANUAL.getItem());
            })
            .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, new Identifier(VillagersReskilled.MODID, "all_items"), VILLAGERS_RESKILLED_ITEMGROUP);
    }
}
