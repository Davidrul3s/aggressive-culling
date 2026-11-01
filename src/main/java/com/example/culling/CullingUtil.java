package com.example.culling;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * Shared render-distance check. Uses chunk-grid (square) distance so it matches
 * exactly the area of chunks the terrain renderer draws.
 */
public final class CullingUtil {
    private CullingUtil() {}

    /** True if (x, z) lies in a chunk outside the render distance around (camX, camZ). */
    public static boolean isBeyondRenderDistance(double camX, double camZ, double x, double z) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.options == null) {
            return false;
        }

        int renderDistance = client.options.getClampedViewDistance();
        int dx = Math.abs((MathHelper.floor(x) >> 4) - (MathHelper.floor(camX) >> 4));
        int dz = Math.abs((MathHelper.floor(z) >> 4) - (MathHelper.floor(camZ) >> 4));

        return dx > renderDistance || dz > renderDistance;
    }

    /** Same check, measured from the current camera entity. Never culls if there is no camera entity. */
    public static boolean isBeyondRenderDistance(double x, double z) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) {
            return false;
        }

        Entity camera = client.getCameraEntity();
        if (camera == null) {
            return false;
        }

        return isBeyondRenderDistance(camera.getX(), camera.getZ(), x, z);
    }
}
