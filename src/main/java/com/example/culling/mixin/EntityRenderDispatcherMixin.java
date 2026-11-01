package com.example.culling.mixin;

import com.example.culling.CullingUtil;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {

    // x, y, z passed to shouldRender are the camera position.
    @Inject(
        method = "shouldRender(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/render/Frustum;DDD)Z",
        at = @At("HEAD"),
        cancellable = true
    )
    private void aggressiveCulling$shouldRender(Entity entity, Frustum frustum, double x, double y, double z,
                                                CallbackInfoReturnable<Boolean> cir) {
        if (CullingUtil.isBeyondRenderDistance(x, z, entity.getX(), entity.getZ())) {
            cir.setReturnValue(false);
        }
    }
}
