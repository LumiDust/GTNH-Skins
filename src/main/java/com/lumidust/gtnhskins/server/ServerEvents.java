package com.lumidust.gtnhskins.server;

import java.util.function.Consumer;

import net.minecraft.entity.player.EntityPlayerMP;

import com.lumidust.gtnhskins.GtnhSkins;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public final class ServerEvents {

    private boolean tickFailureReported;

    @SubscribeEvent
    public void tick(TickEvent.ServerTickEvent event) {
        SkinServer server = GtnhSkins.server();
        if (server == null || event.phase != TickEvent.Phase.END) return;
        try {
            server.tick();
        } catch (RuntimeException | LinkageError failure) {
            if (!tickFailureReported) {
                tickFailureReported = true;
                GtnhSkins.LOGGER.warn("Skin update processing failed; server gameplay continues", failure);
            }
        }
    }

    @SubscribeEvent
    public void join(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player instanceof EntityPlayerMP player) dispatch(player, server -> server.joined(player));
    }

    @SubscribeEvent
    public void leave(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.player instanceof EntityPlayerMP player) dispatch(player, server -> server.left(player));
    }

    @SubscribeEvent
    public void respawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.player instanceof EntityPlayerMP player) dispatch(player, server -> server.rebuilt(player));
    }

    @SubscribeEvent
    public void dimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.player instanceof EntityPlayerMP player) dispatch(player, server -> server.rebuilt(player));
    }

    private void dispatch(EntityPlayerMP player, Consumer<SkinServer> operation) {
        SkinServer server = GtnhSkins.server();
        if (server == null) {
            if (GtnhSkins.startupWarning() != null) SkinServer.say(player, GtnhSkins.startupWarning());
            return;
        }
        try {
            operation.accept(server);
        } catch (RuntimeException | LinkageError failure) {
            SkinServer.say(player, "This skin update could not be completed; normal gameplay continues.");
            GtnhSkins.LOGGER.warn("Skin player event failed for {}", player.getCommandSenderName(), failure);
        }
    }
}
