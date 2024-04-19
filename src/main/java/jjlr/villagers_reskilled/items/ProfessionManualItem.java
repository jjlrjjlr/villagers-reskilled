package jjlr.villagers_reskilled.items;

import com.mojang.serialization.DataResult;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.village.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ProfessionManualItem extends Item {
    public ProfessionManualItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (user.getWorld().isClient()) {
            return ActionResult.SUCCESS;
        }

        if (entity instanceof VillagerEntity villager) {
            NbtCompound stackNbt;

            if (villager.getVillagerData().getProfession() == VillagerProfession.NITWIT) {
                return ActionResult.CONSUME;
            }

            if (stack.hasNbt()) {
                stackNbt = stack.getNbt();
            }
            else {
                stackNbt = new NbtCompound();
            }

            if (!stackNbt.contains("villager_data") && villager.getVillagerData().getProfession() != VillagerProfession.NONE) {
                TradeOfferList trades = villager.getOffers();
                DataResult<NbtElement> villagerData = VillagerData.CODEC.encodeStart(NbtOps.INSTANCE, villager.getVillagerData());
                VillagerType villagerType = villager.getVillagerData().getType();

                NbtCompound villagerNbt = new NbtCompound();
                villagerNbt.put("offers", trades.toNbt());
                villagerNbt.put("villager_data", villagerData.result().orElseThrow());

                user.getStackInHand(hand).setNbt(villagerNbt);

                villager.setOffers(new TradeOfferList());
                villager.setVillagerData(new VillagerData(villagerType, VillagerProfession.NONE, 0));
            }
            else if (stackNbt.contains("villager_data") && villager.getVillagerData().getProfession() == VillagerProfession.NONE) {
                TradeOfferList trades = new TradeOfferList(stack.getNbt().getCompound("offers"));
                DataResult<VillagerData> villagerData = VillagerData.CODEC.parse(NbtOps.INSTANCE, stack.getNbt().get("villager_data"));

                villager.setVillagerData(villagerData.result().orElseThrow());
                villager.setOffers(trades);

                user.getStackInHand(hand).setNbt(new NbtCompound());
            }

            return ActionResult.CONSUME;
        }

        return ActionResult.CONSUME;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasNbt()) {
            NbtCompound nbt = stack.getNbt();

            if (nbt.contains("villager_data")) {
                TradeOfferList trades = new TradeOfferList(nbt.getCompound("offers"));
                DataResult<VillagerData> villagerData = VillagerData.CODEC.parse(NbtOps.INSTANCE, nbt.get("villager_data"));

                tooltip.add(1, Text.translatable("tooltip.villagers-reskilled.profession_manual.line1").formatted(Formatting.GREEN).append(Text.literal(villagerData.result().orElseThrow().getProfession().id()).formatted(Formatting.AQUA)));
                tooltip.add(2, Text.translatable("tooltip.villagers-reskilled.profession_manual.line2").formatted(Formatting.GREEN));

                int lineNum = 3;
                for (TradeOffer offer : trades) {
                    tooltip.add(lineNum, Text.literal(String.format("  %s for %d %s", offer.getSellItem().getName().getString(), offer.getOriginalFirstBuyItem().getCount(), offer.getOriginalFirstBuyItem().getName().getString())).formatted(Formatting.AQUA));
                }
            }
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        if (stack.hasNbt()) {
            return stack.getNbt().contains("villager_data");
        }

        return false;
    }
}
