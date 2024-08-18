package com.alan.clients.module.impl.player;

import com.alan.clients.module.Module;
import com.alan.clients.module.impl.player.fakelag.*;
import com.alan.clients.module.setting.impl.ModeValue;

public class FakeLag extends Module {
    private final ModeValue mode;

    public FakeLag() {
        super("Fake Lag", category.player);
        this.registerSetting(mode = new ModeValue("Mode", this)
                .add(new LatencyFakeLag("Latency", this))
                .add(new DynamicFakeLag("Dynamic", this))
                .setDefaultValue("Latency"));
    }

    public String getInfo() {
        return mode.getSubModeValues().get((int) mode.getInput()).getPrettyName();
    }

    public void onEnable() {
        mode.enable();
    }

    public void onDisable() {
        // TODO we need to let it disable for auto?
        mode.disable();
    }
}