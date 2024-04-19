package jjlr.villagers_reskilled.items;

import jjlr.villagers_reskilled.entities.ExperiencedInkProjectileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ExperiencedInkItem extends Item {
    public ExperiencedInkItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (world.isClient) {
            return TypedActionResult.success(itemStack, true);
        }

        ExperiencedInkProjectileEntity inkProjectileEntity = new ExperiencedInkProjectileEntity(user, world);
        inkProjectileEntity.setItem(itemStack);
        inkProjectileEntity.setVelocity(user, user.getPitch(), user.getYaw(), -5.0f, 0.5f, 1.0f);

        ((ServerWorld) world).playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_EXPERIENCE_BOTTLE_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        world.spawnEntity(inkProjectileEntity);

        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, false);
    }
}
