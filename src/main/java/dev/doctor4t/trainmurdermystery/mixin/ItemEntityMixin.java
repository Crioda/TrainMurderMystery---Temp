package dev.doctor4t.trainmurdermystery.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.doctor4t.trainmurdermystery.cca.TMMComponents;
import dev.doctor4t.trainmurdermystery.index.TMMItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
    @Shadow
    public abstract @Nullable Entity getOwner();

    @Shadow private @Nullable UUID throwerUuid;

    @WrapMethod(method = "onPlayerCollision")
    public void tmm$preventGunPickup(PlayerEntity player, Operation<Void> original) {
        if (!TMMComponents.GAME.get(player.getWorld()).isHitman(player) && !player.equals(this.getOwner()) && !player.getInventory().contains(itemStack -> itemStack.isOf(TMMItems.REVOLVER))) {
            original.call(player);
        }
    }
}
