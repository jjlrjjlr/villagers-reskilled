package jjlr.villagers_reskilled.mixin;

import jjlr.villagers_reskilled.items.interfaces.IUsableOnWanderingTrader;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WanderingTraderEntity.class)
public class WanderingTraderEntityMixin {
    @Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
    protected void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (player.getStackInHand(hand).getItem() instanceof IUsableOnWanderingTrader) {
            cir.setReturnValue(ActionResult.PASS);
        }
    }

}
