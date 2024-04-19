package jjlr.villagers_reskilled;

import com.mojang.serialization.DataResult;
import jjlr.villagers_reskilled.registries.Items;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.client.ItemTooltipCallback;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerProfession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VillagersReskilled implements ModInitializer {
	public static final String MODID = "villagers-reskilled";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		Items.register();

		LOGGER.info("Init finished.");
	}
}