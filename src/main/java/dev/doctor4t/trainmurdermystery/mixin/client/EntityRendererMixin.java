package dev.doctor4t.trainmurdermystery.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.doctor4t.trainmurdermystery.index.TMMBlocks;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity> {
    @WrapMethod(method = "getBlockLight")
    protected int tmm$replaceLightInLightBarrier(T entity, BlockPos pos, Operation<Integer> original) {
        BlockPos blockPos = pos;
        if (entity.getWorld().getBlockState(BlockPos.ofFloored(entity.getEyePos())).isOf(TMMBlocks.LIGHT_BARRIER)) {
            blockPos = pos.down();
        }
        return original.call(entity, blockPos);
    }
}
