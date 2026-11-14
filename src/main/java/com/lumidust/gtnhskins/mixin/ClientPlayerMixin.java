package com.lumidust.gtnhskins.mixin;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.lumidust.gtnhskins.client.ClientBridge;
import com.lumidust.gtnhskins.client.ClientPlayerAccess;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;

@Mixin(value = AbstractClientPlayer.class, priority = 900)
public abstract class ClientPlayerMixin implements ClientPlayerAccess {

    @Shadow
    private ResourceLocation locationSkin;

    @Unique
    private boolean gtnhskins$slim;

    @Inject(method = "func_152121_a", at = @At("HEAD"), cancellable = true)
    private void gtnhskins$acceptSkin(MinecraftProfileTexture.Type type, ResourceLocation location,
        CallbackInfo callback) {
        if (type == MinecraftProfileTexture.Type.SKIN
            && !ClientBridge.acceptCallback((AbstractClientPlayer) (Object) this, location)) {
            callback.cancel();
        }
    }

    /*
     * SSB adds IArmsState to EntityPlayer. These intentional subclass overrides
     * keep its renderer and interface dispatch, without linking SSB at compile
     * time. Both its download-buffer callback and SkinManager TAIL write here.
     * No @Unique: the public SSB names must remain available for virtual dispatch.
     */
    public boolean ssb$isSlim() {
        return gtnhskins$slim;
    }

    public void ssb$setSlim(boolean slim) {
        if (ClientBridge.acceptArmChange((AbstractClientPlayer) (Object) this, slim)) {
            gtnhskins$slim = slim;
        }
    }

    @Override
    public ResourceLocation gtnhskins$getSkin() {
        return locationSkin;
    }

    @Override
    public void gtnhskins$setSkin(ResourceLocation location) {
        locationSkin = location;
    }

    @Override
    public boolean gtnhskins$isSlim() {
        return ssb$isSlim();
    }

    @Override
    public void gtnhskins$setSlim(boolean slim) {
        ssb$setSlim(slim);
    }
}
