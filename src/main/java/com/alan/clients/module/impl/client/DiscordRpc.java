package com.alan.clients.module.impl.client;

import com.alan.clients.module.Module;
import com.alan.clients.module.impl.client.discordrpc.AugustusRPC;
import com.alan.clients.module.impl.client.discordrpc.BadlionRPC;
import com.alan.clients.module.impl.client.discordrpc.LunarClientRPC;
import com.alan.clients.module.impl.client.discordrpc.RavenXdRPC;
import com.alan.clients.module.setting.impl.ModeValue;

public class DiscordRpc extends Module {
    private final ModeValue mode;

    public DiscordRpc() {
        super("DiscordRPC", category.client);
        this.registerSetting(mode = new ModeValue("Mode", this)
                .add(new RavenXdRPC("RavenXD", this))
                .add(new LunarClientRPC("Lunar Client", this))
                .add(new AugustusRPC("Augustus", this))
                .add(new BadlionRPC("Badlion Client", this))
                .setDefaultValue("RavenXD"));
    }

    public void onEnable() {
        mode.enable();
    }

    public void onDisable() {
        mode.disable();
    }
}
