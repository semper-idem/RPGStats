package mc.rpgstats.mixin;

import mc.rpgstats.main.CustomComponents;
import mc.rpgstats.main.RPGStats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(PlayerEntity.class)
public class ShieldMixin {
    @Inject(at = @At("HEAD"), method = "damageShield")
    private void onShieldUse(float amount, CallbackInfo ci) {
        //noinspection ConstantConditions
        if ((Object)this instanceof ServerPlayerEntity && new Random().nextBoolean()) {
            RPGStats.addXpAndLevelUp(CustomComponents.DEFENSE, (ServerPlayerEntity)(Object)this, (int)Math.floor(amount / 2.2));
        }
    }
}
