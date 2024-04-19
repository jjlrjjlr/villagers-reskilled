package jjlr.villagers_reskilled.mixin;

import jjlr.villagers_reskilled.registries.Items;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public class VillagerEntityMixin {
    @Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
    protected void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (player.getStackInHand(hand).isOf(Items.PROFESSION_MANUAL.getItem())) {
            cir.setReturnValue(ActionResult.PASS);
        }
    }
}
