package com.example.culling.mixin;

import com.example.culling.CullingUtil;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityRenderDispatcher.class)
public abstract class BlockEntityRenderDispatcherMixin {

    @Inject(
        method = "render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private void aggressiveCulling$render(BlockEntity blockEntity, float tickDelta, MatrixStack matrices,
                                          VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
        if (CullingUtil.isBeyondRenderDistance(blockEntity.getPos().getX() + 0.5, blockEntity.getPos().getZ() + 0.5)) {
            ci.cancel();
        }
    }
}
