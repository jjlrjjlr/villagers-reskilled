package jjlr.villagers_reskilled.registries;

import jjlr.villagers_reskilled.VillagersReskilled;
import jjlr.villagers_reskilled.entities.BlazingInkEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Entities {
    public static final EntityType<BlazingInkEntity> BLAZING_INK_PROJECTILE_ENTITY = Registry.register(Registries.ENTITY_TYPE, new Identifier(VillagersReskilled.MODID, "blazing_ink_projectile_entity"), FabricEntityTypeBuilder.<BlazingInkEntity>create(SpawnGroup.MISC, BlazingInkEntity::new).dimensions(EntityDimensions.fixed(0.2f, 0.2f)).build());

    public static void register() {
        VillagersReskilled.LOGGER.debug("Registering entities.");
    }
}
