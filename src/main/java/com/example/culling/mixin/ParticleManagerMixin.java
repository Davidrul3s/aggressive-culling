package com.example.culling.mixin;

import com.example.culling.CullingUtil;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleManager.class)
public abstract class ParticleManagerMixin {

    // Returning null is valid here: vanilla already returns null for particles it skips.
    @Inject(
        method = "addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)Lnet/minecraft/client/particle/Particle;",
        at = @At("HEAD"),
        cancellable = true
    )
    private void aggressiveCulling$addParticle(ParticleEffect parameters, double x, double y, double z,
                                               double velocityX, double velocityY, double velocityZ,
                                               CallbackInfoReturnable<Particle> cir) {
        if (CullingUtil.isBeyondRenderDistance(x, z)) {
            cir.setReturnValue(null);
        }
    }
}
