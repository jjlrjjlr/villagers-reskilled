package jjlr.villagers_reskilled.items;

import jjlr.villagers_reskilled.entities.BlazingInkEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BlazingInkItem extends Item {
    public BlazingInkItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (world.isClient) {
            return TypedActionResult.success(itemStack, true);
        }

        BlazingInkEntity inkEntity = new BlazingInkEntity(user, world);
        inkEntity.setItem(itemStack);
        inkEntity.setVelocity(user, user.getPitch(), user.getYaw(), -5.0f, 0.5f, 1.0f);

        world.spawnEntity(inkEntity);

        if (!user.getAbilities().creativeMode) {
            user.getStackInHand(hand).decrement(1);
        }

        return TypedActionResult.pass(itemStack);
    }
}
