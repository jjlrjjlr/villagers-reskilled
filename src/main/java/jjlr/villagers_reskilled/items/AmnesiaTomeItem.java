package jjlr.villagers_reskilled.items;

import jjlr.villagers_reskilled.VillagersReskilled;
import jjlr.villagers_reskilled.items.interfaces.IUsableOnVillager;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AmnesiaTomeItem extends Item implements IUsableOnVillager {
    public AmnesiaTomeItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (user.getWorld().isClient) {
            return ActionResult.SUCCESS;
        }

        if (entity instanceof VillagerEntity villager) {
            VillagerType villagerType = villager.getVillagerData().getType();

            VillagersReskilled.LOGGER.debug("Using AmnesiaTome on villager with id {} , of type {} , with profession {}.", villager.getUuidAsString(), villagerType.toString(), villager.getVillagerData().getProfession().id());

            villager.getBrain().forget(MemoryModuleType.JOB_SITE);
            villager.setVillagerData(new VillagerData(villagerType, VillagerProfession.NONE, 0));
            villager.setOffers(new TradeOfferList());
        }

        if (!user.getAbilities().creativeMode) {
            user.getStackInHand(hand).decrement(1);
        }

        return ActionResult.CONSUME;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (context.isAdvanced()) {
            tooltip.add(Text.translatable("tooltip.villagers-reskilled.amnesia_tome.description.line1"));
        }
    }
}
