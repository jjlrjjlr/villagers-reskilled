package jjlr.villagers_reskilled.entities;

import jjlr.villagers_reskilled.registries.Entities;
import jjlr.villagers_reskilled.registries.Items;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
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
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class BlazingInkEntity extends ThrownItemEntity {
    public BlazingInkEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public BlazingInkEntity(LivingEntity livingEntity, World world) {
        super(Entities.BLAZING_INK_PROJECTILE_ENTITY, livingEntity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.BLAZING_INK.getItem();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (this.getWorld().isClient) {
            return;
        }
        this.getWorld().sendEntityStatus(this, (byte) 3);

        StatusEffectInstance blindness = new StatusEffectInstance(StatusEffects.BLINDNESS, 150);

        AreaEffectCloudEntity effectCloud = new AreaEffectCloudEntity(this.getWorld(), this.getX(), this.getY(), this.getZ());
        effectCloud.addEffect(blindness);
        effectCloud.setDuration(120);
        effectCloud.setRadius(2.5f);
        effectCloud.setColor(2367780);

        this.getWorld().spawnEntity(effectCloud);
        ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 30, 1.75f, 1.0f, 1.75f, 0.007f);

        for (LivingEntity entity : this.getWorld().getEntitiesByClass(LivingEntity.class, Box.of(this.getPos(), 2.5, 2.0, 2.5), livingEntity -> true)) {
            entity.setFireTicks(100 - (int) ((this.distanceTo(entity) > 1.0f) ? (this.distanceTo(entity) * 30) : 0));
        };

        this.discard();
        super.onBlockHit(blockHitResult);
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
