package com.alan.clients.module.impl.client.discordrpc;

import com.alan.clients.module.impl.client.DiscordRpc;
import com.alan.clients.module.setting.impl.SubMode;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;


public class RavenXdRPC extends SubMode<DiscordRpc> {
    private final String clientId = "1261607711653629952";
    private boolean started;

    public RavenXdRPC(String name, DiscordRpc parent) {
        super(name, parent);
    }

    @Override
    public void onUpdate() {
        if (!started) {
            DiscordRPC.discordInitialize(clientId, new DiscordEventHandlers.Builder().setReadyEventHandler(user -> {
                DiscordRichPresence.Builder presence = new DiscordRichPresence.Builder("");
                presence.setDetails("Cheating using RavenXD");
                presence.setBigImage("logo", "https://github.com/夏翊淳/Raven-XD").setStartTimestamps(System.currentTimeMillis());
                DiscordRPC.discordUpdatePresence(presence.build());
            }).build(), true);
            new Thread(() -> {
                while (this.isEnabled()) {
                    DiscordRPC.discordRunCallbacks();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }, "Discord RPC Callback").start();
            started = true;
        }
    }

    @Override
    public void onDisable() {
        DiscordRPC.discordShutdown();
        started = false;
    }
}
