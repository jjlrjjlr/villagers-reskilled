package jjlr.villagers_reskilled;

import jjlr.villagers_reskilled.registries.Entities;
import jjlr.villagers_reskilled.registries.ItemGroups;
import jjlr.villagers_reskilled.registries.Items;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VillagersReskilled implements ModInitializer {
	public static final String MODID = "villagers-reskilled";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		Items.register();
		ItemGroups.register();

		Entities.register();

		LOGGER.info("Init finished.");
	}
}