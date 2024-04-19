package jjlr.villagers_reskilled;

import jjlr.villagers_reskilled.registries.Entities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class VillagersReskilledClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(Entities.BLAZING_INK_PROJECTILE_ENTITY, FlyingItemEntityRenderer::new);
	}
}