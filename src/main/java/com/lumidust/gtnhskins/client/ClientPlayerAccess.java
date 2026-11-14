package com.lumidust.gtnhskins.client;

import net.minecraft.util.ResourceLocation;

/** Client-only access to the existing player skin and SSB model state. */
public interface ClientPlayerAccess {

    ResourceLocation gtnhskins$getSkin();

    void gtnhskins$setSkin(ResourceLocation location);

    boolean gtnhskins$isSlim();

    void gtnhskins$setSlim(boolean slim);
}
