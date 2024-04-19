package jjlr.villagers_reskilled.entities;

import jjlr.villagers_reskilled.registries.Entities;
import jjlr.villagers_reskilled.registries.Items;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public class ExperiencedInkProjectileEntity extends ThrownItemEntity {
    public ExperiencedInkProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public ExperiencedInkProjectileEntity(LivingEntity livingEntity, World world) {
        super(Entities.EXPERIENCED_INK_PROJECTILE_ENTITY, livingEntity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.EXPERIENCED_INK.getItem();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (this.getWorld().isClient) {
            return;
        }
        this.getWorld().sendEntityStatus(this, (byte) 3);
        this.getWorld().syncWorldEvent(WorldEvents.SPLASH_POTION_SPLASHED, this.getBlockPos(), StatusEffects.BLINDNESS.getColor());
        ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.TOTEM_OF_UNDYING, this.getX(), this.getY(), this.getZ(), 30, 2.0f, 1.0f, 2.0f, 0.1);

        StatusEffectInstance blindness = new StatusEffectInstance(StatusEffects.BLINDNESS, 150);
        AreaEffectCloudEntity effectCloud = new AreaEffectCloudEntity(this.getWorld(), this.getX(), this.getY(), this.getZ());
        effectCloud.addEffect(blindness);
        effectCloud.setRadius(2.5f);
        effectCloud.setColor(2367780);
        effectCloud.setDuration(120);

        this.getWorld().spawnEntity(effectCloud);

        int experienceAmount = this.getWorld().random.nextBetween(3, 10);
        ExperienceOrbEntity.spawn((ServerWorld) this.getWorld(), this.getPos(), experienceAmount);

        this.discard();
        super.onBlockHit(blockHitResult);
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
