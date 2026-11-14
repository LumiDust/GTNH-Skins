package com.lumidust.gtnhskins.mixin;

import net.minecraft.entity.EntityTracker;
import net.minecraft.util.IntHashMap;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityTracker.class)
public interface EntityTrackerAccessor {

    @Accessor("trackedEntityIDs")
    IntHashMap gtnhskins$getTrackedEntities();
}
